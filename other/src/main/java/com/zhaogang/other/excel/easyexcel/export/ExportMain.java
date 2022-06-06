package com.zhaogang.other.excel.easyexcel.export;

import com.zhaogang.other.excel.easyexcel.export.handler.AutoMergeStrategy;
import com.zhaogang.other.excel.easyexcel.export.handler.BigNumHandler;
import com.zhaogang.other.excel.easyexcel.export.handler.CommentWriteHandler;
import com.zhaogang.other.excel.easyexcel.export.handler.CustomAutoUpperMergeStrategy;
import com.zhaogang.other.excel.easyexcel.export.handler.CustomCellWriteHandler;
import com.zhaogang.other.excel.easyexcel.export.handler.CustomSheetWriteHandler;
import com.zhaogang.other.excel.easyexcel.export.model.ComplexHeadData;
import com.zhaogang.other.excel.easyexcel.export.model.ConverterData;
import com.zhaogang.other.excel.easyexcel.export.model.DemoMergeData;
import com.zhaogang.other.excel.easyexcel.export.model.DemoModel;
import com.zhaogang.other.excel.easyexcel.export.model.DemoModelWithInt;
import com.zhaogang.other.excel.easyexcel.export.model.DemoStyleData;
import com.zhaogang.other.excel.easyexcel.export.model.ImageData;
import com.zhaogang.other.excel.easyexcel.export.model.IndexData;
import com.zhaogang.other.excel.easyexcel.export.model.LongestMatchColumnWidthData;
import com.zhaogang.other.excel.easyexcel.export.model.WidthAndHeightData;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.streaming.SXSSFSheet;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.util.FileUtils;
import com.alibaba.excel.write.merge.LoopMergeStrategy;
import com.alibaba.excel.write.merge.OnceAbsoluteMergeStrategy;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author weiguo.liu
 * @date 2020/12/31
 * @description excel导出类型，写操作
 */
public class ExportMain {

    private static final String BASE_PATH = "C:\\Users\\jacks\\Desktop\\excel\\";

    public static void main(String[] args) throws Exception {
        // 1. 直接往指定class中写
        // simpleWrite();
        // 2. 指定排除或包含某些列
        // excludeOrIncludeWrite();
        // 3. 通过索引和属性值注解指定关系
        // indexWrite();
        // 4. 复杂表头写入
        // complexHeadWrite();
        // 5. 重复写
        // repeatedWrite();
        // 5.1 使用流进行重复写
        // repeatedWriteWithStream();
        // 6. 自定义格式化的写
        // converterWrite();
        // 7. 合并单元格
        // mergeWrite();
        // 7.1 自定义合并 todo:需要关注
        // mergeCustomWrite();
        // 8. 动态写头 todo:需要关注
        // dynamicHeadWrite();
        // 9. 不指定 excelModel 写
        // noModelWrite();

        // 10. 自定义样式
        // handlerStyleWrite();
        // 11. 自动列宽
        // longestMatchColumnWidthWrite();
        // 12. 自定义拦截器对单元格做处理，可以高度定制化 todo:需要关注
        customHandlerWrite();
    }

    /**
     * 最简单的写
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoModel}
     * <p>
     * 2. 直接写即可 @see DemoModel
     */
    public static void simpleWrite() throws FileNotFoundException {
        // 写法1
        String fileName = BASE_PATH + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        // 1.1 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, DemoModel.class).sheet("模板").doWrite(data());
        // 1.2 用文件流来承载数据
        // FileOutputStream os = new FileOutputStream(fileName);
        // EasyExcel.write(os, DemoModel.class).sheet("t1").doWrite(data());

        //
        // // 写法2
        // fileName = BASE_PATH + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        // // 这里 需要指定写用哪个class去写
        // ExcelWriter excelWriter = null;
        // try {
        // excelWriter = EasyExcel.write(fileName, DemoModel.class).build();
        // WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
        // excelWriter.write(data(), writeSheet);
        // } finally {
        // // 千万别忘记finish 会帮忙关闭流
        // if (excelWriter != null) {
        // excelWriter.finish();
        // }
        // }

        // // 写法3
        // // 这里 需要指定写用哪个class去写
        // ExcelWriter excelWriter = null;
        // try {
        // FileOutputStream fos = new FileOutputStream(fileName);
        // excelWriter = EasyExcel.write(fos).build();
        //
        // WriteSheet writeSheet1 = EasyExcel.writerSheet("template1").build();
        // excelWriter.write(data(), writeSheet1);
        //
        // WriteSheet writeSheet2 = EasyExcel.writerSheet("template2").build();
        // excelWriter.write(data(), writeSheet2);
        //
        // } catch (Exception e) {
        // e.printStackTrace();
        // } finally {
        // // 千万别忘记finish 会帮忙关闭流
        // if (excelWriter != null) {
        // excelWriter.finish();
        // }
        // }

    }

    /**
     * 根据参数只导出指定列
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoModel}
     * <p>
     * 2. 根据自己或者排除自己需要的列
     * <p>
     * 3. 直接写即可
     *
     * @since 2.1.1
     */
    public static void excludeOrIncludeWrite() {
        String fileName = BASE_PATH + "excludeOrIncludeWrite" + System.currentTimeMillis() + ".xlsx";
        //
        // // 根据用户传入字段 假设我们要忽略 date，前提是所有的列都写
        // Set<String> excludeColumnFiledNames = new HashSet<>();
        // excludeColumnFiledNames.add("id");
        // // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // EasyExcel.write(fileName, DemoModel.class).excludeColumnFiledNames(excludeColumnFiledNames).sheet("模板")
        // .doWrite(data());

        fileName = BASE_PATH + "excludeOrIncludeWrite" + System.currentTimeMillis() + ".xlsx";
        // 根据用户传入字段 假设我们只要导出 date，前提是所有的列都被排除
        Set<String> includeColumnFiledNames = new HashSet<>();
        includeColumnFiledNames.add("name");
        includeColumnFiledNames.add("age");
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, DemoModel.class).includeColumnFiledNames(includeColumnFiledNames).sheet("模板")
            .doWrite(data());
    }

    /**
     * 指定写入的列，通过索引注解指定列和属性的关系
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link IndexData}
     * <p>
     * 2. 使用{@link ExcelProperty}注解指定写入的列
     * <p>
     * 3. 直接写即可
     */
    public static void indexWrite() {
        String fileName = BASE_PATH + "indexWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, IndexData.class).sheet("模板").doWrite(data());
    }

    /**
     * 复杂头写入
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link ComplexHeadData}
     * <p>
     * 2. 使用{@link ExcelProperty}注解指定复杂的头
     * <p>
     * 3. 直接写即可
     */
    public static void complexHeadWrite() {
        String fileName = BASE_PATH + "complexHeadWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, ComplexHeadData.class).sheet("模板").doWrite(data());
    }

    /**
     * 重复多次写入
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link ComplexHeadData}
     * <p>
     * 2. 使用{@link ExcelProperty}注解指定复杂的头
     * <p>
     * 3. 直接调用二次写入即可
     */
    public static void repeatedWrite() throws Exception {
        // 方法1 如果写到同一个sheet
        String fileName = BASE_PATH + "repeatedWrite" + System.currentTimeMillis() + ".xlsx";
        ExcelWriter excelWriter = null;
        // try {
        // // 这里 需要指定写用哪个class去写
        // excelWriter = EasyExcel.write(fileName, DemoModel.class).build();
        // // 这里注意 如果同一个sheet只要创建一次
        // WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
        // // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来
        // for (int i = 0; i < 5; i++) {
        // // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
        // List<DemoModel> data = data();
        // excelWriter.write(data, writeSheet);
        // }
        // } finally {
        // // 千万别忘记finish 会帮忙关闭流
        // if (excelWriter != null) {
        // excelWriter.finish();
        // }
        // }
        //
        // // 方法2 如果写到不同的sheet 同一个对象
        // fileName = BASE_PATH + "repeatedWrite" + System.currentTimeMillis() + ".xlsx";
        // try {
        // // 这里 指定文件
        // excelWriter = EasyExcel.write(fileName, DemoModel.class).build();
        // // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来。这里最终会写到5个sheet里面
        // for (int i = 0; i < 5; i++) {
        // // 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样
        // WriteSheet writeSheet = EasyExcel.writerSheet(i, "模板" + i).build();
        // // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
        // List<DemoModel> data = data();
        // excelWriter.write(data, writeSheet);
        // }
        // } finally {
        // // 千万别忘记finish 会帮忙关闭流
        // if (excelWriter != null) {
        // excelWriter.finish();
        // }
        // }

        // 方法3 如果写到不同的sheet 不同的对象
        fileName = BASE_PATH + "repeatedWrite" + System.currentTimeMillis() + ".xlsx";
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        FileOutputStream fos = new FileOutputStream(fileName);
        try {
            // 这里 指定文件
            excelWriter = EasyExcel.write(os).build();
            // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来。这里最终会写到5个sheet里面
            for (int i = 0; i < 5; i++) {
                // 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样。这里注意DemoModel.class 可以每次都变，我这里为了方便 所以用的同一个class
                // 实际上可以一直变，这里的sheetNo可以不穿
                // WriteSheet writeSheet = EasyExcel.writerSheet(i, "模板" + i).head(DemoModel.class).build();
                WriteSheet writeSheet = EasyExcel.writerSheet("模板" + i).head(DemoModel.class).build();
                if (i > 2) {
                    excelWriter.write(Collections.emptyList(), writeSheet);
                } else {
                    // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
                    excelWriter.write(data(), writeSheet);
                }
            }
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }

        fos.write(os.toByteArray());
    }

    public static void repeatedWriteWithStream() {
        String fileName = BASE_PATH + "repeatedWrite" + System.currentTimeMillis() + ".xlsx";
        ExcelWriter excelWriter = null;
        // 方法3 如果写到不同的sheet 不同的对象
        fileName = BASE_PATH + "writeData" + ".xlsx";
        // try {
        // FileOutputStream os = new FileOutputStream(fileName);
        // // 这里 指定文件
        // excelWriter = EasyExcel.write(os).build();
        // // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来。这里最终会写到5个sheet里面
        // for (int i = 0; i < 5; i++) {
        // // 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样。这里注意DemoModel.class 可以每次都变，我这里为了方便 所以用的同一个class
        // // 实际上可以一直变，这里的sheetNo可以不穿
        // WriteSheet writeSheet = EasyExcel.writerSheet(i, "模板" + i).head(DemoModel.class).build();
        // // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
        // List<DemoModel> data = data();
        // excelWriter.write(data, writeSheet);
        // }
        // } catch (Exception e) {
        // e.printStackTrace();
        // } finally {
        // // 千万别忘记finish 会帮忙关闭流
        // if (excelWriter != null) {
        // excelWriter.finish();
        // }
        // }

        // try (FileOutputStream os = new FileOutputStream(fileName)) {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            // 这里 指定文件
            excelWriter = EasyExcel.write(os).build();
            // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来。这里最终会写到5个sheet里面
            for (int i = 0; i < 5; i++) {
                // 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样。这里注意DemoModel.class 可以每次都变，我这里为了方便 所以用的同一个class
                // 实际上可以一直变，这里的sheetNo可以不穿
                WriteSheet writeSheet = EasyExcel.writerSheet(i, "模板" + i).head(DemoModel.class).build();
                // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
                List<DemoModel> data = data();
                excelWriter.write(data, writeSheet);
            }

            // 这个拿到输出流去干坏事
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }

    }

    /**
     * 日期、数字或者自定义格式转换
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link ConverterData}
     * <p>
     * 2. 使用{@link ExcelProperty}配合使用注解{@link DateTimeFormat}、{@link NumberFormat}或者自定义注解
     * <p>
     * 3. 直接写即可
     */
    public static void converterWrite() {
        String fileName = BASE_PATH + "converterWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, ConverterData.class).sheet("模板").doWrite(cdata());
    }

    private static List<ConverterData> cdata() {
        List<ConverterData> list = new ArrayList<>();
        ConverterData converterData = new ConverterData();
        converterData.setDoubleData(999999990.99);
        list.add(converterData);

        return list;
    }

    /**
     * 合并单元格
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoModel} {@link DemoMergeData}
     * <p>
     * 2. 创建一个merge策略 并注册
     * <p>
     * 3. 直接写即可 todo: 需要自定义合并策略
     *
     * @since 2.2.0-beta1 february
     */
    public static void mergeWrite() {
        // 方法1 注解
        String fileName = BASE_PATH + "mergeWrite" + System.currentTimeMillis() + ".xlsx";
        // 在DemoStyleData里面加上ContentLoopMerge注解
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // EasyExcel.write(fileName, DemoMergeData.class).sheet("模板").doWrite(data());

        // 方法2 自定义合并单元格策略
        fileName = BASE_PATH + "mergeWrite" + System.currentTimeMillis() + ".xlsx";
        // 每隔2行会合并 把eachColumn 设置成 3 也就是我们数据的长度，所以就第一列会合并。当然其他合并策略也可以自己写
        LoopMergeStrategy loopMergeStrategy = new LoopMergeStrategy(2, 0);
        // OnceAbsoluteMergeStrategy 是一次性策略，可同时注册多个
        // OnceAbsoluteMergeStrategy onceAbsoluteMergeStrategy = new OnceAbsoluteMergeStrategy();
        // 自定义合并单元格策略
        AutoMergeStrategy autoMergeStrategy = new AutoMergeStrategy(Arrays.asList(new Integer[] {1, 2}), 3);
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, DemoModel.class).registerWriteHandler(autoMergeStrategy).sheet("模板")
            .doWrite(loopColumnData());
    }

    /**
     * 自定义合并，测试通过
     */
    public static void mergeCustomWrite() {
        // 生成模拟数据
        List<String> fields = new ArrayList<>();
        fields.add("id");
        fields.add("name");
        fields.add("age");
        fields.add("price");
        fields.add("createTime");
        fields.add("extra");

        List<String> container = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            DemoModelWithInt demoModel = new DemoModelWithInt();
            demoModel.setName("name" + i / 3);
            demoModel.setId(i / 3);
            demoModel.setAge(i / 2);
            demoModel.setPrice(new BigDecimal("123456.190"));
            demoModel.setCreateTime(new Date());
            demoModel.setExtra("1234567898017037091.1908109829123");
            container.add(JSON.toJSONString(demoModel));
        }

        List<String> head1 = Collections.singletonList("唯一标识");
        List<String> head2 = Collections.singletonList("姓名");
        List<String> head3 = Collections.singletonList("年龄");
        List<String> head4 = Collections.singletonList("价格");
        List<String> head5 = Collections.singletonList("生产日期");
        List<String> head6 = Collections.singletonList("扩展属性");
        List<List<String>> headers = Arrays.asList(head1, head2, head3, head4, head5, head6);

        int rowNum = container.size();
        int columnNum = fields.size();
        Object[][] content = new Object[rowNum + 1][columnNum];
        // 行维度组装数据，由于标题的存在，行号自动加1
        List<List<Object>> dataContent = new ArrayList<>(columnNum);
        for (int i = 1; i < rowNum; i++) {
            List<Object> rowData = new ArrayList<>(columnNum);
            // 行号加1后，原始数这里需要回退
            String jsonStr = container.get(i - 1);
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            for (int j = 0; j < columnNum; j++) {
                // 获取指定导出属性
                Object data = jsonObject.get(fields.get(j));
                content[i][j] = data;
                rowData.add(data);
                if (j == columnNum - 1) {
                    dataContent.add(rowData);
                }
            }
        }

        System.out.println(content);

        // 方法2 自定义合并单元格策略
        String fileName = BASE_PATH + "mergeWrite" + System.currentTimeMillis() + ".xlsx";
        // 自定义合并单元格策略
        Map<Integer, List<Integer>> mergeCon = new HashMap<>();
        // mergeCon.put(1, Arrays.asList(new Integer[] {1}));
        // mergeCon.put(2, Arrays.asList(new Integer[] {1}));
        mergeCon.put(1, Collections.emptyList());

        CustomAutoUpperMergeStrategy customAutoMergeStrategy = new CustomAutoUpperMergeStrategy(1, mergeCon, content);

        long start = System.currentTimeMillis();
        // 这里不需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName).head(headers) // 动态指定头
            .registerWriteHandler(customAutoMergeStrategy) // 指定合并策略
            .registerWriteHandler(getCustomCellStyle()) // 自定义样式
            .sheet("模板").doWrite(dataContent);
        System.out.println(">> time took -> " + (System.currentTimeMillis() - start) + " ms");
    }

    /**
     * 动态头，实时生成头写入
     * <p>
     * 思路是这样子的，先创建List<String>头格式的sheet仅仅写入头,然后通过table 不写入头的方式 去写入数据
     *
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoModel}
     * <p>
     * 2. 然后写入table即可
     */
    public static void dynamicHeadWrite() throws Exception {
        // 定义合并策略，索引位置从0开始
        OnceAbsoluteMergeStrategy ms1 = new OnceAbsoluteMergeStrategy(1, 3, 1, 1);

        String fileName = BASE_PATH + "dynamicHeadWrite" + System.currentTimeMillis() + ".xlsx";

        // OutputStream os = new FileOutputStream(new File(fileName));
        // ByteArrayOutputStream bos = new ByteArrayOutputStream();
        // Writer writer = new OutputStreamWriter(os, "UTF-8");
        // BufferedWriter bw = new BufferedWriter(writer);

        EasyExcel.write(fileName)
            // 这里放入动态头
            .head(head()).sheet("模板").registerWriteHandler(ms1)
            // 当然这里数据也可以用 List<List<String>> 去传入
            .doWrite(getJsonData());
        // .doWrite(data());
    }

    /**
     * 按照行维度划分数据
     * 
     * @return
     */
    private static List<List<String>> getJsonData() {
        List<List<String>> json = new ArrayList<>();

        // 一行数据
        List<String> list1 = new ArrayList<>();
        list1.add("data11");
        list1.add("data12");
        list1.add("data13");
        // 二行数据
        List<String> list2 = new ArrayList<>();
        list2.add("data21");
        list2.add("data22");
        list2.add("data23");
        // 三行数据
        List<String> list3 = new ArrayList<>();
        list3.add("data31");
        list3.add("data32");
        list3.add("data33");

        json.add(list1);
        json.add(list2);
        json.add(list3);

        return json;
    }

    /**
     * 不创建对象的写，列对象或者头对象超出的话需要自己限制
     */
    public static void noModelWrite() {
        // 写法1
        String fileName = BASE_PATH + "noModelWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName).head(head()).sheet("模板").doWrite(dataList());
    }

    private static List<List<String>> head() {
        List<List<String>> list = new ArrayList<>();

        List<String> head0 = new ArrayList<>();
        head0.add("字符串" + System.currentTimeMillis());
        // 在一层头下面追加二层头
        // head0.add("字符串@" + System.currentTimeMillis());

        List<String> head1 = new ArrayList<>();
        head1.add("数字" + System.currentTimeMillis());
        // head1.add("数字@" + System.currentTimeMillis());

        List<String> head2 = new ArrayList<>();
        head2.add("日期" + System.currentTimeMillis());
        // head2.add("日期@" + System.currentTimeMillis());

        list.add(head0);
        list.add(head1);
        list.add(head2);
        return list;
    }

    private static List<DemoModel> loopColumnData() {
        List<DemoModel> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoModel data = new DemoModel();
            data.setId("ID: " + i);
            data.setName("Name: " + i / 3);
            data.setAge("Age: " + i / 3);
            list.add(data);
        }
        return list;
    }

    private static List<DemoModel> data() {
        List<DemoModel> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoModel data = new DemoModel();
            data.setId("ID: " + i);
            data.setName("Name: " + i);
            data.setAge("Age: " + i);
            list.add(data);
        }
        return list;
    }

    private static List<List<Object>> dataList() {
        List<List<Object>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<Object> data = new ArrayList<>();
            data.add("字符串" + i);
            data.add(new Date());
            data.add(0.56);
            data.add("扩展列");
            list.add(data);
        }
        return list;
    }

    /**
     * 拦截器形式自定义样式
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoModel}
     * <p>
     * 2. 创建一个style策略 并注册
     * <p>
     * 3. 直接写即可
     */
    public static void handlerStyleWrite() {
        String fileName = BASE_PATH + "handlerStyleWrite" + System.currentTimeMillis() + ".xlsx";

        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, DemoModel.class)
            // .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
            .registerWriteHandler(getCustomCellStyle()).sheet("模板").doWrite(data());
    }

    private static HorizontalCellStyleStrategy getCustomCellStyle() {
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为红色
        // headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        // 设置字体
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short)12);
        headWriteFont.setBold(false);
        headWriteFont.setColor(Font.COLOR_RED);
        // headWriteFont.setTypeOffset(Font.SS_SUPER);
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 设置头居中
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);

        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
        // contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        // 背景绿色
        // contentWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        // 字体大小
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontHeightInPoints((short)12);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        // 设置内容居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 设置自动换行
        contentWriteCellStyle.setWrapped(true);

        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy cellStyleStrategy =
            new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

        return cellStyleStrategy;
    }

    /**
     * 自动列宽(不太精确)
     * <p>
     * 这个目前不是很好用，比如有数字就会导致换行。而且长度也不是刚好和实际长度一致。 所以需要精确到刚好列宽的慎用。 当然也可以自己参照
     * {@link LongestMatchColumnWidthStyleStrategy}重新实现.
     * <p>
     * poi 自带{@link SXSSFSheet#autoSizeColumn(int)} 对中文支持也不太好。目前没找到很好的算法。 有的话可以推荐下。
     *
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link LongestMatchColumnWidthData}
     * <p>
     * 2. 注册策略{@link LongestMatchColumnWidthStyleStrategy}
     * <p>
     * 3. 直接写即可
     */
    public static void longestMatchColumnWidthWrite() {
        String fileName = BASE_PATH + "longestMatchColumnWidthWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, LongestMatchColumnWidthData.class)
            .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).sheet("模板").doWrite(dataLong());
    }

    private static List<LongestMatchColumnWidthData> dataLong() {
        List<LongestMatchColumnWidthData> list = new ArrayList<LongestMatchColumnWidthData>();
        for (int i = 0; i < 10; i++) {
            LongestMatchColumnWidthData data = new LongestMatchColumnWidthData();
            data.setString("测试很长的字符串测试很长的字符串测试很长的字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(1000000000000.0);
            list.add(data);
        }
        return list;
    }

    /**
     * 下拉，超链接等自定义拦截器（上面几点都不符合但是要对单元格进行操作的参照这个）
     * <p>
     * demo这里实现2点。1. 对第一行第一列的头超链接到:https://github.com/alibaba/easyexcel 2. 对第一列第一行和第二行的数据新增下拉框，显示 测试1 测试2
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoModel}
     * <p>
     * 2. 注册拦截器 {@link CustomCellWriteHandler} {@link CustomSheetWriteHandler}
     * <p>
     * 2. 直接写即可
     */
    public static void customHandlerWrite() {
        String fileName = BASE_PATH + "customHandlerWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, DemoModel.class).registerWriteHandler(new CustomSheetWriteHandler())
            .registerWriteHandler(new CustomCellWriteHandler())
             .registerWriteHandler(new BigNumHandler())
            .sheet("模板").doWrite(dataWithExtend());
    }

    private static List<DemoModel> dataWithExtend() {
        List<DemoModel> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            DemoModel data = new DemoModel();
            data.setId("ID: " + i);
            data.setName("Name: " + i);
            data.setAge("Age: " + i);
            data.setIntegerValue(1234567890);
            data.setLongValue(12345678901234L);
            data.setFloatValue(8921.1234567F);
            data.setDoubleValue(40044.000000);
//            data.setBigDecimal(new BigDecimal("40044.000000"));
            data.setBigDecimal(new BigDecimal("2000000000.0000"));
            list.add(data);
        }
        return list;
    }

    /**
     * 根据模板写入
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link IndexData}
     * <p>
     * 2. 使用{@link ExcelProperty}注解指定写入的列
     * <p>
     * 3. 使用withTemplate 写取模板
     * <p>
     * 4. 直接写即可
     */
    public void templateWrite() {
        String templateFileName = BASE_PATH + "demo" + File.separator + "demo.xlsx";
        String fileName = BASE_PATH + "templateWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, DemoModel.class).withTemplate(templateFileName).sheet().doWrite(data());
    }

    /**
     * 列宽、行高
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link WidthAndHeightData}
     * <p>
     * 2. 使用注解{@link ColumnWidth}、{@link HeadRowHeight}、{@link ContentRowHeight}指定宽度或高度
     * <p>
     * 3. 直接写即可
     */

    public void widthAndHeightWrite() {
        String fileName = BASE_PATH + "widthAndHeightWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, WidthAndHeightData.class).sheet("模板").doWrite(data());
    }

    /**
     * 注解形式自定义样式
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoStyleData}
     * <p>
     * 3. 直接写即可
     *
     * @since 2.2.0-beta1
     */
    public void annotationStyleWrite() {
        String fileName = BASE_PATH + "annotationStyleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, DemoStyleData.class).sheet("模板").doWrite(data());
    }

    /**
     * 插入批注
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoModel}
     * <p>
     * 2. 注册拦截器 {@link CommentWriteHandler}
     * <p>
     * 2. 直接写即可
     */
    public void commentWrite() {
        String fileName = BASE_PATH + "commentWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 这里要注意inMemory 要设置为true，才能支持批注。目前没有好的办法解决 不在内存处理批注。这个需要自己选择。
        EasyExcel.write(fileName, DemoModel.class).inMemory(Boolean.TRUE)
            .registerWriteHandler(new CommentWriteHandler()).sheet("模板").doWrite(data());
    }

    /**
     * 可变标题处理(包括标题国际化等)
     * <p>
     * 简单的说用List<List<String>>的标题 但是还支持注解
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link ConverterData}
     * <p>
     * 2. 直接写即可
     */
    public void variableTitleWrite() {
        // 写法1
        String fileName = BASE_PATH + "variableTitleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, ConverterData.class).head(variableTitleHead()).sheet("模板").doWrite(data());
    }

    /**
     * 图片导出
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link ImageData}
     * <p>
     * 2. 直接写即可
     */
    public void imageWrite() throws Exception {
        String fileName = BASE_PATH + "imageWrite" + System.currentTimeMillis() + ".xlsx";
        // 如果使用流 记得关闭
        InputStream inputStream = null;
        try {
            List<ImageData> list = new ArrayList<ImageData>();
            ImageData imageData = new ImageData();
            list.add(imageData);
            String imagePath = BASE_PATH + "converter" + File.separator + "img.jpg";
            // 放入五种类型的图片 实际使用只要选一种即可
            imageData.setByteArray(FileUtils.readFileToByteArray(new File(imagePath)));
            imageData.setFile(new File(imagePath));
            imageData.setString(imagePath);
            inputStream = FileUtils.openInputStream(new File(imagePath));
            imageData.setInputStream(inputStream);
            imageData.setUrl(new URL(
                "https://raw.githubusercontent.com/alibaba/easyexcel/master/src/test/resources/converter/img.jpg"));
            EasyExcel.write(fileName, ImageData.class).sheet().doWrite(list);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    /**
     * 使用table去写入
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoModel}
     * <p>
     * 2. 然后写入table即可
     */
    public void tableWrite() {
        String fileName = BASE_PATH + "tableWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里直接写多个table的案例了，如果只有一个 也可以直一行代码搞定，参照其他案例
        // 这里 需要指定写用哪个class去写
        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write(fileName, DemoModel.class).build();
            // 把sheet设置为不需要头 不然会输出sheet的头 这样看起来第一个table 就有2个头了
            WriteSheet writeSheet = EasyExcel.writerSheet("模板").needHead(Boolean.FALSE).build();
            // 这里必须指定需要头，table 会继承sheet的配置，sheet配置了不需要，table 默认也是不需要
            WriteTable writeTable0 = EasyExcel.writerTable(0).needHead(Boolean.TRUE).build();
            WriteTable writeTable1 = EasyExcel.writerTable(1).needHead(Boolean.TRUE).build();
            // 第一次写入会创建头
            excelWriter.write(data(), writeSheet, writeTable0);
            // 第二次写如也会创建头，然后在第一次的后面写入数据
            excelWriter.write(data(), writeSheet, writeTable1);
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    private List<List<String>> variableTitleHead() {
        List<List<String>> list = new ArrayList<>();
        List<String> head0 = new ArrayList<>();
        head0.add("string" + System.currentTimeMillis());
        List<String> head1 = new ArrayList<>();
        head1.add("number" + System.currentTimeMillis());
        List<String> head2 = new ArrayList<>();
        head2.add("date" + System.currentTimeMillis());
        list.add(head0);
        list.add(head1);
        list.add(head2);
        return list;
    }

}
