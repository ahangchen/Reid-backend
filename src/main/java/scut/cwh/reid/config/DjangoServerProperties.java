package scut.cwh.reid.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="djangoServer")
public class DjangoServerProperties {
    private String detectApi;
    private String topnApi;
    private String fusionTopnApi;

    public String getDetectApi() {
        return detectApi;
    }

    public void setDetectApi(String detectApi) {
        this.detectApi = detectApi;
    }

    public String getTopnApi() {
        return topnApi;
    }

    public void setTopnApi(String topnApi) {
        this.topnApi = topnApi;
    }

    public String getFusionTopnApi() {
        return fusionTopnApi;
    }

    public void setFusionTopnApi(String fusionTopnApi) {
        this.fusionTopnApi = fusionTopnApi;
    }
}
