package scut.cwh.reid.algo;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static scut.cwh.reid.utils.NetworkUtils.postJsonRequest;

public class Reid {
    public static List<Integer> sortImgIDs(String queryImgPath, int[] galleryImgIDs) {
        String sortURL = "";
        StringBuilder dbImgIDs = new StringBuilder("[");
        for(int i= 0; i < galleryImgIDs.length - 1; i ++) {
            dbImgIDs.append(String.format("%d,", galleryImgIDs[i +1]));
        }
        dbImgIDs.append(String.format("%d]", galleryImgIDs[galleryImgIDs[galleryImgIDs.length - 1]]));
        String request = String.format("{'queryImg': %s, 'dbimgids': %s}", queryImgPath, dbImgIDs.toString());
        String result = postJsonRequest(sortURL, request);
        return new Gson().fromJson(result, ArrayList.class);
    }
}
