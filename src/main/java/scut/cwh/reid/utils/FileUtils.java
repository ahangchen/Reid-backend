package scut.cwh.reid.utils;

import org.springframework.web.multipart.MultipartFile;
import scut.cwh.reid.config.ResultEnum;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
}
