package utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author ：xuanlong
 * @date ：Created in 2020/9/3 3:35 PM
 * @description：TODO
 * @modified By：
 * @version: 0.0.1
 */
public class HttpClientUtil {
    public static final String POST_URL = "http://localhost:8081/file/baseQuery";


    public static String doPOST(String serverUrl, String jsonStr) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(serverUrl);
        StringEntity stringEntity = new StringEntity(jsonStr, ContentType.APPLICATION_JSON);
        post.setEntity(stringEntity);
        //响应内容
        String responseContent = null;
        CloseableHttpResponse response = null;
        try {
            response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                responseContent = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (client != null) {
                        client.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return responseContent;
    }

    public static String doPOST(String jsonStr) {
       return doPOST(POST_URL,jsonStr);
    }

    public static void main(String[] args) {
        //测试上传功能
        //serverDestFilePath服务端的接收地址
        String serverDestFilePath = "/Users/xuanlong/Desktop/download/";
        //文件的地址
        File file = new File("/Users/xuanlong/Desktop/peiban/demo/pom.xml");
        //执行上传
        postUploadFile(serverDestFilePath, file);
    }

    public static String postUploadFile(String serverDestFilePath, File file) {
        String resultStr = "";
        try {
            String baseStr = FileUtil.encodeBase64File(file.getAbsolutePath());
            HashMap<String, String> postMap = new HashMap<String, String>(16);
            postMap.put("name", file.getName());
            postMap.put("baseStr", baseStr);
            postMap.put("filePath", serverDestFilePath);
            resultStr = HttpClientUtil.doPOST(JSON.toJSONString(postMap));
            if(resultStr.equals("success")){
                System.out.println(file.getName()+"---上传成功");
            }
        } catch (Exception e) {
            System.out.println(file.getName()+"---上传失败");
            e.printStackTrace();
        }
        return  resultStr;
    }
}
