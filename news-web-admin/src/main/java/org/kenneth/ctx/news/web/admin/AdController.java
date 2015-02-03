package org.kenneth.ctx.news.web.admin;

import com.alibaba.fastjson.JSON;
import org.kenneth.ctx.news.biz.AdBiz;
import org.kenneth.ctx.news.entity.Ad;
import org.kenneth.ctx.news.utils.constant.ConfigPropKey;
import org.kenneth.ctx.news.utils.exception.BaseServiceException;
import org.kenneth.ctx.news.utils.json.EasyUIJson;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.utils.spring.CustomPropertyConfigurer;
import org.kenneth.ctx.news.utils.upload.HttpFileUploadUtils;
import org.kenneth.ctx.news.vo.AdVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/26.
 */
@Controller
@RequestMapping("admin/ad")
public class AdController extends BaseController {

    private final String TEMPLATE_DIR = "ad/";

    @Autowired
    AdBiz adBiz;

    @RequestMapping(value = "/to_list")
    public ModelAndView toList() {
        ModelAndView mav = new ModelAndView(TEMPLATE_DIR + "list");
        return mav;
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public String list(AdVo vo, PageParameter pageParameter) {
        List<Ad> logLoginList = adBiz.page(vo, pageParameter);
        return EasyUIJson.datagrid(logLoginList, pageParameter);
    }

    @RequestMapping(value = "/to_add")
    public ModelAndView toAdd() {
        ModelAndView mav = new ModelAndView(TEMPLATE_DIR + "add");
        return mav;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public String add(Ad ad, @RequestParam MultipartFile[] uploadAdImg) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            for (MultipartFile myfile : uploadAdImg) {
                if (myfile.isEmpty()) {
                    System.out.println("文件未上传");
                } else {
                   String filePath = HttpFileUploadUtils.uploadImage(myfile,
                            CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_PATH),
                            CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_PATH_AD),
                            Integer.valueOf(CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_SCALE_AD_WIDTH)),
                            Integer.valueOf(CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_SCALE_AD_HEIGHT)));

                    ad.setImgPath(filePath);
                }
            }
            adBiz.add(ad);
            map.put("result", "ok");
        } catch (BaseServiceException e) {
            map.put("msg", e.getMessage());
        }
        return JSON.toJSONString(map);
    }

}
