package com.zhaogang.other.excel.export.handler;

import java.util.List;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.AbstractCellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;

/**
 * @author weiguo.liu
 * @date 2021/1/22
 * @description 自定义监听器：为第首列标题添加超链接
 */
public class CustomCellWriteHandler extends AbstractCellWriteHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomCellWriteHandler.class);

    private static int columnMaxIndex = 0;

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
        List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        // 这里可以对cell进行任何操作
        LOGGER.info("第{}行，第{}列写入完成。", cell.getRowIndex(), cell.getColumnIndex());
        setColumnNum(cell.getRowIndex(), cell.getColumnIndex());
        if (isHead && cell.getColumnIndex() == 0) {
            CreationHelper createHelper = writeSheetHolder.getSheet().getWorkbook().getCreationHelper();
            Hyperlink hyperlink = createHelper.createHyperlink(HyperlinkType.URL);
            hyperlink.setAddress("https://github.com/alibaba/easyexcel");
            cell.setHyperlink(hyperlink);
        }

        // 将最后一列除标题外的字体变红
        if (!isHead && cell.getColumnIndex() == columnMaxIndex) {
            CellStyle cellStyle = cell.getCellStyle();
            cellStyle.setFont(createFont(cell));
            cell.setCellStyle(cellStyle);
        }
    }

    private Object getCellValue(Cell cell) {
        CellType cellTypeEnum = cell.getCellTypeEnum();

        switch (cellTypeEnum) {
            case ERROR:
                return cell.getErrorCellValue();
            case BLANK:
                return "";
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case NUMERIC:
                return cell.getNumericCellValue();
            case _NONE:
            default:
                return cell.getStringCellValue();
        }
    }

    private Font createFont(Cell cell) {
        Font redFont = cell.getSheet().getWorkbook().createFont();
        redFont.setColor(Font.COLOR_RED);
        return redFont;
    }

    private void setColumnNum(int rowIndex, int columnIndex) {
        if (rowIndex != 0) {
            return;
        }
        columnMaxIndex = columnIndex;
    }
}
