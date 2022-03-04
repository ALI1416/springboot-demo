package com.demo.hutool.crypto;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import lombok.extern.slf4j.Slf4j;

/**
 * <h1>加密</h1>
 *
 * <p>
 * createDate 2022/03/04 11:03:55
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
@Slf4j
public class Main {

    public static void main(String[] args) {
        aes();
        rsa();
        md5AndSh1AndBcrypt();
        hMacMd5();
        md5withRsa();
    }

    /**
     * <a href="https://www.hutool.cn/docs/#/crypto/对称加密-SymmetricCrypto">对称加密</a>
     * 以aes为例
     */
    public static void aes() {
        String content = "test中文";
        //随机生成密钥
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        log.info("随机生成密钥Hex:" + HexUtil.encodeHexStr(key));
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
        //加密
        String encryptHex = aes.encryptHex(content);
        log.info("加密:" + encryptHex);
        //解密
        String decryptStr = aes.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
        log.info("解密:" + decryptStr);
    }

    /**
     * <a href="https://www.hutool.cn/docs/#/crypto/非对称加密-AsymmetricCrypto">非对称加密</a>
     * 以rsa为例
     */
    public static void rsa() {
        String str = "test中文";
        byte[] bytes = StrUtil.bytes(str, CharsetUtil.CHARSET_UTF_8);
        RSA rsa = new RSA();
        //获得私钥
        log.info("获得私钥:" + rsa.getPrivateKeyBase64());
        //获得公钥
        log.info("获得公钥:" + rsa.getPublicKeyBase64());
        //公钥加密，私钥解密
        byte[] encrypt = rsa.encrypt(bytes, KeyType.PublicKey);
        byte[] decrypt = rsa.decrypt(encrypt, KeyType.PrivateKey);
        log.info("公钥加密，私钥解密:" + StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));
        //私钥加密，公钥解密
        byte[] encrypt2 = rsa.encrypt(bytes, KeyType.PrivateKey);
        byte[] decrypt2 = rsa.decrypt(encrypt2, KeyType.PublicKey);
        log.info("私钥加密，公钥解密:" + StrUtil.str(decrypt2, CharsetUtil.CHARSET_UTF_8));
    }

    /**
     * <a href="https://www.hutool.cn/docs/#/crypto/摘要加密-Digester">摘要加密</a>
     * 以MD5、SHA1、BCrypt为例
     */
    public static void md5AndSh1AndBcrypt() {
        String s = "123456";
        log.info("md5Hex:" + DigestUtil.md5Hex(s));
        log.info("sha1Hex:" + DigestUtil.sha1Hex(s));
        String bCrypt = BCrypt.hashpw(s);
        log.info("bCrypt:" + bCrypt);
        log.info("bCrypt验证:" + BCrypt.checkpw(s, bCrypt));
    }

    /**
     * <a href="https://www.hutool.cn/docs/#/crypto/消息认证码算法-HMac">消息认证码算法</a>
     * 以HmacMD5为例
     */
    public static void hMacMd5() {
        String s = "test中文";
        byte[] key = "password".getBytes();
        HMac mac = new HMac(HmacAlgorithm.HmacMD5, key);
        log.info("HmacMD5:" + mac.digestHex(s));
    }

    /**
     * <a href="https://www.hutool.cn/docs/#/crypto/签名和验证-Sign">签名和验证</a>
     * 以MD5withRSA为例
     */
    public static void md5withRsa() {
        byte[] data = "我是一段测试字符串".getBytes();
        Sign sign = SecureUtil.sign(SignAlgorithm.MD5withRSA);
        byte[] signed = sign.sign(data);
        log.info("签名Hex:" + HexUtil.encodeHexStr(signed));
        log.info("验证签名:" + sign.verify(data, signed));
    }

}
