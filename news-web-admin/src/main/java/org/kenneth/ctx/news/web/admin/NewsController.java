package org.kenneth.ctx.news.web.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.kenneth.ctx.news.biz.ChannelViewBiz;
import org.kenneth.ctx.news.biz.NewsService;
import org.kenneth.ctx.news.entity.ChannelView;
import org.kenneth.ctx.news.entity.News;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/12.
 */
@Controller
@RequestMapping("/admin/news")
public class NewsController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(NewsController.class);

    private final String TEMPLATE_DIR = "news/";

    @Autowired
    private NewsService newsService;

    @Autowired
    private ChannelViewBiz channelViewBiz;

    @RequestMapping("/to_add")
    public ModelAndView toAdd() {
        ModelAndView mav = new ModelAndView(TEMPLATE_DIR + "add");
        QueryCondition qc = new QueryCondition();
        qc.setChannelType(ChannelView.S_TYPE_CATEGORY);
        List<ChannelView> channelViewList = channelViewBiz.query(qc);
        mav.addObject("channelViewList", channelViewList);
        return mav;
    }

    @RequestMapping("/to_preview")
    public ModelAndView toPreview(String nid) {
        ModelAndView mav = new ModelAndView(TEMPLATE_DIR + "preview");
        News previewNews = newsService.queryPreviewNews(nid);
        mav.addObject("previewNews", previewNews);
        return mav;
    }

    @RequestMapping("/to_update")
    public ModelAndView toUpdate(String nid) {
        ModelAndView mav = new ModelAndView(TEMPLATE_DIR + "update");
        News updatingNews = newsService.getById(nid);
        QueryCondition qc = new QueryCondition();
        qc.setChannelType(updatingNews.getChannelType());
        List<ChannelView> channelViewList = channelViewBiz.query(qc);
        mav.addObject("updatingNews", updatingNews);
        mav.addObject("channelViewList", channelViewList);
        return mav;
    }

    @RequestMapping("/to_list")
    public String toList() {
        return TEMPLATE_DIR + "list";
    }

    @RequestMapping("/to_recycle")
    public String toRecycle() {
        return TEMPLATE_DIR + "recycle";
    }

    @RequestMapping("/list")
    @ResponseBody
    public String list(QueryCondition qc, PageParameter pageParameter) {
        qc.setUserId(1);
        List<News> listNews = newsService.page(qc, pageParameter);
        return EasyUIJson.datagrid(listNews, pageParameter);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(News news) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            boolean res = newsService.delete(news);
            logger.info(String.format("删除新闻[%s]", news.getTitle()));
           /* String carouselImgPath = CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_PATH) +
                    news.getCarouselImgPath();
            String listImgPath = CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_PATH) +
                    news.getListImgPath();

            if (FileUtils.deleteQuietly(new File(carouselImgPath))) {
                logger.info(String.format("删除新闻[%s] 的轮播图", news.getTitle()));
            }
            if (FileUtils.deleteQuietly(new File(listImgPath))) {
                logger.info(String.format("删除新闻[%s] 的列表图", news.getTitle()));
            }*/
            map.put("result", "ok");
        } catch (Exception e) {
            map.put("result", "failure");
            map.put("msg", "服务器出现错误");
            e.printStackTrace();
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "revert", method = RequestMethod.POST)
    @ResponseBody
    public String revert(News news) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            boolean res = newsService.revert(news);
            logger.info(String.format("恢复新闻[%s]", news.getTitle()));
            map.put("result", "ok");
        } catch (Exception e) {
            map.put("result", "failure");
            map.put("msg", "服务器出现错误");
            e.printStackTrace();
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "updateTop", method = RequestMethod.POST)
    @ResponseBody
    public String updateTop(News news) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            boolean res = newsService.updateTop(news);
            map.put("result", "ok");
        } catch (Exception e) {
            map.put("result", "failure");
            map.put("msg", "服务器出现错误");
            e.printStackTrace();
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "updateRecommand", method = RequestMethod.POST)
    @ResponseBody
    public String updateRecommand(News news) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            boolean res = newsService.updateRecommand(news);
            map.put("result", "ok");
        } catch (Exception e) {
            map.put("result", "failure");
            map.put("msg", "服务器出现错误");
            e.printStackTrace();
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "updateCarousel", method = RequestMethod.POST)
    @ResponseBody
    public String updateCarousel(News news) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            boolean res = newsService.updateCarousel(news);
            map.put("result", "ok");
        } catch (Exception e) {
            map.put("result", "failure");
            map.put("msg", "服务器出现错误");
            e.printStackTrace();
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public String updateStatus(News news) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            boolean res = newsService.updateStatus(news);
            map.put("result", "ok");
        } catch (Exception e) {
            map.put("result", "failure");
            map.put("msg", "服务器出现错误");
            e.printStackTrace();
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public String update(News news, MultipartFile carouselImg, MultipartFile listImg) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (carouselImg != null && !carouselImg.isEmpty()) {
                String filePath = HttpFileUploadUtils.uploadImage(carouselImg,
                        CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_PATH),
                        CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_PATH_NEWS),
                        Integer.valueOf(CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_SCALE_NEW_CAROUSEL_WIDTH)),
                        Integer.valueOf(CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_SCALE_NEW_CAROUSEL_HEIGHT)));

                news.setCarouselImgPath(filePath);
            }
            if (listImg != null && !listImg.isEmpty()) {
                String filePath = HttpFileUploadUtils.uploadImage(listImg,
                        CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_PATH),
                        CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_PATH_NEWS),
                        Integer.valueOf(CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_SCALE_NEW_LIST_WIDTH)),
                        Integer.valueOf(CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_SCALE_NEW_LIST_HEIGHT)));

                news.setListImgPath(filePath);
            }
            boolean res = newsService.update(news);
            News updatedNews = newsService.getById(String.valueOf(news.getNid()));
            map.put("updatedNews", updatedNews);
            map.put("result", "ok");
        } catch (Exception e) {
            map.put("result", "failure");
            map.put("msg", "服务器出现错误");
            e.printStackTrace();
        }
        return JSON.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public String add(News news, MultipartFile carouselImg, MultipartFile listImg, HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            if (carouselImg != null && !carouselImg.isEmpty()) {
                String filePath = HttpFileUploadUtils.uploadImage(carouselImg,
                        CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_PATH),
                        CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_PATH_NEWS),
                        Integer.valueOf(CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_SCALE_NEW_CAROUSEL_WIDTH)),
                        Integer.valueOf(CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_SCALE_NEW_CAROUSEL_HEIGHT)));

                news.setCarouselImgPath(filePath);
            }
            if (listImg != null && !listImg.isEmpty()) {
                String filePath = HttpFileUploadUtils.uploadImage(listImg,
                        CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_PATH),
                        CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_PATH_NEWS),
                        Integer.valueOf(CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_SCALE_NEW_LIST_WIDTH)),
                        Integer.valueOf(CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_SCALE_NEW_LIST_HEIGHT)));

                news.setListImgPath(filePath);
            }
            newsService.add(news, request.getContextPath());
            map.put("result", "ok");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return JSON.toJSONString(map);
    }


    @RequestMapping(value = "uploadContentImg", method = RequestMethod.POST)
    @ResponseBody
    public String uploadContentImg(MultipartFile contentImg) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            if (contentImg != null && !contentImg.isEmpty()) {
                String filePath = HttpFileUploadUtils.uploadImage(contentImg,
                        CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_PATH),
                        CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_PATH_NEWS),
                        Integer.valueOf(CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_SCALE_NEW_CONTENT_WIDTH)),
                        Integer.valueOf(CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_SCALE_NEW_CONTENT_HEIGHT)));
            }
            map.put("result", "ok");
        } catch (Exception e) {
            map.put("result", "failure");
            e.printStackTrace();
        }

        return JSON.toJSONString(map);
    }
}
