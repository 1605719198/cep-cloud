package com.jlkj.imageIdentify.utils;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Liukuiyan
 * @name openCV - Mat 对象与 BufferedImage 互转
 * @date 2023/7/3
 */
public class ImgChangeUtil {

    /**
     * Mat转换成BufferedImage
     *
     * @param matrix
     *            要转换的Mat
     * @param fileExtension
     *            格式为 ".jpg", ".png", etc
     * @return
     */
    public static BufferedImage Mat2BufImg (Mat matrix, String fileExtension) throws IOException {
        // convert the matrix into a matrix of bytes appropriate for
        // this file extension
        MatOfByte mob = new MatOfByte();
        Imgcodecs.imencode(fileExtension, matrix, mob);
        // convert the "matrix of bytes" into a byte array
        byte[] byteArray = mob.toArray();
        BufferedImage bufImage = null;
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bufImage;
    }

    /**
     * BufferedImage转换成Mat
     *
     */
    public static Mat BufImg2Mat (BufferedImage image) throws IOException {
//已失效
//        if (original == null) {
//            throw new IllegalArgumentException("original == null");
//        }
//        if (original.getType() != imgType) {
//            BufferedImage image = new BufferedImage(original.getWidth(), original.getHeight(), imgType);
//            Graphics2D g = image.createGraphics();
//            try {
//                g.setComposite(AlphaComposite.Src);
//                g.drawImage(original, 0, 0, null);
//            } finally {
//                g.dispose();
//            }
//        }
//        DataBufferByte dbi =(DataBufferByte)original.getRaster().getDataBuffer();
//        byte[] pixels = dbi.getData();
//        Mat mat = Mat.eye(original.getHeight(), original.getWidth(), matType);
//        mat.put(0, 0, pixels);
//        return mat;
//灰度图片
//        Mat out;
//        byte[] data;
//        int r, g, b;
//        if (in.getType() == BufferedImage.TYPE_INT_RGB) {
//            out = new Mat(in.getHeight(), in.getWidth(), CvType.CV_8UC3);
//            data = new byte[in.getWidth() * in.getHeight() * (int) out.elemSize()];
//            int[] dataBuff = in.getRGB(0, 0, in.getWidth(), in.getHeight(), null, 0, in.getWidth());
//            for (int i = 0; i < dataBuff.length; i++) {
//                data[i * 3] = (byte) ((dataBuff[i] >> 0) & 0xFF);
//                data[i * 3 + 1] = (byte) ((dataBuff[i] >> 8) & 0xFF);
//                data[i * 3 + 2] = (byte) ((dataBuff[i] >> 16) & 0xFF);
//            }
//        } else {
//            out = new Mat(in.getHeight(), in.getWidth(), CvType.CV_8UC1);
//            data = new byte[in.getWidth() * in.getHeight() * (int) out.elemSize()];
//            int[] dataBuff = in.getRGB(0, 0, in.getWidth(), in.getHeight(), null, 0, in.getWidth());
//            for (int i = 0; i < dataBuff.length; i++) {
//                r = (byte) ((dataBuff[i] >> 0) & 0xFF);
//                g = (byte) ((dataBuff[i] >> 8) & 0xFF);
//                b = (byte) ((dataBuff[i] >> 16) & 0xFF);
//                data[i] = (byte) ((0.21 * r) + (0.71 * g) + (0.07 * b));
//            }
//        }
//        out.put(0, 0, data);
//        return out;

        image = convertTo3ByteBGRType(image);
        byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
        mat.put(0, 0, data);
        return mat;
    }

    private static BufferedImage convertTo3ByteBGRType(BufferedImage image) {
        BufferedImage convertedImage = new BufferedImage(image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_3BYTE_BGR);
        convertedImage.getGraphics().drawImage(image, 0, 0, null);
        return convertedImage;
    }
}
