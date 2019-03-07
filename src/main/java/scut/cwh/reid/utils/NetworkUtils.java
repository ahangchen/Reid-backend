package scut.cwh.reid.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class NetworkUtils {
    public static String postItem(JSONObject item, String queryURL){
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;
        try {
            HttpPost req = new HttpPost(queryURL);
            req.addHeader("content-type", "application/json");

            //url格式编码
            StringEntity params = new StringEntity(item.toString());
            req.setEntity(params);
            response = httpClient.execute(req);
            HttpEntity entity = response.getEntity();

//            System.out.println(response.getStatusLine().getStatusCode());
//            System.out.println(EntityUtils.toString(entity));
            return EntityUtils.toString(entity);

            // handle response here...
        } catch (Exception ex) {
            // handle exception here
            return null;
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String postJsonRequest(String requestURL, String jsonStr) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = null;
        try {
            HttpPost request = new HttpPost(requestURL);
            StringEntity params = new StringEntity("details=" + jsonStr);
            request.addHeader("content-type", "application/x-www-form-urlencoded");
            request.setEntity(params);
            response = httpClient.execute(request);
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
