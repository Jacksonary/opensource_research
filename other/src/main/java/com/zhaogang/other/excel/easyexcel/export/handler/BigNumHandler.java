package com.zhaogang.other.excel.easyexcel.export.handler;

import java.util.HashSet;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.AbstractCellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;

/**
 * @author weiguo.liu
 * @date 2021/2/22
 * @description 主要解决导出时 excel 数值类型会超过11位时自动转成科学计数法的问题 同时解决小数点后的精度问题 这个需要特别注意
 */
public class BigNumHandler extends AbstractCellWriteHandler {

    // 原生excel的最大显示限制
    private static final int EXCEL_MAX_NUM_LENGTH = 11;
    // 内置的限制为15
    private static final int MAX_NUM_LIMIT = 15;
    private static final Logger LOGGER = LoggerFactory.getLogger(BigNumHandler.class);
    private static Set<Integer> cache = new HashSet<>(20);

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
        Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {
        super.beforeCellCreate(writeSheetHolder, writeTableHolder, row, head, columnIndex, relativeRowIndex, isHead);
    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell,
        Head head, Integer relativeRowIndex, Boolean isHead) {
        super.afterCellCreate(writeSheetHolder, writeTableHolder, cell, head, relativeRowIndex, isHead);
    }

    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
        CellData cellData, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        // 设置列宽
        if (!cache.contains(cell.getColumnIndex())) {
            System.out.println(cell.getColumnIndex());
            System.out.println(relativeRowIndex);
            writeSheetHolder.getSheet().setColumnWidth(cell.getColumnIndex(), 5 * 256);
            cache.add(cell.getColumnIndex());
        }

        if (!CellDataTypeEnum.NUMBER.equals(cellData.getType())) {
            return;
        }

        // 先将数字中无意义的0去除
        cellData.setNumberValue(cellData.getNumberValue().stripTrailingZeros());

        // stripTrailingZeros 可以剔除尾随的无意义的0，不能使用toString，那样数值太大会转成科学计数法，必须使用toPlainString
        String stringContent = cellData.getNumberValue().toPlainString();
        if (stringContent.length() <= EXCEL_MAX_NUM_LENGTH) {
            return;
        }

        if (stringContent.length() <= MAX_NUM_LIMIT) {
            // 做显示处理
            numShowProcess(cell, stringContent);
            return;
        }

        // 大于15位直接转成String类型
        convertBigNumToString(cellData);
    }

    private void numShowProcess(Cell cell, String num) {
        // 超过 11 位的数值类型需要改变样式
        int splitIndex = num.indexOf(".");

        boolean flag = false;
        StringBuilder sb = new StringBuilder();
        // 构建数值格式化样式，应为 ###0.### ，详细文档：https://blog.csdn.net/ba_bi/article/details/74935843
        for (int i = 0; i < num.length(); i++) {
            if (i == splitIndex) {
                sb.append(".");
                continue;
            }
            if (i > splitIndex) {
                if (num.charAt(i) != '0') {
                    flag = true;
                }
            }
            sb.append(i == splitIndex - 1 ? "0" : "#");
        }

        if (!flag) {
            return;
        }

        Workbook workbook = cell.getSheet().getWorkbook();
        CellStyle cellStyle = createCellStyle(workbook, sb.toString());
        cell.setCellStyle(cellStyle);
    }

    // 实际中如果直接获取原单元格的样式进行修改, 最后发现是改了整行的样式, 因此这里是新建一个样* 式
    private CellStyle createCellStyle(Workbook workbook, String numFormat) {
        CellStyle cellStyle = workbook.createCellStyle();

        // 创建各自的样式
        DataFormat dataFormat = workbook.createDataFormat();
        try {
            cellStyle.setDataFormat(dataFormat.getFormat(numFormat));
        } catch (Exception e) {
            LOGGER.warn(">> illegal num format,numFormat:{}", numFormat);
            e.printStackTrace();
            return cellStyle;
        }

        return cellStyle;
    }

    // 将大数值转成 String 类型
    private void convertBigNumToString(CellData cellData) {
        if (!CellDataTypeEnum.NUMBER.equals(cellData.getType())) {
            return;
        }

        String stringContent = cellData.getNumberValue().toPlainString();
        if (stringContent.length() <= EXCEL_MAX_NUM_LENGTH) {
            return;
        }

        cellData.setType(CellDataTypeEnum.STRING);
        cellData.setStringValue(stringContent);
    }

}
