package com.jlkj.imageIdentify.utils;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author
 * @name openCV - Mat 对象与 BufferedImage 互转
 * @date 2019/3/1 - 11:10
 */
public class ImgChangeUtil {

    /**
     * Mat转换成BufferedImage
     *
     * @param matrix
     *            要转换的Mat
     * @return
     */
    public static BufferedImage Mat2BufImg(Mat matrix)throws IOException {
        MatOfByte mob=new MatOfByte();
        Imgcodecs.imencode(".png", matrix, mob);
        return ImageIO.read(new ByteArrayInputStream(mob.toArray()));
    }


    /**
     * BufferedImage转换成Mat
     *            要转换的BufferedImage
     */
    public static Mat BufImg2Mat (BufferedImage bi) {
        Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
        byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
        mat.put(0, 0, data);
        return mat;
    }
}
