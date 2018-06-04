package scut.cwh.reid.algo;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import scut.cwh.reid.domain.VisionMacInfo;

import java.io.IOException;
import java.util.List;

public class Reid {

    public static double getSimilarity(VisionMacInfo vision1, VisionMacInfo vision2) {
        JSONObject jsonObject=new JSONObject();
        try {
            String path1 = vision1.getImgPath().replace("http://222.201.137.47:12347/reid/","/home/cwh/coding/reid_file_backend/");
            String path2 = vision2.getImgPath().replace("http://222.201.137.47:12347/reid/","/home/cwh/coding/reid_file_backend/");
            jsonObject.put("pic1",path1);
            jsonObject.put("pic2",path2);

            JSONArray array1=new JSONArray();
            for(List<Integer> list:vision1.getBoxes()){
                JSONArray arraySub1=new JSONArray();
                for(Integer num:list){
                    arraySub1.put(num);
                }
                array1.put(arraySub1);
            }

            JSONArray array2=new JSONArray();
            for(List<Integer> list:vision2.getBoxes()){
                JSONArray arraySub2=new JSONArray();
                for(Integer num:list){
                    arraySub2.put(num);
                }
                array2.put(arraySub2);
            }

            jsonObject.put("box1",array1);
            jsonObject.put("box2",array2);

            double maxSim=0;
            String resultStr = postItem(jsonObject);
            JSONObject result = new JSONObject(resultStr);
//            System.out.println("result : "+result.toString());

            JSONArray array = result.getJSONArray("sim");
            for(int i=0;i<array.length();i++){
                JSONArray subArray = array.getJSONArray(i);
                for(int j=0;j<subArray.length();j++){
                    if(subArray.getDouble(j)>maxSim)
                        maxSim=subArray.getDouble(j);
                }
            }
//            System.out.println("maxSim : "+maxSim);
            return maxSim;

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return 0;
    }

    static String postItem(JSONObject item){
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;
        try {
            HttpPost req = new HttpPost("http://222.201.137.47:12349/query/");
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
}
