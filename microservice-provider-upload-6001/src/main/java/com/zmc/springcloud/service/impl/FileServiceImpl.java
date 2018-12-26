package com.zmc.springcloud.service.impl;

import com.zmc.springcloud.service.FileService;
import com.zmc.springcloud.utils.FastDFSClient;
import com.zmc.springcloud.utils.FileInfo;
import com.zmc.springcloud.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


/**
 * Created by xyy on 2018/11/23.
 * @author xyy
 */
@Service
public class FileServiceImpl implements FileService{

    @Value("${img.location}")
    private String location;

    @Value("${trackerServer}")
    private String trackerServer;

    @Override
    public boolean isValid(FileInfo.FileType fileType, MultipartFile multipartFile) throws Exception{
        // TODO 对不同类型的媒体文件进行不同的校验
        if(multipartFile == null){
            return false;
        }
        return true;
    }

    @Override
    public String upload(FileInfo.FileType fileType, MultipartFile multipartFile)throws Exception{
        String file_name = null;
        String filePath = location;
        // TODO 对于上传文件和视频  不能直接使用saveImg!!!
        try{
            file_name = UploadUtil.saveImg(multipartFile, filePath);
        }catch (Exception e){
            e.printStackTrace();
        }
        return file_name;
    }

    /** 基于FastDFS的图片上传*/
    @Override
    public String uploadPic(MultipartFile file) throws Exception {
        // 上传到图片服务器
        // 取图片扩展名
        String originalFilename = file.getOriginalFilename();
        // 取扩展名不要“.”
        String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        FastDFSClient client = new FastDFSClient(trackerServer);
        String url = client.uploadFile(file.getBytes(), extName);
        return url;
    }
}
