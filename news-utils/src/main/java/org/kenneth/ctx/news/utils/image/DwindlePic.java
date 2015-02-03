package org.kenneth.ctx.news.utils.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

//缩略图类，
//本java类能将jpg图片文件，进行等比或非等比的大小转换。
//具体使用方法
//s_pic(大图片路径,生成小图片路径,大图片文件名,生成小图片文名,生成小图片宽度,生成小图片高度,是否等比缩放(默认为true))
public class DwindlePic {

    public static boolean s_pic(InputStream input, String targetDir, String outputFilename, int width, int height, boolean dbZoon) {

        boolean writeResult = false;

        File dir = new File(targetDir);
        if (dir.exists() == false) {
            dir.mkdirs();
        }

        File file = new File(targetDir + "/" + outputFilename);
        FileOutputStream tempout = null;
        try {
            tempout = new FileOutputStream(file);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        Image img = null;
        try {
            img = ImageIO.read(input);
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (img.getWidth(null) == -1) {
            return false;
        } else {
            int new_w;
            int new_h;
            if (dbZoon) {
                //为等比缩放计算输出的图片宽度及高度
                double rate1 = ((double) img.getWidth(null)) / (double) width +
                        0.1;
                double rate2 = ((double) img.getHeight(null)) / (double) height +
                        0.1;
                double rate = rate1 > rate2 ? rate1 : rate2;
                new_w = (int) (((double) img.getWidth(null)) / rate);
                new_h = (int) (((double) img.getHeight(null)) / rate);
            } else {
                new_w = width;
                new_h = height;
            }

            BufferedImage buffImg = new BufferedImage(new_w, new_h,
                    BufferedImage.TYPE_INT_RGB);

            Graphics g = buffImg.createGraphics();

            g.setColor(Color.white);
            g.fillRect(0, 0, new_w, new_h);

            g.drawImage(img, 0, 0, new_w, new_h, null);
            g.dispose();

            String fileFormat = outputFilename.substring(outputFilename.lastIndexOf(".") + 1);
            try {
                writeResult = ImageIO.write(buffImg, fileFormat, tempout);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return writeResult;
    }

}