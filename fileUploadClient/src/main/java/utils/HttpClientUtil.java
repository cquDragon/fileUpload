package utils;

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

import java.io.IOException;

/**
 * @author ：xuanlong
 * @date ：Created in 2020/9/3 3:35 PM
 * @description：TODO
 * @modified By：
 * @version: 0.0.1
 */
public class HttpClientUtil {
    public final static String POST_URL = "http://www.baidu.com";

    public static String doPOST(String jsonStr) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(POST_URL);
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

    public static void main(String[] args) {
        try {
            String result = HttpClientUtil.doPOST("{\"a\":\"b\"}");
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
