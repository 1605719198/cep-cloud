package com.jlkj.imageIdentify.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.imageIdentify.utils.ClassPathUtils;
import com.jlkj.imageIdentify.utils.ImgChangeUtil;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.util.ImageHelper;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * @author Liukuiyan
 */
@RestController
public class OcrController {

    @Value("${tess4j.language}")
    private String language;

    @RequestMapping("/ocr/idCard")
    public Object OcrIdCard(@RequestParam("file") MultipartFile file){
        long startTime = System.currentTimeMillis();
        if (file != null) {
            System.out.println("doOCR on a jpg image");
            try {
                InputStream sbs = new ByteArrayInputStream(file.getBytes());
                BufferedImage img = ImageIO.read(sbs);


                /**************************************截取身份证人脸图 start***********************************/
                // 解决awt报错问题
                System.setProperty("java.awt.headless", "false");
                System.out.println(System.getProperty("java.library.path"));
// 加载动态库
                URL url = ClassLoader.getSystemResource("opencvlib/opencv_java320.dll");
                System.load(url.getPath());

                long startTime1 = System.currentTimeMillis();
                BufferedImage faceImg = img;
                //用来记录人脸坐标
                int[] rectPosition = new int[4];
                //从配置文件lbpcascade_frontalface.xml中创建一个人脸识别器
                //该文件在openCV包C:\opencv\sources\data\haarcascades\中
                CascadeClassifier faceDetector = new CascadeClassifier(ClassPathUtils.getPath()+"haarcascades/haarcascade_frontalface_alt2.xml");



//                // 在图片中检测人脸
//                MatOfRect faceDetections = new MatOfRect();
//                //指定人脸识别的最大和最小像素范围
//                Size minSize = new Size(100, 100);
//                Size maxSize = new Size(500, 500);
//                //参数设置为scaleFactor=1.1f, minNeighbors=4, flags=0 以此来增加识别人脸的正确率
//                faceDetector.detectMultiScale(mat, faceDetections, 1.1f, 4, 0, minSize, maxSize);
//                Rect[] rects = faceDetections.toArray();
//                if(rects != null && rects.length == 1){
//                    // 在每一个识别出来的人脸周围画出一个方框
//                    Rect rect = rects[0];
//                    return mat;
//                }else{
//                    return null;
//                }


                //将BufferedImage转换为Mat对象
                Mat mat = ImgChangeUtil.BufImg2Mat(faceImg);
                //指定人脸识别的最大和最小像素范围
                Size minSize = new Size(200, 200);
                Size maxSize = new Size(1500, 1500);
                // 在图片中检测人脸,参数设置为scaleFactor=1.1f, minNeighbors=4, flags=0 以此来增加识别人脸的正确率
                MatOfRect faceDetections = new MatOfRect();
                faceDetector.detectMultiScale(mat, faceDetections,1.1f, 4, 0, minSize, maxSize);
                //人脸坐标
                Rect[] rects = faceDetections.toArray();
                //如果检测到人脸
//                if (rects != null && rects.length >= 1) {
                    System.out.println("识别到人脸，执行保存！");
                    for (Rect rect : rects) {
                        rectPosition[0]=rect.x-120;
                        rectPosition[1]=rect.y-240;
                        rectPosition[2]=rect.width+200;
                        rectPosition[3]=rect.height+600;
                    }
                    //将Mat对象转换回BufferedImage对象
                    faceImg = ImgChangeUtil.Mat2BufImg(mat);
                    //截取图片
                    faceImg=ImageHelper.getSubImage(faceImg, rectPosition[0], rectPosition[1], rectPosition[2], rectPosition[3]);
                    ImageIO.write(faceImg,"jpg",new File("D:\\imgs\\faceImg1.jpg"));
//                }
                System.out.println("人脸图片检测并保存耗时："+(System.currentTimeMillis()-startTime1)+"ms");

                //对图片进行处理
                img = convertImage(img);
                ITesseract tesseract = new Tesseract();
                //设置中文字体库路径,不设置 默认为resources下
//                tesseract.setDatapath("D:\\tessdata");
                //中文识别
                tesseract.setLanguage(language);
                String result = tesseract.doOCR(img);
                long endTime = System.currentTimeMillis();
                System.out.println("扫描的文本："+result);
                System.out.println("本次处理耗时：" + (endTime - startTime) + " 毫秒");
                return AjaxResult.success(result+faceImg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("文件是空的");
        }
        return AjaxResult.error();
    }

    //对图片进行处理 - 提高识别度
    public static BufferedImage convertImage(BufferedImage image) throws Exception {
        //按指定宽高创建一个图像副本
        //image = ImageHelper.getSubImage(image, 0, 0, image.getWidth(), image.getHeight());
        //图像转换成灰度的简单方法 - 黑白处理
        image = ImageHelper.convertImageToGrayscale(image);
        //图片锐化
        image = ImageHelper.convertImageToBinary(image);
        //图像缩放 - 放大n倍图像
        image = ImageHelper.getScaledInstance(image, image.getWidth() * 3, image.getHeight() * 3);
        return image;
    }
}
