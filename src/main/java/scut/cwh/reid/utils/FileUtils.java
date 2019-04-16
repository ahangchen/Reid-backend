package scut.cwh.reid.utils;

import org.springframework.web.multipart.MultipartFile;
import scut.cwh.reid.config.FileServerProperties;
import scut.cwh.reid.config.ResultEnum;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class FileUtils {
    public static boolean isExist(String filePath){
        File f = new File(filePath);
        return f.exists() && !f.isDirectory();
    }

    public static String saveRequestFile(String filePath, MultipartFile file) {
        if (!file.isEmpty()) {
            File f = new File(filePath);
            if(f.exists()) {
                filePath = filePath.replace(file.getOriginalFilename(),
                        file.hashCode() + file.getOriginalFilename());
            }
            f = new File(filePath);
            try {
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(f));
                out.write(file.getBytes());
                out.flush();
                out.close();
                return filePath;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public static boolean saveBase64Img(String base64Img, String filePath) {
        if(base64Img == null) {
            return false;
        }
//        if(!base64Img.startsWith("data:image/png;base64,")) {
//            return false;
//        }
//        String[] blocks = base64Img.split("base64,");
//        byte[] imgByte = Base64.getDecoder().decode(blocks[1]);
        byte[] imgByte = Base64.getDecoder().decode(base64Img);
        try {
            new FileOutputStream(filePath).write(imgByte);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static String url2Path(String fileUrl, FileServerProperties fsp) {
        fileUrl = fileUrl.replace("//reid/", "/reid/");
        return fileUrl.replace(fsp.getHost() + "reid", fsp.getPath());
    }

    public static String path2Url(String filePath, FileServerProperties fsp) {
        return filePath.replace(fsp.getPath(), fsp.getHost() + "reid");
    }

    public static void main(String[]args){
        String[] blocks = "data:image/png;base64,aaa".split("base64,");
        System.out.println(blocks[1]);
    }
}
