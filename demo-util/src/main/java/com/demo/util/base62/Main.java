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
        long number = 1234567890123456789L;
        String encoder = Base62.encode(number);
        log.info("encoder:" + encoder);
        String encoderInverted = Base62.encode(number, Base62.INVERTED);
        log.info("encoderInverted:" + encoderInverted);
        log.info("decoder:" + Base62.decodeNumber(encoder));
        log.info("decoderInverted:" + Base62.decodeNumber(encoderInverted, Base62.INVERTED));
        log.info("isValid:" + Base62.isValid(encoder));
        log.info("isValid:" + Base62.isValid("="));
    }

}
