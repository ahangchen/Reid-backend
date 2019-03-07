package scut.cwh.reid.domain.base;

public enum FileType {
    IMG("img"),
    AUDIO("audio"),
    OTHER("other");
    String subDirName;
    FileType(String subDirName) {
        this.subDirName = subDirName;
    }

    public String getSubDirName() {
        return subDirName;
    }

    public void setSubDirName(String subDirName) {
        this.subDirName = subDirName;
    }
}
