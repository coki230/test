package docker;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DockerRegisty {
    public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, IOException, CertificateException, KeyStoreException, UnrecoverableKeyException {
        String server = "http://10.65.4.2:5000/v2";
        String listApi = "/_catalog";

        // GET /v2/<name>/tags/list
        String tagListApi = "/app-dolphin-test-jar-jdk-test-demo/tags/list";

        // GET /v2/<name>/manifests/<reference>
        String manifestsApi = "/app-dolphin-test-jar-jdk-test-demo/manifests/k8s-v3-20200208103133";

        // GET /v2/<name>/blobs/<digest>
        String getLayerApi = "/aledbf/kube-keepalived-vip/blobs/sha256:a3ed95caeb02ffe68cdd9fd84406680ae93d633cb16422d00e8a7c22955b46d4";


//        String result1 = HttpUtil.get(server + manifestsApi);

        // 上传镜像
        /*
                先推各个层到registry仓库，然后上传清单。

        * 1. http://10.65.4.2:5000/v2/tiller/blobs/uploads/ 用post访问，初始化上传
        * */

        // 获取头信息
//        HttpResponse res = HttpRequest.get(server + manifestsApi).execute();
//        System.out.println(res.headers());
//        System.out.println(res.body());

//        String s = HttpUtil.get(server + listApi);
//        JSONObject jsonObject = JSON.parseObject(s);
//        JSONArray repositories = jsonObject.getJSONArray("repositories");
//        repositories.forEach(repository -> {
//            System.out.println(repository);
//        });

//        String s = DockerRegisty.doGet("https://10.68.7.151:15000/v2/_catalog", null);
//        System.out.println(s);

        DockerRegisty.test();

    }

    public static void test2() throws IOException {
        FileInputStream fis = new FileInputStream("D:\\tmp\\client.cert");
        DataInputStream dataInputStream = new DataInputStream(fis);
        int i = dataInputStream.readInt();
        System.out.println(i);
    }




    public static void test() throws NoSuchAlgorithmException, KeyManagementException, IOException, CertificateException, KeyStoreException, UnrecoverableKeyException {
        SSLContext ctx = SSLContext.getInstance("TLS");
        X509TrustManager tm = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] xcs,
                                           String string) throws CertificateException {
            }
            public void checkServerTrusted(X509Certificate[] xcs,
                                           String string) throws CertificateException {
            }
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public boolean isServerTrusted(
                    java.security.cert.X509Certificate[] certs) {
                return true;
            }

            public boolean isClientTrusted(
                    java.security.cert.X509Certificate[] certs) {
                return true;
            }
        };

        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());

        String keyStorePassword = null;

        FileInputStream fis = new FileInputStream("C:\\Users\\YangChao\\Desktop\\temp\\client.key");


        String certPath = "C:\\Users\\YangChao\\Desktop\\temp\\client.cert";
        String key = "C:\\Users\\YangChao\\Desktop\\temp\\client.key";

        KeyStore ks = KeyStore.getInstance("JKS");
        CertificateFactory certFactory = CertificateFactory.getInstance("X509");
        X509Certificate cert = (X509Certificate) certFactory.generateCertificate(new ByteArrayInputStream(new String(Files.readAllBytes(Paths.get(certPath))).trim().getBytes()));
        ks.load(null);
        ks.setCertificateEntry("aaa", cert);
//        kmf.init(ks, new String(Files.readAllBytes(Paths.get(key)));

        ctx.init(kmf.getKeyManagers(), new TrustManager[]{tm},null );

        System.out.println("缺省安全套接字使用的协议: " + ctx.getProtocol());
        // 获取SSLContext实例相关的SSLEngine
        SSLEngine en = ctx.createSSLEngine();
        System.out
                .println("支持的协议: " + Arrays.asList(en.getSupportedProtocols()));
        System.out.println("启用的协议: " + Arrays.asList(en.getEnabledProtocols()));
        System.out.println("支持的加密套件: "
                + Arrays.asList(en.getSupportedCipherSuites()));
        System.out.println("启用的加密套件: "
                + Arrays.asList(en.getEnabledCipherSuites()));

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(ctx, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        CloseableHttpClient build = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        HttpUriRequest httpUriRequest = new HttpGet("https://10.68.7.151:15000/v2/_catalog");
        build.execute(httpUriRequest);
    }


    /**
     * 设置可访问https
     * @return
     */
    public static CloseableHttpClient createSSLClientDefault() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                //信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }

    public static String doGet(String url, Map<String, String> param) {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = createSSLClientDefault();//调用createSSLClientDefault
        String resultString = "";
        CloseableHttpResponse response = null;
        try {

            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            //设置请求头
//            if (parameter != null) {
//                httpGet.addHeader("Token", parameter.getToken());
//                httpGet.addHeader("Sign", parameter.getSign());
//                httpGet.addHeader("Org_id", parameter.getOrg_id() + "");
//                httpGet.addHeader("Product_id", parameter.getProduct_id() + "");
//                httpGet.addHeader("Timestamp", parameter.getTimestamp());
//            }

            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(),
                        "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

}
