package docker;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParser;

public class DockerRegisty {
    public static void main(String[] args) {
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

        String s = HttpUtil.get(server + listApi);
        JSONObject jsonObject = JSON.parseObject(s);
        JSONArray repositories = jsonObject.getJSONArray("repositories");
        repositories.forEach(repository -> {
            System.out.println(repository);
        });
    }
}
