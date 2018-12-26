package com.zmc.springcloud.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * Created by xyy on 2018/11/27.
 *
 * @author xyy
 */
public class UploadUtil {
    /**
     * 保存文件，直接以multipartFile形式
     *
     * @param multipartFile
     * @param path          文件保存绝对路径
     * @return 返回文件名
     * @throws IOException
     */
    public static String saveImg(MultipartFile multipartFile, String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        InputStream fileInputStream = multipartFile.getInputStream();
        String fileName = UUID.randomUUID().toString() + ".png";
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path + File.separator + fileName));
        byte[] bs = new byte[1024];
        int len;
        while ((len = fileInputStream.read(bs)) != -1) {
            bos.write(bs, 0, len);
        }
        bos.flush();
        bos.close();
        return fileName;
    }

      // 可能有用的另一种写法
//    public static String saveImg(MultipartFile multipartFile, String path) throws IOException {
//        String fileName = UUID.randomUUID().toString() + ".png";
//        // path + File.separator + fileName
//        File dest = new File(path + File.separator + fileName);
//        if (!dest.getParentFile().exists()) {
//            dest.getParentFile().mkdirs();
//        }
//        multipartFile.transferTo(dest);
//        return fileName;
//    }
}
