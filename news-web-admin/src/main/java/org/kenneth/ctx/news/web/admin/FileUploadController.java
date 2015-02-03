package org.kenneth.ctx.news.web.admin;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.kenneth.ctx.news.utils.constant.ConfigPropKey;
import org.kenneth.ctx.news.utils.spring.CustomPropertyConfigurer;
import org.kenneth.ctx.news.utils.upload.HttpFileUploadUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 上传图片
 * <p>
 * 为CKEDITOR定制的图片上传功能，后续可以扩展上传其他格式的文件
 * </p>
 */
@Controller
@RequestMapping("/upload")
public class FileUploadController {

    protected final Logger logger = Logger.getLogger(FileUploadController.class);

    @RequestMapping(method = RequestMethod.GET)
    public void processUpload(MultipartFile upload, HttpServletRequest request, HttpServletResponse response) {
        processUploadPost(upload, request, response);
        return;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void processUploadPost(MultipartFile upload, HttpServletRequest request, HttpServletResponse response) {

        // 判断提交的请求是否包含文件
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (!isMultipart) {
            return;
        }

        try {
            String website = CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_WEBSITE);
            String imgpath = CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_PATH);
            String filePath = "";

            if (upload != null && !upload.isEmpty()) {
                if (logger.isInfoEnabled()) {
                    logger.info("开始上传文件:" + upload.getOriginalFilename());
                }

                filePath = HttpFileUploadUtils.uploadImage(upload, imgpath,
                        CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_PATH_NEWS),
                        Integer.valueOf(CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_SCALE_NEW_CONTENT_WIDTH)),
                        Integer.valueOf(CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_SCALE_NEW_CONTENT_HEIGHT)));
            }

            response.setContentType("text/html; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            PrintWriter out = response.getWriter();

            String fileUrl = "http://" + website + imgpath + filePath;

            // 组装返回url，以便于ckeditor定位图片
            // 去掉第一个/，否则ckeditor不识别

            // 将上传的图片的url返回给ckeditor
            String callback = request.getParameter("CKEditorFuncNum");
            out.println("<script type='text/javascript'>");
            out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + fileUrl + "','')");
            out.println("</script>");

            out.flush();
            out.close();

        } catch (IOException e) {
            logger.error("上传文件发生异常！", e);
        }

        return;
    }

}