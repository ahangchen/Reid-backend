package scut.cwh.reid.domain.base;

import scut.cwh.reid.config.FileServerProperties;

import java.io.File;

public class FileInfo {
    private String fileName;
    private String filePath;
    private String fileUrl;

    public FileInfo(String fileName, FileType fileType, FileServerProperties fileServerProperties) {
        this.fileName = fileName;
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

    public void updateInfo(String newFilePath) {
        if(newFilePath.equals(this.filePath)) {
            return;
        }
        this.filePath = newFilePath;
        String[] newFileInfos = newFilePath.split(File.separator);
        this.fileUrl = this.fileUrl.replace(this.fileName, newFileInfos[newFileInfos.length - 1]);
        this.fileName = newFileInfos[newFileInfos.length - 1];
    }
}
