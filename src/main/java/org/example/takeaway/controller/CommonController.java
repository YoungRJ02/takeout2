package org.example.takeaway.controller;

import org.example.takeaway.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * 处理文件上传下载
 */

@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${takeout.path}")
    private String basepath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String filename = UUID.randomUUID().toString() + suffix;
        File dir = new File(basepath);
        if (!dir.exists()){
            dir.mkdirs();
        }

        try {
            file.transferTo(new File(basepath + filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return R.success(filename);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(basepath + name));
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
            byte[] bytes = new byte[1024];
            int length = 0;
            while ((length = fileInputStream.read(bytes)) != -1){
                outputStream.write(bytes, 0, length);
                outputStream.flush();
            }
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
