package com.hhu.other.excel.commoncsv.export;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 * @author jacks
 * @date 2022/2/9
 */
public class WriteExcel {
    public static void main(String[] args) throws Exception {
        File outputFile = new File("C:/Users/jacks/Desktop/test.csv");
        String[] headers = new String[] {"id", "姓名", "年龄"};
        // List<?> contents = new ArrayList<>();
        List<String[]> data = new ArrayList<>();
        for (long i = 863879048185220L; i < 863879048185225L; i++) {
            data.add(new String[] {String.valueOf(i), "B" + i, "C" + i});
        }

        byte[] uft8bom = {(byte)0xef, (byte)0xbb, (byte)0xbf};
        FileOutputStream fos = new FileOutputStream(outputFile);
        fos.write(uft8bom);

        // 1. 使用数组定义表头
        CSVFormat csvFormat = CSVFormat.EXCEL.withHeader(headers);
        // 2. 使用枚举定义表头
        // CSVFormat csvFormat = CSVFormat.EXCEL.withHeader(CustomHeaderEnum.class);

        CSVPrinter csvPrinter =
            new CSVPrinter(new BufferedWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8)), csvFormat);

        // csvPrinter.printRecord(data);
        csvPrinter.printRecord(new BigDecimal("863879048185220").toPlainString(), "B", "C");
        csvPrinter.printRecord("863879048185221", "B2", "C2");

        List list = new ArrayList();
        list.add("A3");
        list.add("3");
        list.add(new BigDecimal(10));
        csvPrinter.printRecord(list);
        csvPrinter.flush();
    }
}
