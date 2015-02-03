package org.kenneth.ctx.news.web.mobile;

import org.kenneth.ctx.news.biz.RightsProtectionService;
import org.kenneth.ctx.news.entity.RightsProtection;
import org.kenneth.ctx.news.utils.constant.ConfigPropKey;
import org.kenneth.ctx.news.utils.constant.LogConstants;
import org.kenneth.ctx.news.utils.spring.CustomPropertyConfigurer;
import org.kenneth.ctx.news.utils.upload.HttpFileUploadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2015/1/8.
 */
@Controller
@RequestMapping("/rest/rights")
public class RestRightsProtectionController extends RestBaseController {


    private Logger logger = LoggerFactory.getLogger(RestBaseController.class);

    @Autowired
    private RightsProtectionService rightsProtectionService;

    @RequestMapping(value = "/commit", method = RequestMethod.POST)
    @ResponseBody
    public String commit(RightsProtection rightsProtection, @RequestParam MultipartFile[] uploadImgs) {
        try {
            StringBuffer uploadImgsPath = new StringBuffer("");
            for (int i = 0; i < uploadImgs.length; ++i) {
                MultipartFile myfile = uploadImgs[i];
                if (i != 0) {
                    uploadImgsPath.append(":");
                }
                if (myfile.isEmpty()) {
                    System.out.println("文件未上传");
                } else {
                    String filePath = HttpFileUploadUtils.uploadImage(myfile,
                            CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_PATH),
                            CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_PATH_RIGHT_PROTECTION),
                            Integer.valueOf(CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_SCALE_RIGHTS_WIDTH)),
                            Integer.valueOf(CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_SCALE_RIGHTS_HEIGHT))
                    );
                    if (i == 0) {
                        rightsProtection.setListImgPath(filePath);
                    }
                    uploadImgsPath.append(filePath);
                }
            }
            rightsProtection.setDetailMultiImgPath(uploadImgsPath.toString());
            rightsProtectionService.commit(rightsProtection);
            return success();
        } catch (RuntimeException e) {
            e.printStackTrace();
            logger.error(LogConstants.FLAG_ERROR, e.getMessage());
            return error(e);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(LogConstants.FLAG_ERROR, e.getMessage());
            return error(e);
        }
    }

}
