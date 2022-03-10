package com.demo.hutool.net;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * <h1>网络</h1>
 *
 * <p>
 * createDate 2022/03/10 15:27:37
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        /*网络工具-NetUtil*/
        log.info("根据long值获取ipv4地址:" + NetUtil.longToIpv4(2130706433L));
        log.info("根据ip地址计算出long型的数据:" + NetUtil.ipv4ToLong("127.0.0.1"));
        log.info("检测本地端口可用性:" + NetUtil.isUsableLocalPort(8080));
        log.info("是否为有效的端口:" + NetUtil.isValidPort(12345));
        log.info("判定是否为内网IP:" + NetUtil.isInnerIP("123.45.67.89"));
        log.info("获得本机的IPv4地址列表:" + NetUtil.localIpv4s());
        log.info("隐藏掉IP地址的最后一部分为*代替:" + NetUtil.hideIpPart("127.0.0.1"));
        log.info("通过域名得到IP:" + NetUtil.getIpByHost("www.baidu.com"));

        /*URL生成器-UrlBuilder*/
        // 构建
        // https://www.hutool.cn/s?ie=UTF-8&ie=GBK&wd=%E6%B5%8B%E8%AF%95
        String buildUrl = UrlBuilder.create()//
                .setScheme("https")//
                .setHost("www.hutool.cn")//
                .addPath("/s")//
                .addQuery("ie", "UTF-8")//
                .addQuery("ie", "GBK")//
                .addQuery("wd", "测试")//
                .build();
        log.info("URL生成器:" + buildUrl);
        // 解析
        UrlBuilder builder = UrlBuilder.ofHttp("www.hutool.cn/aaa/bbb/?a=张三&b=%e6%9d%8e%e5%9b%9b#frag1",
                CharsetUtil.CHARSET_UTF_8);
        log.info("参数:" + builder.getQuery());
    }

}
