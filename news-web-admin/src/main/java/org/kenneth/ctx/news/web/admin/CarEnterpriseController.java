package org.kenneth.ctx.news.web.admin;

import com.alibaba.fastjson.JSON;
import org.kenneth.ctx.news.biz.CarEnterpriseService;
import org.kenneth.ctx.news.entity.CarEnterprise;
import org.kenneth.ctx.news.utils.constant.ConfigPropKey;
import org.kenneth.ctx.news.utils.json.EasyUIJson;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.kenneth.ctx.news.utils.spring.CustomPropertyConfigurer;
import org.kenneth.ctx.news.utils.upload.HttpFileUploadUtils;
import org.kenneth.ctx.news.vo.QueryCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/16.
 */
@Controller
@RequestMapping("/admin/car_enterprise")
public class CarEnterpriseController extends BaseController {

    private final String TEMPLATE_DIR = "car_enterprise/";

    private Logger logger = LoggerFactory.getLogger(CarEnterpriseController.class);

    @Autowired
    private CarEnterpriseService carEnterpriseService;

    @RequestMapping("/query")
    @ResponseBody
    public String query(QueryCondition qc) {
        List<CarEnterprise> list = carEnterpriseService.query(qc);
        return JSON.toJSONString(list);
    }

    @RequestMapping("/to_add")
    public String toAdd() {
        return TEMPLATE_DIR + "add";
    }

    @RequestMapping("/to_update")
    public ModelAndView toUpdate(String id) {
        ModelAndView mav = new ModelAndView(TEMPLATE_DIR + "update");
        CarEnterprise updatingCarEnterprise = carEnterpriseService.getById(id);
        mav.addObject("updatingCarEnterprise", updatingCarEnterprise);
        return mav;
    }

    @RequestMapping("/to_list")
    public String toList() {
        return TEMPLATE_DIR + "list";
    }

    @RequestMapping("/list")
    @ResponseBody
    public String list(QueryCondition qc, PageParameter pageParameter) {
        List<CarEnterprise> list = carEnterpriseService.page(qc, pageParameter);
        return EasyUIJson.datagrid(list, pageParameter);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(CarEnterprise carEnterprise) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            boolean res = carEnterpriseService.delete(carEnterprise);
            map.put("result", "ok");
        } catch (Exception e) {
            map.put("result", "failure");
            map.put("msg", "服务器出现错误");
            e.printStackTrace();
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public String add(CarEnterprise carEnterprise, @RequestParam MultipartFile[] uploadBrandImg) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            //如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解
            //如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解
            //并且上传多个文件时，前台表单中的所有<input type="file"/>的name都应该是myfiles，否则参数里的myfiles无法获取到所有上传的文件
            for (MultipartFile myfile : uploadBrandImg) {
                if (myfile.isEmpty()) {
                    System.out.println("文件未上传");
                } else {

                    String filePath = HttpFileUploadUtils.uploadImage(myfile,
                            CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_PATH),
                            CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_PATH_CAR_ENTERPRISE),
                            Integer.valueOf(CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_SCALE_CAR_ENTERPRISE_WIDTH)),
                            Integer.valueOf(CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_SCALE_CAR_ENTERPRISE_HEIGHT)));

                    carEnterprise.setBrandImgPath(filePath);
                }
            }
            carEnterpriseService.add(carEnterprise);
            map.put("result", "ok");
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            e.printStackTrace();
        }

        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public String update(CarEnterprise carEnterprise, @RequestParam MultipartFile[] uploadBrandImg, HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            //如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解
            //如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解
            //并且上传多个文件时，前台表单中的所有<input type="file"/>的name都应该是myfiles，否则参数里的myfiles无法获取到所有上传的文件
            for (MultipartFile myfile : uploadBrandImg) {
                if (myfile.isEmpty()) {
                    // 文件未上传, 无需更新
                } else {
                    String filePath = HttpFileUploadUtils.uploadImage(myfile,
                            CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_PATH),
                            CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_PATH_CAR_ENTERPRISE),
                            Integer.valueOf(CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_SCALE_CAR_ENTERPRISE_WIDTH)),
                            Integer.valueOf(CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_SCALE_CAR_ENTERPRISE_HEIGHT)));

                    carEnterprise.setBrandImgPath(filePath);
                }
            }
            carEnterpriseService.update(carEnterprise);
            CarEnterprise updatedCarEnterprise = carEnterpriseService.getById(String.valueOf(carEnterprise.getCeid()));
            map.put("updatedCarEnterprise", updatedCarEnterprise);
            map.put("result", "ok");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return JSON.toJSONString(map);
    }

}
