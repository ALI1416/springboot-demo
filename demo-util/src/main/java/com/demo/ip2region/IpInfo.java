package com.demo.ip2region;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <h1>Ip实体</h1>
 *
 * <p>
 * createDate 2020/11/22 15:43:28
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Getter
@Setter
@ToString
public class IpInfo {

    /**
     * 国家
     */
    private String country;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 区域
     */
    private String area;
    /**
     * isp
     */
    private String isp;

    /**
     * 构造函数
     */
    public IpInfo() {

    }

    /**
     * 构造函数<br>
     * ip2region中的DataBlock.getRegion()转国家/省份/城市/区域/ISP
     *
     * @param region 格式：国家|区域|省份|城市|ISP
     */
    public IpInfo(String region) {
        if (region != null) {
            String[] s = region.split("\\|");
            if (s.length == 5) {
                this.country = "0".equals(s[0]) ? "" : s[0];
                this.area = "0".equals(s[1]) ? "" : s[1];
                this.province = "0".equals(s[2]) ? "" : s[2];
                this.city = "0".equals(s[3]) ? "" : s[3];
                this.isp = "0".equals(s[4]) ? "" : s[4];
            }
        }
    }
}
