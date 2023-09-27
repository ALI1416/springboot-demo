package cn.z.util;

/**
 * <h1>IP工具</h1>
 *
 * <p>
 * createDate 2023/08/31 11:25:02
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class IpUtils {

    private IpUtils() {
    }

    /* ==================== IP ==================== */
    // region IP

    /**
     * IP地址转long
     *
     * @param ip IP地址
     * @return long型IP地址
     * @throws IllegalArgumentException ip {@value EMPTY_STRING} {@value IP_NOT_VALID}
     */
    public static long ip2long(String ip) {
        checkEmptyString(ip);
        String[] s = ip.split("\\.");
        if (s.length != 4) {
            throw new IllegalArgumentException(IP_NOT_VALID);
        }
        long address = 0;
        for (int i = 0; i < 4; i++) {
            long v = Long.parseLong(s[i]);
            if (v < 0 || v > 255) {
                throw new IllegalArgumentException(IP_NOT_VALID);
            }
            address |= (v << 8 * (3 - i));
        }
        return address;
    }

    /**
     * long转IP地址
     *
     * @param ip long型IP地址
     * @return IP地址
     * @throws IllegalArgumentException ip {@value IP_NOT_VALID}
     */
    public static String long2ip(long ip) {
        if (!isValidIp(ip)) {
            throw new IllegalArgumentException(IP_NOT_VALID);
        }
        return ((ip >> 24) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip) & 0xFF);
    }

    /**
     * 是合法的IP地址
     *
     * @param ip IP地址
     * @return 是否合法
     */
    public static boolean isValidIp(String ip) {
        if (ip == null || ip.isEmpty()) {
            return false;
        }
        String[] s = ip.split("\\.");
        if (s.length != 4) {
            return false;
        }
        for (int i = 0; i < 4; i++) {
            int v = Integer.parseInt(s[i]);
            if (v < 0 || v > 255) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是合法的IP地址
     *
     * @param ip long型IP地址
     * @return 是否合法
     */
    public static boolean isValidIp(long ip) {
        return ip >= 0 && ip <= 0xFFFFFFFFL;
    }

    // endregion

    /* ==================== 端口 ==================== */
    // region 端口

    /**
     * 是合法的端口号
     *
     * @param port 端口号
     * @return 是否合法
     */
    public static boolean isValidPort(int port) {
        return port >= 0 && port <= 65535;
    }

    // endregion

    /* ==================== 子网掩码 ==================== */
    // region 子网掩码

    /**
     * 子网掩码转long
     *
     * @param mask 子网掩码
     * @return long型子网掩码
     * @throws IllegalArgumentException mask {@value EMPTY_STRING} {@value MASK_NOT_VALID}
     */
    public static long mask2long(String mask) {
        checkEmptyString(mask);
        for (int i = 0; i < MASK.length; i++) {
            if (MASK[i].equals(mask)) {
                return MASK_LONG[i];
            }
        }
        throw new IllegalArgumentException(MASK_NOT_VALID);
    }

    /**
     * 子网掩码转子网掩码位
     *
     * @param mask 子网掩码
     * @return 子网掩码位
     * @throws IllegalArgumentException mask {@value EMPTY_STRING} {@value MASK_NOT_VALID}
     */
    public static int mask2maskBit(String mask) {
        checkEmptyString(mask);
        for (int i = 0; i < MASK.length; i++) {
            if (MASK[i].equals(mask)) {
                return i + 1;
            }
        }
        throw new IllegalArgumentException(MASK_NOT_VALID);
    }

    /**
     * long转子网掩码
     *
     * @param mask long型子网掩码
     * @return 子网掩码
     * @throws IllegalArgumentException mask {@value MASK_NOT_VALID}
     */
    public static String long2mask(long mask) {
        for (int i = 0; i < MASK_LONG.length; i++) {
            if (MASK_LONG[i] == mask) {
                return MASK[i];
            }
        }
        throw new IllegalArgumentException(MASK_NOT_VALID);
    }

    /**
     * long转子网掩码位
     *
     * @param mask long型子网掩码
     * @return 子网掩码位
     * @throws IllegalArgumentException mask {@value MASK_NOT_VALID}
     */
    public static int long2maskBit(long mask) {
        for (int i = 0; i < MASK_LONG.length; i++) {
            if (MASK_LONG[i] == mask) {
                return i + 1;
            }
        }
        throw new IllegalArgumentException(MASK_NOT_VALID);
    }

    /**
     * 子网掩码位转子网掩码
     *
     * @param maskBit 子网掩码位
     * @return 子网掩码
     * @throws IllegalArgumentException maskBit {@value MASK_NOT_VALID}
     */
    public static String maskBit2mask(int maskBit) {
        if (!isValidMask(maskBit)) {
            throw new IllegalArgumentException(MASK_NOT_VALID);
        }
        return MASK[maskBit - 1];
    }

    /**
     * 子网掩码位转long
     *
     * @param maskBit 子网掩码位
     * @return long型子网掩码
     * @throws IllegalArgumentException maskBit {@value MASK_NOT_VALID}
     */
    public static long maskBit2long(int maskBit) {
        if (!isValidMask(maskBit)) {
            throw new IllegalArgumentException(MASK_NOT_VALID);
        }
        return MASK_LONG[maskBit - 1];
    }

    /**
     * 是合法的子网掩码
     *
     * @param mask 子网掩码
     * @return 是否合法
     */
    public static boolean isValidMask(String mask) {
        if (mask == null || mask.isEmpty()) {
            return false;
        }
        for (String s : MASK) {
            if (s.equals(mask)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是合法的long型子网掩码
     *
     * @param mask long型子网掩码
     * @return 是否合法
     */
    public static boolean isValidMask(long mask) {
        for (long l : MASK_LONG) {
            if (l == mask) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是合法的子网掩码位
     *
     * @param maskBit 子网掩码位
     * @return 是否合法
     */
    public static boolean isValidMask(int maskBit) {
        return maskBit >= 1 && maskBit <= 32;
    }

    // endregion

    /* ==================== 私有 ==================== */
    // region 私有

    /**
     * 子网掩码
     */
    private static final String[] MASK = {
            "128.0.0.0", "192.0.0.0", "224.0.0.0", "240.0.0.0", "248.0.0.0", "252.0.0.0", "254.0.0.0", "255.0.0.0",
            "255.128.0.0", "255.192.0.0", "255.224.0.0", "255.240.0.0", "255.248.0.0", "255.252.0.0", "255.254.0.0", "255.255.0.0",
            "255.255.128.0", "255.255.192.0", "255.255.224.0", "255.255.240.0", "255.255.248.0", "255.255.252.0", "255.255.254.0", "255.255.255.0",
            "255.255.255.128", "255.255.255.192", "255.255.255.224", "255.255.255.240", "255.255.255.248", "255.255.255.252", "255.255.255.254", "255.255.255.255"
    };
    /**
     * long型子网掩码
     */
    private static final long[] MASK_LONG = {
            0x80000000L, 0xC0000000L, 0xE0000000L, 0xF0000000L, 0xF8000000L, 0xFC000000L, 0xFE000000L, 0xFF000000L,
            0xFF800000L, 0xFFC00000L, 0xFFE00000L, 0xFFF00000L, 0xFFF80000L, 0xFFFC0000L, 0xFFFE0000L, 0xFFFF0000L,
            0xFFFF8000L, 0xFFFFC000L, 0xFFFFE000L, 0xFFFFF000L, 0xFFFFF800L, 0xFFFFFC00L, 0xFFFFFE00L, 0xFFFFFF00L,
            0xFFFFFF80L, 0xFFFFFFC0L, 0xFFFFFFE0L, 0xFFFFFFF0L, 0xFFFFFFF8L, 0xFFFFFFFCL, 0xFFFFFFFEL, 0xFFFFFFFFL
    };

    /**
     * 空字符串
     */
    private static final String EMPTY_STRING = "不可为null或空字符串！";
    /**
     * IP地址不合法
     */
    private static final String IP_NOT_VALID = "IP地址不合法！";
    /**
     * 子网掩码不合法
     */
    private static final String MASK_NOT_VALID = "子网掩码不合法！";

    /**
     * 检查是否为null或空字符串
     *
     * @param string 字符串
     * @throws IllegalArgumentException string {@value EMPTY_STRING}
     */
    private static void checkEmptyString(String string) {
        if (string == null || string.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_STRING);
        }
    }

    // endregion

}
