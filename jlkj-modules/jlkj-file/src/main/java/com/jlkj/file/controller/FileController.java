package com.jlkj.file.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.file.constants.SysConstant;
import com.jlkj.file.entity.SysFileresource;
import com.jlkj.file.service.SysFileresourceServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author zyf
 * @Description
 * @create 2022-05-24 8:12
 */
@Tag(name = "文件资源")
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Autowired(required=false)
    SysFileresourceServiceImpl fileresourceService;

    @Resource
    private HttpServletRequest httpServletRequest;

//    @Value("${file.save-path}")
    private String savePath;

    public static final String MSIE = "MSIE";
    public static final String TRINDENT = "Trident";
    public static final Integer LENGTH = 1024;


    @PostMapping("/upload")
    public Object uploadFiles(@RequestPart(value = "file") MultipartFile file) {
        if (file.isEmpty()) {
            return AjaxResult.error("文件不能为空");
        }
        return fileresourceService.uploadFiles(file);
    }

    @GetMapping(value = "/download/{id}")
    public Object getFiles(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            SysFileresource fileDetails = fileresourceService.getById(id);
            if (fileDetails == null) {
                return AjaxResult.error("文件资源未找到");
            }
            String filename = fileDetails.getFileName();
            File file = new File(fileDetails.getTargetName());
            inputStream = new FileInputStream(file);
            response.setHeader("Content-Disposition", "attachment;filename=" + convertToFileName(request, filename));
            outputStream = response.getOutputStream();
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            log.error("文件下载出错", e);
            return AjaxResult.error("文件下载出错");
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return AjaxResult.success();
    }

    @GetMapping(value = "/view/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public Object viewFilesImage(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        SysFileresource fileDetails = fileresourceService.getById(id);
        if (fileDetails != null) {
            if (!fileDetails.getSuffix().isEmpty() && !SysConstant.IMAGE_TYPE.contains(fileDetails.getSuffix())) {
                return AjaxResult.error("非图片/PDF类型请先下载");
            }
        }
        try {
            File file = new File(fileDetails.getTargetName());
            InputStream inputStream = new FileInputStream(file);
            return new ResponseEntity<byte[]>(inputStreamToByte(inputStream), HttpStatus.OK);
        } catch (Exception e) {
            return AjaxResult.error("获取文件出错");
        }
    }

    private String convertToFileName(HttpServletRequest request, String fileName) throws UnsupportedEncodingException {
        // IE
        String userAgent = request.getHeader("User-Agent");
        if (userAgent.contains(MSIE) || userAgent.contains(TRINDENT)) {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } else {
            // 非IE
            fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        }
        return fileName;
    }

    public static byte[] inputStreamToByte(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int rc = 0;
        while ((rc = inputStream.read(buff, 0, LENGTH)) > 0) {
            byteArrayOutputStream.write(buff, 0, rc);
        }
        return byteArrayOutputStream.toByteArray();
    }
}
