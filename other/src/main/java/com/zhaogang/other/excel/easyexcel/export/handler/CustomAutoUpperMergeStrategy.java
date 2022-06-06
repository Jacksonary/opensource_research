package com.zhaogang.other.excel.easyexcel.export.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;

/**
 * @author weiguo.liu
 * @date 2021/1/22
 * @description 自动向上合并策略
 * 耗时严重
 * 5000 -> 13697ms | 13850ms
 * 10000 -> 50287ms | 50815ms
 */
public class CustomAutoUpperMergeStrategy extends AbstractMergeStrategy {
    public static final String DATA_REPLICATION_TEMPLATE = "r:{0}-c:{1}";
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAutoUpperMergeStrategy.class);
    /**
     * 合并的起始行，从第几行开始合并行为
     */
    private int startRowIndex;
    /**
     * 合并规则，每列在什么情况进行向上合并 key - 列号（从0开始），即需要合并的列有哪些 value - 列号（从0开始），在该列上行记录中除了该列相同以外，还需要哪些列值相同的情况下才能合并
     *
     * 如：1 - [2,3]， 2 - [1,3] 在第1、2、3列的值和上行记录相同时合并第1列的值 在第1、2、3列的值和上行记录相同时合并第2列的值
     *
     * 注：这种方式向上合并条件中的列属性只能是上行或者左列，无法指定右列
     */
    private Map<Integer, List<Integer>> mergeCon;
    /**
     * 数据副本，为了解决easy-excel无法读取本行和后面行的右列问题
     */
    private Object[][] dataReplication;

    public CustomAutoUpperMergeStrategy(int startRowIndex, Map<Integer, List<Integer>> mergeCon,
        Object[][] dataReplication) {
        this.startRowIndex = startRowIndex;
        this.mergeCon = mergeCon;
        this.dataReplication = dataReplication;
    }

    /**
     *
     * @param sheet
     * @param cell
     *            当前单元格，通过这个对象可以获取单元格的行列
     * @param head
     * @param relativeRowIndex
     */
    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, Integer relativeRowIndex) {
//        LOGGER.info("=============== cell:[{}], row:[{}], column:[{}] ===================", cell.getStringCellValue(),
//            cell.getRowIndex(), cell.getColumnIndex());

        // 检测是否达到合并的坐标
        if (cell.getRowIndex() < startRowIndex) {
            return;
        }

        // 检测是否包含指定合并的列
        if (!mergeCon.containsKey(cell.getColumnIndex())) {
            return;
        }

        // 检测合并属性条件
        if (mergeConCheckInReplication(cell)) {
            mergeWithPreRow(cell);
        }
    }

    private void mergeWithPreRow(Cell cell) {
        int curRowIndex = cell.getRowIndex();
        int curColIndex = cell.getColumnIndex();
        Sheet sheet = cell.getSheet();

        List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
        // 记录当前单元格是否处于合并域，只有没有处于合并域的单元格才会进行合并
        boolean isMerged = false;

        for (int i = 0; i < mergedRegions.size() && !isMerged; i++) {
            CellRangeAddress cellAddresses = mergedRegions.get(i);
            // 若上 一个单元格已经被合并，则先移出原有的合并单元，再重新添加合并单元
            if (!cellAddresses.isInRange(curRowIndex - 1, curColIndex)) {
                continue;
            }

            sheet.removeMergedRegion(i);
            cellAddresses.setLastRow(curRowIndex);
            sheet.addMergedRegion(cellAddresses);
            isMerged = true;
        }

        // 若上一个单元格未被合并，则新增合并单元
        if (!isMerged) {
            CellRangeAddress cellAddresses =
                new CellRangeAddress(curRowIndex - 1, curRowIndex, curColIndex, curColIndex);
            sheet.addMergedRegion(cellAddresses);
        }
    }

    private boolean mergeConCheck(Cell cell) {
        int curRowIndex = cell.getRowIndex();
        int curColIndex = cell.getColumnIndex();
        Sheet sheet = cell.getSheet();

        // 第一行（0行）数据即标题不需要向上合并，
        // todo: 第2行(1行)数据也不需要向上合并，因为实际数据不会和标题合并，这是有标题的情况下
        if (curRowIndex == 0) {
            return false;
        }

        // 强制比较该列上行的属性
        if (!Objects.equals(getCellData(sheet, curRowIndex, curColIndex),
            getCellData(sheet, curRowIndex - 1, curColIndex))) {
            return false;
        }

        // 基准比较，寻找不相等的列
        return mergeCon.get(cell.getColumnIndex()).stream().noneMatch(o -> {
            // 获取当前行的当前列的数据和上一行的当前列数据，通过上一行数据是否相同进行合并
            return !Objects.equals(getCellData(sheet, curRowIndex, o), getCellData(sheet, curRowIndex - 1, o));
        });
    }

    /**
     * 从写入的数据读取
     * 
     * @param sheet
     * @param rowIndex
     * @param columnIndex
     * @return
     */
    private Object getCellData(Sheet sheet, int rowIndex, int columnIndex) {
        Cell cell = sheet.getRow(rowIndex).getCell(columnIndex);
        if (cell == null) {
            LOGGER.warn(">> cell is null -> row:[{}], column:[{}]", rowIndex, columnIndex);
            return null;
        }
        return CellType.STRING == cell.getCellTypeEnum() ? cell.getStringCellValue() : cell.getNumericCellValue();
    }

    /**
     * 从数据副本中校验
     * 
     * @param cell
     * @return
     */
    private boolean mergeConCheckInReplication(Cell cell) {
        int curRowIndex = cell.getRowIndex();
        int curColIndex = cell.getColumnIndex();

        // 第一行（0行）数据即标题不需要向上合并，
        // todo: 第2行(1行)数据也不需要向上合并，因为实际数据不会和标题合并，这是有标题的情况下
        if (curRowIndex == 0) {
            return false;
        }

        // 强制比较该列上行的属性
        if (!Objects.equals(getCellData(curRowIndex, curColIndex), getCellData(curRowIndex - 1, curColIndex))) {
            return false;
        }

        // 基准比较，寻找不相等的列
        return mergeCon.get(cell.getColumnIndex()).stream().noneMatch(o -> {
            // 获取当前行的当前列的数据和上一行的当前列数据，通过上一行数据是否相同进行合并
            return !Objects.equals(getCellData(curRowIndex, o), getCellData(curRowIndex - 1, o));
        });
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        boolean noneMatch = list.stream().noneMatch(o -> {
            // 获取当前行的当前列的数据和上一行的当前列数据，通过上一行数据是否相同进行合并
            if (o == 2) {
                return true;
            }
            return false;
        });
        System.out.println(noneMatch);
    }

    /**
     * 从数据副本中读取
     * 
     * @param rowIndex
     * @param columnIndex
     * @return
     */
    private Object getCellData(int rowIndex, int columnIndex) {
        try {
            return dataReplication[rowIndex][columnIndex];
        } catch (IndexOutOfBoundsException e) {
            LOGGER.warn(">> illegal param -> index out of bounds, rowIndex:{}, columnIndex:{}", rowIndex, columnIndex);
            e.printStackTrace();
            return null;
        }
    }

}
