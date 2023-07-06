package com.jlkj.imageIdentify.utils;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

import java.awt.image.BufferedImage;

public class OCRUtil {
    /**
     * 识别图片信息
     * @param img
     * @return
     */
    public static String getImageMessage(BufferedImage img,String language){
        String result="end";
        try{
            ITesseract tesseract = new Tesseract();
            //设置中文字体库路径,不设置 默认为resources下
//          tesseract.setDatapath("D:\\tessdata");
            //中文识别
            tesseract.setLanguage(language);
            result = tesseract.doOCR(img);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }
}
