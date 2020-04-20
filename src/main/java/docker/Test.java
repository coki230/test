package docker;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

public class Test {
    public static KeyStore getHttpsKeyStore()
    {
        InputStream ins = null;
        try {

            ins = new FileInputStream("C:\\Users\\YangChao\\Desktop\\temp\\client.cert");
            //读取证书
            CertificateFactory cerFactory = CertificateFactory.getInstance("X.509");  //问1
            Certificate cer = cerFactory.generateCertificate(ins);
            //创建一个证书库，并将证书导入证书库
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());   //问2
            keyStore.load(null, null);
            keyStore.setCertificateEntry("trust", cer);
            return keyStore;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(ins!=null)
            {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    public void initSSLContext() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(getHttpsKeyStore());
        sslContext.init( null, trustManagerFactory.getTrustManagers(), new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

            @Override
            public boolean verify(String hostname, SSLSession sslsession) {

                return true;
//                if("localhost".equals(hostname)){
//                    return true;
//                } else {
//                    return false;
//                }
            }
        });


        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        CloseableHttpClient build = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        HttpUriRequest httpUriRequest = new HttpGet("https://10.68.7.151:15000/v2/_catalog");
        CloseableHttpResponse execute = build.execute(httpUriRequest);
        System.out.println(execute.getAllHeaders());
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        Test t = new Test();
        t.initSSLContext();
    }
}
