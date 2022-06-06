package com.zhaogang.other.excel.easyexcel.export.handler;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;

/**
 * @author weiguo.liu
 * @date 2021/1/22
 * @description 自动合并策略
 */
public class AutoMergeStrategy extends AbstractMergeStrategy {

    /**
     * 需要合并的列集合
     */
    private List<Integer> columnCollection;
    /**
     * 合并的起始行，从第几行开始合并行为
     */
    private int startRowIndex;

    public AutoMergeStrategy(List<Integer> columnCollection, int startRowIndex) {
        this.columnCollection = columnCollection != null ? columnCollection : Collections.emptyList();
        this.startRowIndex = startRowIndex;
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
        // 检测是否达到合并的坐标
        if (cell.getRowIndex() < startRowIndex) {
            return;
        }

        // 检测是否包含指定合并的列
        if (columnCollection.contains(cell.getColumnIndex())) {
//            mergeWithPreRow(sheet, cell);
            mergeWithPreRow2(cell);
        }
    }

    /**
     * 与上层对象的单元格合并
     * 
     * @param sheet
     * @param cell
     */
    private void mergeWithPreRow(Sheet sheet, Cell cell) {
        int curRowIndex = cell.getRowIndex();
        int curColIndex = cell.getColumnIndex();

        // 第一行（0行）数据即标题不需要向上合并，
        // todo: 第2行(1行)数据也不需要向上合并，因为实际数据不会和标题合并，这是有标题的情况下
        if (curRowIndex == 0) {
            return;
        }

        // 获取当前行的当前列的数据和上一行的当前列数据，通过上一行数据是否相同进行合并
        Object curData =
            cell.getCellTypeEnum() == CellType.STRING ? cell.getStringCellValue() : cell.getNumericCellValue();
        Cell preCell = cell.getSheet().getRow(curRowIndex - 1).getCell(curColIndex);
        Object preData =
            preCell.getCellTypeEnum() == CellType.STRING ? preCell.getStringCellValue() : preCell.getNumericCellValue();

        // 比较当前行的第一列的单元格与上一行是否相同
        // 1. 相同则进行合并 2. 不同则返回
        if (!curData.equals(preData)) {
            return;
        }

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

    /**
     * 与上层对象的单元格合并
     *
     * @param cell
     */
    private void mergeWithPreRow2(Cell cell) {
        int curRowIndex = cell.getRowIndex();
        int curColIndex = cell.getColumnIndex();
        Sheet sheet = cell.getSheet();

        // 第一行（0行）数据即标题不需要向上合并，
        // todo: 第2行(1行)数据也不需要向上合并，因为实际数据不会和标题合并，这是有标题的情况下
        if (curRowIndex == 0) {
            return;
        }

        // 获取当前行的当前列的数据和上一行的当前列数据，通过上一行数据是否相同进行合并
        Object curData =
            cell.getCellTypeEnum() == CellType.STRING ? cell.getStringCellValue() : cell.getNumericCellValue();
        Cell preCell = sheet.getRow(curRowIndex - 1).getCell(curColIndex);
        Object preData =
            preCell.getCellTypeEnum() == CellType.STRING ? preCell.getStringCellValue() : preCell.getNumericCellValue();

        // 比较当前行的第一列的单元格与上一行是否相同
        // 1. 相同则进行合并 2. 不同则返回
        if (!curData.equals(preData)) {
            return;
        }

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
}
