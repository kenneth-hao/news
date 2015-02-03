package org.kenneth.ctx.news.utils.upload;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.kenneth.ctx.news.utils.exception.BaseServiceException;
import org.kenneth.ctx.news.utils.image.DwindlePic;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2015/1/21.
 */
public class HttpFileUploadUtils {

    private HttpFileUploadUtils() {
    }

    public static String upload(MultipartFile file, String imgDir, String categoryDir) throws IOException {
        String todayStr = DateFormatUtils.format(new Date(), "yyyyMMdd");

        String newFileName = genFilename(file.getOriginalFilename());
        String targetDir = imgDir + categoryDir + "/" + todayStr;
        String returnFilePath = categoryDir + "/" + todayStr + "/" + newFileName;

        //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的
        FileUtils.copyInputStreamToFile(file.getInputStream(), new File(targetDir, newFileName));

        return returnFilePath;
    }


    public static String uploadImage(MultipartFile file, String imgDir, String categoryDir, int width, int height) throws IOException {

        String todayStr = DateFormatUtils.format(new Date(), "yyyyMMdd");

        String newFileName = genFilename(file.getOriginalFilename());
        String targetDirStr = imgDir + categoryDir + "/" + todayStr;
        String returnFilePath = categoryDir + "/" + todayStr + "/" + newFileName;
        Thumbnails.of(file.getInputStream()).size(width, height).toOutputStream(FileUtils.openOutputStream(new File(targetDirStr, newFileName)));

        return returnFilePath;
    }

    private static String genFilename(String origFilename) {
        String suffix = origFilename.substring(origFilename.lastIndexOf("."));
        return UUID.randomUUID().toString().replace("-", "") + suffix;
    }

    private static String getSuffix(String origFilename) {
        return origFilename.substring(origFilename.lastIndexOf("."));
    }

    private static String getFileformat(String origFilename) {
        return origFilename.substring(origFilename.lastIndexOf(".") + 1);
    }
}
