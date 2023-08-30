package com.demo.util.base62;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * <h1>Base62</h1>
 *
 * <p>
 * createDate 2023/08/28 15:01:10
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static final String DIY = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    public static final byte[] DIY_ALPHABET = DIY.getBytes(StandardCharsets.UTF_8);
    public static final byte[] DIY_LOOKUP = Base62.lookup(DIY_ALPHABET);

    public static void main(String[] args) {

        /* ==================== int型数字 ==================== */
        log.warn("int型数字");
        int numberInt = 1234567890;
        String encoderInt = Base62.encode(numberInt);
        log.info("encoderInt:" + encoderInt);
        log.info("decoderInt:" + Base62.decodeInt(encoderInt));
        String encoderIntInverted = Base62.encode(numberInt, Base62.INVERTED_ALPHABET);
        log.info("encoderIntInverted:" + encoderIntInverted);
        log.info("decoderIntInverted:" + Base62.decodeInt(encoderIntInverted, Base62.INVERTED_LOOKUP));
        String encoderIntDIY = Base62.encode(numberInt, DIY_ALPHABET);
        log.info("encoderIntDIY:" + encoderIntDIY);
        log.info("decoderIntDIY:" + Base62.decodeInt(encoderIntDIY, DIY_LOOKUP));

        /* ==================== long型数字 ==================== */
        log.warn("long型数字");
        long numberLong = 1234567890123456789L;
        String encoderLong = Base62.encode(numberLong);
        log.info("encoderLong:" + encoderLong);
        log.info("decoderLong:" + Base62.decodeLong(encoderLong));
        String encoderLongInverted = Base62.encode(numberLong, Base62.INVERTED_ALPHABET);
        log.info("encoderLongInverted:" + encoderLongInverted);
        log.info("decoderLongInverted:" + Base62.decodeLong(encoderLongInverted, Base62.INVERTED_LOOKUP));
        String encoderLongDIY = Base62.encode(numberLong, DIY_ALPHABET);
        log.info("encoderLongDIY:" + encoderLongDIY);
        log.info("decoderLongDIY:" + Base62.decodeLong(encoderLongDIY, DIY_LOOKUP));

        /* ==================== 字符串-字符串 ==================== */
        log.warn("字符串-字符串");
        String string = "爱上对方过后就哭了";
        String encodeToString = Base62.encodeToString(string);
        log.info("encodeToString:" + encodeToString);
        log.info("decodeToString:" + Base62.decodeToString(encodeToString));
        String encodeToStringInverted = Base62.encodeToString(string, Base62.INVERTED_ALPHABET);
        log.info("encodeToStringInverted:" + encodeToStringInverted);
        log.info("decodeToStringInverted:" + Base62.decodeToString(encodeToStringInverted, Base62.INVERTED_LOOKUP));
        String encodeToStringDIY = Base62.encodeToString(string, DIY_ALPHABET);
        log.info("encodeToStringDIY:" + encodeToStringDIY);
        log.info("decodeToStringDIY:" + Base62.decodeToString(encodeToStringDIY, DIY_LOOKUP));

        /* ==================== 字符串-byte[] ==================== */
        log.warn("字符串-byte[]");
        byte[] encode = Base62.encode(string);
        log.info("encode:" + Arrays.toString(encode));
        log.info("decode:" + Arrays.toString(Base62.decode(encode)));
        byte[] encodeInverted = Base62.encode(string, Base62.INVERTED_ALPHABET);
        log.info("encodeInverted:" + Arrays.toString(encodeInverted));
        log.info("decodeInverted:" + Arrays.toString(Base62.decode(encodeInverted, Base62.INVERTED_LOOKUP)));
        byte[] encodeDIY = Base62.encode(string, DIY_ALPHABET);
        log.info("encodeDIY:" + Arrays.toString(encodeDIY));
        log.info("decodeDIY:" + Arrays.toString(Base62.decode(encodeDIY, DIY_LOOKUP)));

        /* ==================== byte[]-字符串 ==================== */
        log.warn("byte[]-字符串");
        byte[] bytes = new byte[]{12, 34, 56, 78, 90, 123, -111, -98, -76, -54, -32, -21, 0, 127, -128};
        String encodeToStringByBytes = Base62.encodeToString(bytes);
        log.info("encodeToStringByBytes:" + encodeToStringByBytes);
        log.info("decodeToString:" + Arrays.toString(Base62.decode(encodeToStringByBytes)));
        String encodeToStringByBytesInverted = Base62.encodeToString(bytes, Base62.INVERTED_ALPHABET);
        log.info("encodeToStringByBytesInverted:" + encodeToStringByBytesInverted);
        log.info("decodeToStringInverted:" + Arrays.toString(Base62.decode(encodeToStringByBytesInverted, Base62.INVERTED_LOOKUP)));
        String encodeToStringByBytesDIY = Base62.encodeToString(bytes, DIY_ALPHABET);
        log.info("encodeToStringByBytesDIY:" + encodeToStringByBytesDIY);
        log.info("decodeToStringDIY:" + Arrays.toString(Base62.decode(encodeToStringByBytesDIY, DIY_LOOKUP)));

        /* ==================== byte[]-byte[] ==================== */
        log.warn("byte[]-byte[]");
        byte[] encodeByBytes = Base62.encode(bytes);
        log.info("encodeByBytes:" + Arrays.toString(encodeByBytes));
        log.info("decode:" + Arrays.toString(Base62.decode(encodeByBytes)));
        byte[] encodeByBytesInverted = Base62.encode(bytes, Base62.INVERTED_ALPHABET);
        log.info("encodeByBytesInverted:" + Arrays.toString(encodeByBytesInverted));
        log.info("decodeInverted:" + Arrays.toString(Base62.decode(encodeByBytesInverted, Base62.INVERTED_LOOKUP)));
        byte[] encodeByBytesDIY = Base62.encode(bytes, DIY_ALPHABET);
        log.info("encodeByBytesDIY:" + Arrays.toString(encodeByBytesDIY));
        log.info("decodeDIY:" + Arrays.toString(Base62.decode(encodeByBytesDIY, DIY_LOOKUP)));

        /* ==================== 工具 ==================== */
        log.warn("工具");
        log.info("isValid:" + Base62.isValid(new byte[]{'0', 'A', 'a'}));
        log.info("isValid:" + Base62.isValid(new byte[]{'0', 'A', '-'}));
        log.info("isValid:" + Base62.isValid("0Aa"));
        log.info("isValid:" + Base62.isValid("0A-"));
        byte[] convert = Base62.convert(bytes, 256, 62);
        log.info("convert:" + Arrays.toString(convert));
        log.info("convert:" + Arrays.toString(Base62.convert(convert, 62, 256)));

    }

}
