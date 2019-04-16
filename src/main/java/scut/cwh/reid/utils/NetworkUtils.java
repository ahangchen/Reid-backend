package scut.cwh.reid.utils;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetworkUtils {
    public static String postItem(JSONObject item, String queryURL) {
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

    public static String postForm(String requestURL, Map<String, String> params) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            List<NameValuePair> form = new ArrayList<>();
            for (Map.Entry<String, String> param : params.entrySet()) {
                form.add(new BasicNameValuePair(param.getKey(), param.getValue()));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(form, Consts.UTF_8);

            HttpPost httpPost = new HttpPost(requestURL);
            httpPost.setEntity(entity);
            System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity responseEntity = response.getEntity();
                    return responseEntity != null ? EntityUtils.toString(responseEntity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            String responseBody = httpclient.execute(httpPost, responseHandler);
            return responseBody;
//            System.out.println("----------------------------------------");
//            System.out.println(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        HashMap params = new HashMap<>();
        params.put("path", "/home/cwh/coding/reid_file_backend/img/query_2006334270.png");
        System.out.print(postForm("http://222.201.145.237:8082/reid/detect", params)
        );
    }
}
