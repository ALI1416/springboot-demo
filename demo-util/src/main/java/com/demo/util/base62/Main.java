package com.demo.util.base62;

import lombok.extern.slf4j.Slf4j;

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

    public static void main(String[] args) {
        int numberInt = 1234567890;
        String encoderInt = Base62.encode(numberInt);
        log.info("encoderInt:" + encoderInt);
        String encoderIntInverted = Base62.encode(numberInt, Base62.INVERTED_ALPHABET);
        log.info("encoderIntInverted:" + encoderIntInverted);
        log.info("decoderInt:" + Base62.decodeInt(encoderInt));
        log.info("decoderIntInverted:" + Base62.decodeInt(encoderIntInverted, Base62.INVERTED_LOOKUP));
        long numberLong = 1234567890123456789L;
        String encoderLong = Base62.encode(numberLong);
        log.info("encoderLong:" + encoderLong);
        String encoderLongInverted = Base62.encode(numberLong, Base62.INVERTED_ALPHABET);
        log.info("encoderLongInverted:" + encoderLongInverted);
        log.info("decoderLong:" + Base62.decodeLong(encoderLong));
        log.info("decoderLongInverted:" + Base62.decodeLong(encoderLongInverted, Base62.INVERTED_LOOKUP));
        log.info("isValid:" + Base62.isValid("1234"));
        log.info("isValid:" + Base62.isValid("123="));
        log.info("encodeToString:" + Base62.encodeToString("爱上对方过后就哭了"));
        log.info("decodeToString:" + Base62.decodeToString("2pqkyr1c15yT4DHD0x0iXRowCONHxu1pisZja"));
    }

}
