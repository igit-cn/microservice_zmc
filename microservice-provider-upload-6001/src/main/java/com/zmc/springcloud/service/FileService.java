package com.zmc.springcloud.service;

import com.zmc.springcloud.utils.Json;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import com.zmc.springcloud.utils.FileInfo;
import com.zmc.springcloud.utils.FileInfo.FileType;
import com.zmc.springcloud.utils.FileInfo.OrderType;

/**
 * Created by xyy on 2018/11/23.
 */
public interface FileService {

    /**
     * 文件验证
     *
     * @param fileType
     *            文件类型
     * @param multipartFile
     *            上传文件
     * @return 文件验证是否通过
     */
    boolean isValid(FileType fileType, MultipartFile multipartFile) throws Exception;

    String upload(FileType fileType, MultipartFile multipartFile) throws Exception;

    String uploadPic(MultipartFile file) throws Exception;
}
