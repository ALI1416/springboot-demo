package cn.z.mqtt;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import javax.net.ssl.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * <h1>SSL工具</h1>
 *
 * <p>
 * createDate 2023/11/28 16:33:08
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 **/
public class SslUtil {

    private SslUtil(){
    }

    /**
     * 自动签名
     *
     * @return SSLSocketFactory
     */
    public static SSLSocketFactory getSocketFactory() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
        };
        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(null, trustAllCerts, null);
        return context.getSocketFactory();
    }

    /**
     * 手动签名
     *
     * @param caCrt     CA证书
     * @param clientCrt 客户端证书
     * @param clientKey 客户端密钥
     * @return SSLSocketFactory
     */
    public static SSLSocketFactory getSocketFactory(InputStream caCrt, InputStream clientCrt, InputStream clientKey) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
        Security.addProvider(new BouncyCastleProvider());
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        // CA证书
        X509Certificate caCert = (X509Certificate) factory.generateCertificate(new BufferedInputStream(caCrt));
        // 客户端证书
        X509Certificate clientCert = (X509Certificate) factory.generateCertificate(new BufferedInputStream(clientCrt));
        // 客户端密钥
        PrivateKey privateKey;
        try (PEMParser pemParser = new PEMParser(new InputStreamReader(clientKey))) {
            privateKey = new JcaPEMKeyConverter().setProvider("BC").getPrivateKey((PrivateKeyInfo) pemParser.readObject());
        }
        // CA证书用于对服务器进行认证
        KeyStore caKs = KeyStore.getInstance(KeyStore.getDefaultType());
        caKs.load(null, null);
        caKs.setCertificateEntry("ca-certificate", caCert);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
        tmf.init(caKs);
        // 客户端密钥和证书被发送到服务器，以便服务器对我们进行身份验证
        KeyStore clientKs = KeyStore.getInstance(KeyStore.getDefaultType());
        clientKs.load(null, null);
        clientKs.setCertificateEntry("certificate", clientCert);
        clientKs.setKeyEntry("private-key", privateKey, null, new Certificate[]{clientCert});
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(clientKs, null);
        // 创建SSL套接字
        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        return context.getSocketFactory();
    }

}
