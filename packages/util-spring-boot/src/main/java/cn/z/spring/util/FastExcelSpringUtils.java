package cn.z.spring.util;

import cn.idev.excel.FastExcelFactory;
import cn.z.util.FastExcelUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <h1>FastExcel工具</h1>
 *
 * <p>
 * createDate 2021/02/02 14:56:48
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class FastExcelSpringUtils extends FastExcelUtils {

    /**
     * 下载文件到客户端
     *
     * @param <T>      数据类型
     * @param response HttpServletResponse
     * @param name     文件名(自动追加yyyyMMddHHmmssSSS格式的日期时间，以及后缀.xlsx)
     * @param data     提供的数据
     * @param clazz    T.class
     */
    public static <T> void download(HttpServletResponse response, String name, List<T> data, Class<T> clazz) throws IOException {
        name = URLEncoder.encode(name + LocalDateTime.now().format(DATETIME_FORMAT), "UTF-8").replace("\\+", "%20");
        response.setContentType("application/octet-stream;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename*=utf-8''" + name + ".xlsx");
        FastExcelFactory.write(response.getOutputStream(), clazz).registerWriteHandler(style1()).sheet("工作表1").doWrite(data);
    }

    /**
     * 客户端上传文件
     *
     * @param <T>   数据类型
     * @param file  MultipartFile
     * @param data  导出的数据
     * @param clazz T.class
     */
    public static <T> void upload(MultipartFile file, List<T> data, Class<T> clazz) throws IOException {
        MyReadListener<T> listener = new MyReadListener<>();
        FastExcelFactory.read(file.getInputStream(), clazz, listener).sheet().doRead();
        data.addAll(listener.getList());
    }

}
