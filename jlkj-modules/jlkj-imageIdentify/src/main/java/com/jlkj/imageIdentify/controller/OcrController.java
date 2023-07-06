package com.jlkj.imageIdentify.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.imageIdentify.utils.ImgChangeUtil;
import com.jlkj.imageIdentify.utils.OCRUtil;
import com.jlkj.imageIdentify.utils.OpencvUtil;
import net.sourceforge.tess4j.util.ImageHelper;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.jlkj.imageIdentify.utils.ImgChangeUtil.Mat2BufImg;
import static com.jlkj.imageIdentify.utils.OpencvUtil.rotate3;
import static com.jlkj.imageIdentify.utils.OpencvUtil.shear;

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

                BufferedImage faceImg = img;

                //该文件在openCV包C:\opencv\sources\data\haarcascades\中
//                CascadeClassifier faceDetector = new CascadeClassifier(ClassPathUtils.getPath()+"haarcascades/haarcascade_frontalface_alt2.xml");

                //将BufferedImage转换为Mat对象
//                Mat mat = ImgChangeUtil.BufImg2Mat(faceImg,BufferedImage.TYPE_4BYTE_ABGR, CvType.CV_8UC3);
                Mat mat = ImgChangeUtil.BufImg2Mat(faceImg);

                cardUp(mat);

                //对图片进行处理
//                img = convertImage(img);
//                ITesseract tesseract = new Tesseract();
//                //设置中文字体库路径,不设置 默认为resources下
////                tesseract.setDatapath("D:\\tessdata");
//                //中文识别
//                tesseract.setLanguage(language);
//                String result = tesseract.doOCR(img);
//                long endTime = System.currentTimeMillis();
//                System.out.println("扫描的文本："+result);
//                System.out.println("本次处理耗时：" + (endTime - startTime) + " 毫秒");
                return AjaxResult.success("处理成功");
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
//        image = ImageHelper.convertImageToBinary(image);
        //图像缩放 - 放大n倍图像
        image = ImageHelper.getScaledInstance(image, image.getWidth() * 3, image.getHeight() * 3);
        return image;
    }
    public static BufferedImage convertImage2(BufferedImage image) throws Exception {
        //图像转换成灰度的简单方法 - 黑白处理
        image = ImageHelper.convertImageToGrayscale(image);
        //图像缩放 - 放大n倍图像
        image = ImageHelper.getScaledInstance(image, image.getWidth() * 2, image.getHeight() * 2);
        return image;
    }

    /**
     * 身份证正面识别
     */
    public static void cardUp (Mat mat) throws Exception {
        Mat begin=mat.clone();
        //截取身份证区域，图像规则不留白则截取头像
        mat = OpencvUtil.houghLinesP(begin,mat);
        BufferedImage bufferedImage1234 = Mat2BufImg(mat,".jpg");

        Imgcodecs.imwrite("D:/Download/houghLinesP.jpg", mat);
        //进行人脸识别,校正图片方向
        Mat mat1 = OpencvUtil.faceLoop(mat);
        if(mat1==null){
            Imgcodecs.imwrite("D:/Download/face.jpg", mat);
        }else {
            Imgcodecs.imwrite("D:/Download/face.jpg", mat1);
        }
        //灰度
        mat=OpencvUtil.gray(begin);
        //二值化
        //mat=OpencvUtil.binary(mat);
        //腐蚀
        mat=OpencvUtil.erode(mat,1);
        //膨胀
        mat=OpencvUtil.dilate(mat,1);
        Imgcodecs.imwrite("D:/Download/bbb.jpg", mat);
        //获取名称
        String name=name(mat);
        System.out.print("姓名是："+name);
        //获取性别
        String sex=sex(mat);
        System.out.print("性别是："+sex);

        //获取民族
        String nation=nation(mat);
        System.out.print("民族是："+nation);

        //获取出生日期
        String birthday=birthday(mat);
        System.out.print("出生日期是："+birthday);

        //获取住址
        String address=address(mat);
        System.out.print("住址是："+address);

        //获取身份证
        String card=card(mat);
        System.out.print("身份证号是："+card);
    }

    public static String name(Mat mat) throws Exception {
        //图上两点
        Point point1=new Point(mat.cols()*0.18,mat.rows()*0.09);
        //图下两点
        Point point2=new Point(mat.cols()*0.18,mat.rows()*0.26);
        Point point3=new Point(mat.cols()*0.4,mat.rows()*0.09);
        Point point4=new Point(mat.cols()*0.4,mat.rows()*0.26);
        List<Point> list=new ArrayList<>();
        list.add(point1);
        list.add(point2);
        list.add(point3);
        list.add(point4);
        Mat name= shear(mat,list);
        name=OpencvUtil.drawContours(name,50);
        Imgcodecs.imwrite("D:/Download/name.jpg", name);
        BufferedImage nameBuffer=OpencvUtil.Mat2BufImg(name,".jpg");
        String nameStr=OCRUtil.getImageMessage(convertImage(nameBuffer),"chi_sim");
        nameStr=nameStr.replace("\n","");
        return nameStr+"\n";
    }

    public static String sex(Mat mat) throws Exception {
        Point point1=new Point(mat.cols()*0.18,mat.rows()*0.25);
        Point point2=new Point(mat.cols()*0.18,mat.rows()*0.38);
        Point point3=new Point(mat.cols()*0.26,mat.rows()*0.25);
        Point point4=new Point(mat.cols()*0.26,mat.rows()*0.38);
        List<Point> list=new ArrayList<>();
        list.add(point1);
        list.add(point2);
        list.add(point3);
        list.add(point4);
        Mat sex= shear(mat,list);
        sex=OpencvUtil.drawContours(sex,50);
        Imgcodecs.imwrite("D:/Download/sex.jpg", sex);
        BufferedImage sexBuffer=OpencvUtil.Mat2BufImg(sex,".jpg");
        String sexStr=OCRUtil.getImageMessage(convertImage(sexBuffer),"chi_sim");
        sexStr=sexStr.replace("\n","");
        return sexStr+"\n";
    }

    public static String nation(Mat mat) throws Exception {
        Point point1=new Point(mat.cols()*0.39,mat.rows()*0.23);
        Point point2=new Point(mat.cols()*0.39,mat.rows()*0.36);
        Point point3=new Point(mat.cols()*0.55,mat.rows()*0.23);
        Point point4=new Point(mat.cols()*0.55,mat.rows()*0.36);
        List<Point> list=new ArrayList<>();
        list.add(point1);
        list.add(point2);
        list.add(point3);
        list.add(point4);
        Mat nation= shear(mat,list);
        Imgcodecs.imwrite("D:/Download/nation.jpg", nation);
        BufferedImage nationBuffer=OpencvUtil.Mat2BufImg(nation,".jpg");
        String nationStr=OCRUtil.getImageMessage(convertImage2(nationBuffer),"chi_sim");
        nationStr=nationStr.replace("\n","");
        return nationStr+"\n";
    }

    public static String birthday(Mat mat) throws Exception {
        Point point1=new Point(mat.cols()*0.18,mat.rows()*0.37);
        Point point2=new Point(mat.cols()*0.18,mat.rows()*0.37);
        Point point3=new Point(mat.cols()*0.55,mat.rows()*0.48);
        Point point4=new Point(mat.cols()*0.55,mat.rows()*0.48);
        List<Point> list=new ArrayList<>();
        list.add(point1);
        list.add(point2);
        list.add(point3);
        list.add(point4);
        Mat birthday= shear(mat,list);
        birthday= OpencvUtil.drawContours(birthday,50);
        Imgcodecs.imwrite("D:/Download/birthday.jpg", birthday);
        //年份
        Point yearPoint1=new Point(0,0);
        Point yearPoint2=new Point(0,birthday.rows());
        Point yearPoint3=new Point(birthday.cols()*0.33,0);
        Point yearPoint4=new Point(birthday.cols()*0.33,birthday.rows());
        List<Point> yearList=new ArrayList<>();
        yearList.add(yearPoint1);
        yearList.add(yearPoint2);
        yearList.add(yearPoint3);
        yearList.add(yearPoint4);
        Mat year= shear(birthday,yearList);
        Imgcodecs.imwrite("D:/Download/year.jpg", year);
        BufferedImage yearBuffer=OpencvUtil.Mat2BufImg(year,".jpg");
        String yearStr= OCRUtil.getImageMessage(convertImage(yearBuffer),"eng");

        //月份
        Point monthPoint1=new Point(birthday.cols()*0.44,0);
        Point monthPoint2=new Point(birthday.cols()*0.44,birthday.rows());
        Point monthPoint3=new Point(birthday.cols()*0.58,0);
        Point monthPoint4=new Point(birthday.cols()*0.58,birthday.rows());
        List<Point> monthList=new ArrayList<>();
        monthList.add(monthPoint1);
        monthList.add(monthPoint2);
        monthList.add(monthPoint3);
        monthList.add(monthPoint4);
        Mat month= shear(birthday,monthList);
        Imgcodecs.imwrite("D:/Download/month.jpg", month);
        BufferedImage monthBuffer=OpencvUtil.Mat2BufImg(month,".jpg");
        String monthStr=OCRUtil.getImageMessage(monthBuffer,"eng");

        //日期
        Point dayPoint1=new Point(birthday.cols()*0.69,0);
        Point dayPoint2=new Point(birthday.cols()*0.69,birthday.rows());
        Point dayPoint3=new Point(birthday.cols()*0.83,0);
        Point dayPoint4=new Point(birthday.cols()*0.83,birthday.rows());
        List<Point> dayList=new ArrayList<>();
        dayList.add(dayPoint1);
        dayList.add(dayPoint2);
        dayList.add(dayPoint3);
        dayList.add(dayPoint4);
        Mat day= shear(birthday,dayList);
        Imgcodecs.imwrite("D:/Download/day.jpg", day);
        BufferedImage dayBuffer=OpencvUtil.Mat2BufImg(day,".jpg");
        String dayStr=OCRUtil.getImageMessage(convertImage(dayBuffer),"eng");

        String birthdayStr=yearStr+"年"+monthStr+"月"+dayStr+"日";
        birthdayStr=birthdayStr.replace("\n","");
        return birthdayStr+"\n";
    }

    public static String address(Mat mat) throws Exception {
        Point point1=new Point(mat.cols()*0.17,mat.rows()*0.47);
        Point point2=new Point(mat.cols()*0.17,mat.rows()*0.47);
        Point point3=new Point(mat.cols()*0.63,mat.rows()*0.76);
        Point point4=new Point(mat.cols()*0.63,mat.rows()*0.76);
        List<Point> list=new ArrayList<>();
        list.add(point1);
        list.add(point2);
        list.add(point3);
        list.add(point4);
        Mat address= shear(mat,list);
        address=OpencvUtil.drawContours(address,50);
        Imgcodecs.imwrite("D:/Download/address.jpg", address);
        BufferedImage addressBuffer=OpencvUtil.Mat2BufImg(address,".jpg");
        String addressStr = OCRUtil.getImageMessage(convertImage(addressBuffer), "chi_sim");
        addressStr=addressStr.replace("\n","");
        return addressStr+"\n";
    }

    public static String card(Mat mat){
        Point point1=new Point(mat.cols()*0.34,mat.rows()*0.75);
        Point point2=new Point(mat.cols()*0.34,mat.rows()*0.75);
        Point point3=new Point(mat.cols()*0.92,mat.rows()*0.91);
        Point point4=new Point(mat.cols()*0.92,mat.rows()*0.91);
        List<Point> list=new ArrayList<>();
        list.add(point1);
        list.add(point2);
        list.add(point3);
        list.add(point4);
        Mat card= shear(mat,list);
        card=OpencvUtil.drawContours(card,50);
        Imgcodecs.imwrite("D:/Download/card.jpg", card);
        BufferedImage cardBuffer=OpencvUtil.Mat2BufImg(card,".jpg");
        return OCRUtil.getImageMessage(cardBuffer,"eng")+"\n";
    }

    /**
     * 身份证反面识别
     */
    public static void cardDown(Mat mat){
        //灰度
        mat= OpencvUtil.gray(mat);
        //二值化
        mat=OpencvUtil.binary(mat);
        //腐蚀
        mat=OpencvUtil.erode(mat,3);
        //膨胀
        mat=OpencvUtil.dilate(mat,3);

        //检测是否有居民身份证字体，若有为正向，若没有则旋转图片
        for(int i=0;i<4;i++){
            String temp=temp(mat);
            if(!temp.contains("居")&&!temp.contains("民")){
                mat= rotate3(mat,90);
            }else{
                break;
            }
        }

        Imgcodecs.imwrite("D:/Download/result.jpg", mat);
        String organization=organization (mat);
        System.out.print("签发机关是："+organization);

        String time=time (mat);
        System.out.print("有效期限是："+time);
    }

    public static String temp (Mat mat){
        Point point1=new Point(mat.cols()*0.30,mat.rows()*0.25);
        Point point2=new Point(mat.cols()*0.30,mat.rows()*0.25);
        Point point3=new Point(mat.cols()*0.90,mat.rows()*0.45);
        Point point4=new Point(mat.cols()*0.90,mat.rows()*0.45);
        List<Point> list=new ArrayList<>();
        list.add(point1);
        list.add(point2);
        list.add(point3);
        list.add(point4);
        Mat temp= shear(mat,list);

        List<MatOfPoint> nameContours=OpencvUtil.findContours(temp);
        for (int i = 0; i < nameContours.size(); i++)
        {
            double area=OpencvUtil.area(nameContours.get(i));
            if(area<100){
                Imgproc.drawContours(temp, nameContours, i, new Scalar( 0, 0, 0), -1);
            }
        }
        Imgcodecs.imwrite("D:/Download/temp.jpg", temp);
        BufferedImage nameBuffer=OpencvUtil.Mat2BufImg(temp,".jpg");
        String nameStr= OCRUtil.getImageMessage(nameBuffer,"chi_sim");
        nameStr=nameStr.replace("\n","");
        return nameStr;
    }

    public static String organization (Mat mat){
        Point point1=new Point(mat.cols()*0.36,mat.rows()*0.68);
        Point point2=new Point(mat.cols()*0.36,mat.rows()*0.68);
        Point point3=new Point(mat.cols()*0.80,mat.rows()*0.80);
        Point point4=new Point(mat.cols()*0.80,mat.rows()*0.80);
        List<Point> list=new ArrayList<>();
        list.add(point1);
        list.add(point2);
        list.add(point3);
        list.add(point4);
        Mat name= shear(mat,list);

        List<MatOfPoint> nameContours=OpencvUtil.findContours(name);
        for (int i = 0; i < nameContours.size(); i++)
        {
            double area=OpencvUtil.area(nameContours.get(i));
            if(area<100){
                Imgproc.drawContours(name, nameContours, i, new Scalar( 0, 0, 0), -1);
            }
        }
        Imgcodecs.imwrite("D:/Download/organization.jpg", name);
        BufferedImage nameBuffer=OpencvUtil.Mat2BufImg(name,".jpg");
        String nameStr=OCRUtil.getImageMessage(nameBuffer,"chi_sim");
        nameStr=nameStr.replace("\n","");
        return nameStr+"\n";
    }

    public static String time (Mat mat){
        Point point1=new Point(mat.cols()*0.38,mat.rows()*0.82);
        Point point2=new Point(mat.cols()*0.38,mat.rows()*0.82);
        Point point3=new Point(mat.cols()*0.85,mat.rows()*0.92);
        Point point4=new Point(mat.cols()*0.85,mat.rows()*0.92);
        List<Point> list=new ArrayList<>();
        list.add(point1);
        list.add(point2);
        list.add(point3);
        list.add(point4);
        Mat time= shear(mat,list);

        List<MatOfPoint> timeContours=OpencvUtil.findContours(time);
        for (int i = 0; i < timeContours.size(); i++)
        {
            double area=OpencvUtil.area(timeContours.get(i));
            if(area<100){
                Imgproc.drawContours(time, timeContours, i, new Scalar( 0, 0, 0), -1);
            }
        }
        Imgcodecs.imwrite("D:/Download/time.jpg", time);

        //起始日期
        Point startPoint1=new Point(0,0);
        Point startPoint2=new Point(0,time.rows());
        Point startPoint3=new Point(time.cols()*0.47,0);
        Point startPoint4=new Point(time.cols()*0.47,time.rows());
        List<Point> startList=new ArrayList<>();
        startList.add(startPoint1);
        startList.add(startPoint2);
        startList.add(startPoint3);
        startList.add(startPoint4);
        Mat start= shear(time,startList);
        Imgcodecs.imwrite("D:/Download/start.jpg", start);
        BufferedImage yearBuffer=OpencvUtil.Mat2BufImg(start,".jpg");
        String startStr=OCRUtil.getImageMessage(yearBuffer,"eng");
        startStr=startStr.replace("-","");
        startStr=startStr.replace(" ","");
        startStr=startStr.replace("\n","");

        //截止日期
        Point endPoint1=new Point(time.cols()*0.47,0);
        Point endPoint2=new Point(time.cols()*0.47,time.rows());
        Point endPoint3=new Point(time.cols(),0);
        Point endPoint4=new Point(time.cols(),time.rows());
        List<Point> endList=new ArrayList<>();
        endList.add(endPoint1);
        endList.add(endPoint2);
        endList.add(endPoint3);
        endList.add(endPoint4);
        Mat end= shear(time,endList);
        Imgcodecs.imwrite("D:/Download/end.jpg", end);
        BufferedImage endBuffer=OpencvUtil.Mat2BufImg(end,".jpg");
        String endStr=OCRUtil.getImageMessage(endBuffer,"chi_sim");
        if(!endStr.contains("长")&&!endStr.contains("期")){
            endStr=OCRUtil.getImageMessage(endBuffer,"eng");
            endStr=endStr.replace("-","");
            endStr=endStr.replace(" ","");
        }

        return startStr+"-"+endStr;
    }
}
