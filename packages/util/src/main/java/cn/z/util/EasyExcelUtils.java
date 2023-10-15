package cn.z.util;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>EasyExcel工具</h1>
 *
 * <p>
 * createDate 2021/02/02 14:56:48
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class EasyExcelUtils {

    /**
     * yyyyMMddHHmmssSSS格式DateTimeFormatter
     */
    public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    /**
     * 写文件
     *
     * @param <T>   数据类型
     * @param path  路径
     * @param data  提供的数据
     * @param clazz T.class
     */
    public static <T> void write(String path, List<T> data, Class<T> clazz) {
        EasyExcelFactory.write(path, clazz).registerWriteHandler(style1()).sheet("工作表1").doWrite(data);
    }

    /**
     * 读文件
     *
     * @param <T>   数据类型
     * @param path  路径
     * @param data  导出的数据
     * @param clazz T.class
     */
    public static <T> void read(String path, List<T> data, Class<T> clazz) {
        MyReadListener<T> listener = new MyReadListener<>();
        EasyExcelFactory.read(path, clazz, listener).sheet().doRead();
        data.addAll(listener.getList());
    }

    /**
     * 样式1
     */
    public static HorizontalCellStyleStrategy style1() {
        /* 表头 */
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景颜色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        // 字体
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontName("宋体");
        headWriteFont.setFontHeightInPoints((short) 14);
        headWriteCellStyle.setWriteFont(headWriteFont);
        /* 内容 */
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 字体
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontName("宋体");
        contentWriteFont.setFontHeightInPoints((short) 12);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }

    /**
     * 读取数据监听器
     */
    public static class MyReadListener<T> extends AnalysisEventListener<T> {

        private final List<T> list = new ArrayList<>();

        @Override
        public void invoke(T data, AnalysisContext context) {
            list.add(data);
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
        }

        /**
         * 获取数据
         */
        public List<T> getList() {
            return list;
        }

    }

}
