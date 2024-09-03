package cn.z.influx;

import cn.z.influx.autoconfigure.InfluxProperties;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.InfluxDBClientOptions;
import com.influxdb.client.domain.Bucket;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * <h1>InfluxDB模板</h1>
 *
 * <p>
 * createDate 2024/09/03 11:35:59
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class InfluxTemp {

    private final InfluxDBClient client;

    /**
     * 构造函数(自动注入)
     *
     * @param influxProperties InfluxProperties
     */
    public InfluxTemp(InfluxProperties influxProperties) {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .readTimeout(influxProperties.getReadTimeout())
                .writeTimeout(influxProperties.getWriteTimeout())
                .connectTimeout(influxProperties.getConnectTimeout());
        InfluxDBClientOptions.Builder influxBuilder = InfluxDBClientOptions.builder()
                .url(influxProperties.getUrl())
                .bucket(influxProperties.getBucket())
                .org(influxProperties.getOrg())
                .okHttpClient(okHttpBuilder);
        if (StringUtils.hasLength(influxProperties.getToken())) {
            influxBuilder.authenticateToken(influxProperties.getToken().toCharArray());
        } else if (StringUtils.hasLength(influxProperties.getUsername()) && StringUtils.hasLength(influxProperties.getPassword())) {
            influxBuilder.authenticate(influxProperties.getUsername(), influxProperties.getPassword().toCharArray());
        }
        this.client = InfluxDBClientFactory.create(influxBuilder.build()).setLogLevel(influxProperties.getLogLevel());
    }

    /**
     * 获取所有储存桶
     *
     * @return List Bucket
     */
    public List<Bucket> bucketAll() {
        return client.getBucketsApi().findBuckets();
    }

}
