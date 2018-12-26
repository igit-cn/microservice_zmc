package com.zmc.springcloud.controller;

import com.zmc.springcloud.service.FileService;
import com.zmc.springcloud.utils.FileInfo;
import com.zmc.springcloud.utils.Json;
import com.zmc.springcloud.utils.FileInfo.FileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xyy on 2018/11/23.
 */
@RestController
public class FileController {
    @Autowired
    FileService fileService;

    //50MB
    static final long FILE_LIMIT = 52428800;

    @RequestMapping("/image/upload2")
    @ResponseBody
    public Json uploadImages(@RequestParam MultipartFile[] files) throws Exception{
        Json json = new Json();

        for (MultipartFile file : files) {
            if (file.getSize() > FILE_LIMIT) {
                json.setSuccess(false);
                json.setMsg("文件过大");
                json.setObj(null);
                return json;
            }
            if (!fileService.isValid(FileType.image, file)) {
                json.setSuccess(false);
                json.setMsg("文件格式不支持");
                json.setObj(null);
                return json;
            }
        }

        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            String url = fileService.upload(FileType.image, file);
            if (url == null) {
                json.setSuccess(false);
                json.setMsg("服务器异常");
                json.setObj(null);
                return json;
            }
            urls.add(url);
        }

        json.setSuccess(true);
        json.setMsg("上传成功");
        json.setObj(urls);
        return json;
    }

    @RequestMapping("/image/multisize/upload")
    @ResponseBody
    public Json uploadMultisizeImages(@RequestParam MultipartFile[] files) throws Exception{
        Json json = new Json();

        for (MultipartFile file : files) {
            if (file.getSize() > FILE_LIMIT) {
                json.setSuccess(false);
                json.setMsg("文件过大");
                json.setObj(null);
                return json;
            }
            if (!fileService.isValid(FileType.image, file)) {
                json.setSuccess(false);
                json.setMsg("文件格式不支持");
                json.setObj(null);
                return json;
            }
        }

        List<Map<String, String>> maps = new ArrayList<Map<String, String>>();
        for (MultipartFile file : files) {
//            Map<String, String> map = fileService.build(file);
//            maps.add(map);
        }

        json.setSuccess(true);
        json.setMsg("上传成功");
        json.setObj(maps);
        return json;
    }

    @RequestMapping("/video/upload")
    @ResponseBody
    public Json uploadVideos(@RequestParam MultipartFile[] files) throws Exception{
        Json json = new Json();

        for (MultipartFile file : files) {
            if (file.getSize() > FILE_LIMIT) {
                json.setSuccess(false);
                json.setMsg("文件过大");
                json.setObj(null);
                return json;
            }
            if (!fileService.isValid(FileType.media, file)) {
                json.setSuccess(false);
                json.setMsg("文件格式不支持");
                json.setObj(null);
                return json;
            }
        }

        List<String> urls = new ArrayList<String>();
        for (MultipartFile file : files) {
            String url = fileService.upload(FileType.media, file);
            if (url == null) {
                json.setSuccess(false);
                json.setMsg("服务器异常");
                json.setObj(null);
                return json;
            }
            urls.add(url);
        }

        json.setSuccess(true);
        json.setMsg("上传成功");
        json.setObj(urls);
        return json;
    }

    @RequestMapping("/file/upload")
    @ResponseBody
    public Json uploadFiles(@RequestParam MultipartFile[] files) throws Exception{
        Json json = new Json();

        for (MultipartFile file : files) {
            if (file.getSize() > FILE_LIMIT) {
                json.setSuccess(false);
                json.setMsg("文件过大");
                json.setObj(null);
                return json;
            }
            if (!fileService.isValid(FileType.file, file)) {
                json.setSuccess(false);
                json.setMsg("文件格式不支持");
                json.setObj(null);
                return json;
            }
        }

        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            String url = fileService.upload(FileType.file, file);
            if (url == null) {
                json.setSuccess(false);
                json.setMsg("服务器异常");
                json.setObj(null);
                return json;
            }
            urls.add(url);
        }

        json.setSuccess(true);
        json.setMsg("上传成功");
        json.setObj(urls);
        return json;
    }

    //使用FastDFS=========================================================================================
    /** 使用FastDFS进行图片的上传*/
    @RequestMapping("/image/upload")
    @ResponseBody
    public Json upload(@RequestParam MultipartFile[] files) {
        Json j = new Json();
        List<String> urls = new ArrayList<>();
        try{
            for (MultipartFile file : files) {
                // 对图片判空
                if(file.isEmpty()){
                    j.setMsg("文件为空");
                    j.setSuccess(false);
                    return null;
                }
                String url = fileService.uploadPic(file);
                if (url == null) {
                    j.setSuccess(false);
                    j.setMsg("服务器异常");
                    j.setObj(null);
                    return j;
                }
                urls.add(url);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        j.setSuccess(true);
        j.setMsg("上传成功");
        j.setObj(urls);
        return j;
    }
}
