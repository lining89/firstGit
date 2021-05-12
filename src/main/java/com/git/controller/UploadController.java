package com.git.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

/**
 * @author Mr ● Li
 * @version 1.0
 * @date 2021/5/6 9:23
 */
@Controller
@Slf4j
public class UploadController {

    @RequestMapping(value = "/index")
    public String toUpload(){
        return "upload";
    }

    @RequestMapping(value = "/indexs")
    public String toUploads(){
        return "uploads";
    }
    //单个文件上传
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFile(MultipartFile file, HttpServletRequest request){
        try {
            //创建文件在服务器端的存放路径
            String dir = request.getServletContext().getRealPath("/upload");
            log.info(">>>>>>>" + dir);
            File fileDir = new File(dir);
            if(!fileDir.exists()){
                fileDir.mkdir();
            }
            //生成文件在服务器端存放的名字
            String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String fileName = UUID.randomUUID().toString().split("-")[0] + fileSuffix;
            File filePath = new File(fileDir + "/" + fileName);
            //上传
            file.transferTo(filePath);
        }catch(Exception e){
            e.printStackTrace();
            return "上传失败";
        }
        return "success";
    }
    //批量文件上传
    @RequestMapping(value = "/upload/batch", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFile(MultipartFile[] file, HttpServletRequest request){
        try {
            //创建文件在服务器端的存放路径
            String dir = request.getServletContext().getRealPath("/uploads");
            log.info(">>>>>>>" + dir);
            File fileDir = new File(dir);
            if(!fileDir.exists()){
                fileDir.mkdir();
            }
            for (MultipartFile fileBatch: file) {
                //生成文件在服务器端存放的名字
                String fileSuffix = fileBatch.getOriginalFilename().substring(fileBatch.getOriginalFilename().lastIndexOf("."));
                String fileName = UUID.randomUUID().toString().split("-")[0] + fileSuffix;
                File filePath = new File(fileDir + "/" + fileName);
                //上传
                fileBatch.transferTo(filePath);
            }
        }catch(Exception e){
            e.printStackTrace();
            return "上传失败";
        }
        return "上传成功";
    }
}
