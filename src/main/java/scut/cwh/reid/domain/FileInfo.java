package scut.cwh.reid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import scut.cwh.reid.config.FileServerProperties;

import java.io.File;

public class FileInfo {
    private String fileType;
    private String fileName;
    private String filePath;
    private String fileUrl;

    public FileInfo(String fileName, FileType fileType, FileServerProperties fileServerProperties) {
        this.filePath = fileServerProperties.getPath() + File.separator
                + fileType.getSubDirName() + File.separator + fileName;
        this.fileUrl = fileServerProperties.getHost() + "/reid/" + fileType.getSubDirName() + "/" + fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
