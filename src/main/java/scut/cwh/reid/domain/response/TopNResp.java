package scut.cwh.reid.domain.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopNResp {
    @SerializedName("top_imgs")
    private List<String> topnUrls;

    public List<String> getTopnUrls() {
        return topnUrls;
    }

    public void setTopnUrls(List<String> topnUrls) {
        this.topnUrls = topnUrls;
    }
}
