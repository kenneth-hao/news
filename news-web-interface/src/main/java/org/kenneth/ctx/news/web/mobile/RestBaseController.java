package org.kenneth.ctx.news.web.mobile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import org.apache.commons.lang.StringUtils;
import org.kenneth.ctx.news.entity.User;
import org.kenneth.ctx.news.utils.constant.HttpCode;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by Administrator on 2015/1/5.
 */
@Controller
public abstract class RestBaseController {

    private Logger logger = LoggerFactory.getLogger(RestBaseController.class);

    public static final String TAG_MSG = "msg";
    public static final String TAG_CODE = "code";
    public static final String TAG_DATA = "data";
    public static final String TAG_MORE_DATA = "more";
    public static final String TAG_PAGE_CURRENT = "currentPage";
    public static final String TAG_PAGE_SIZE = "pageSize";
    public static final String TAG_SESSION_ID = "JSESSIONID";

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpSession session;

    protected User currentUser() {
        User u = (User) session.getAttribute("user");
        return u;
    }


    protected String error(Exception e) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(TAG_CODE, HttpCode.SERVER_ERROR);
        map.put(TAG_MSG, e.getMessage());
        map = setSessionId(map);
        return toJSONString(map, null, null);
    }

    protected String failure(int failureCode, String msg) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(TAG_CODE, failureCode);
        map.put(TAG_MSG, msg);
        map = setSessionId(map);
        return toJSONString(map, null, null);
    }

    protected String success() {
        return success(null);
    }

    protected String success(Object object, SerializerFeature... featuries) {
        Map<String, Object> map = this.getBaseSuccessMap(object);
        return toJSONString(map, null, null);
    }

    protected String success(Object object, SerializeFilter filter, SerializerFeature... featuries) {
        Map<String, Object> map = this.getBaseSuccessMap(object);
        return toJSONString(map, null, filter);
    }

    protected String success(Object object, PageParameter page) {
        Map<String, Object> map = this.getPageSuccessMap(object, page);
        return toJSONString(map, null, null);
    }

    protected String success(Object object, PageParameter page, SerializeFilter filter) {
        Map<String, Object> map = this.getPageSuccessMap(object, page);
        return toJSONString(map, null, filter);
    }

    protected String success(Object object, PageParameter page, String dateFormat) {
        Map<String, Object> map = this.getPageSuccessMap(object, page);
        return toJSONString(map, dateFormat, null);
    }

    private Map<String, Object> getPageSuccessMap(Object object, PageParameter page, SerializerFeature... featuries) {
        Map<String, Object> map = this.getBaseSuccessMap(object);
        map.put(TAG_MORE_DATA, page.getTotalPage() > page.getCurrentPage());
        map.put(TAG_PAGE_CURRENT, page.getCurrentPage());
        map.put(TAG_PAGE_SIZE, page.getPageSize());
        map = setSessionId(map);
        return map;
    }

    private Map<String, Object> getBaseSuccessMap(Object object) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(TAG_CODE, HttpCode.OK);
        map.put(TAG_DATA, object);
        map = setSessionId(map);
        return map;
    }

    private String toJSONString(Object object, String dateFormat, SerializeFilter filter, SerializerFeature... featuries) {
        List<SerializerFeature> featureList = new ArrayList<SerializerFeature>();
        if (StringUtils.isEmpty(dateFormat) == false) {
            JSON.DEFFAULT_DATE_FORMAT = dateFormat;
        }

        featureList.add(SerializerFeature.WriteDateUseDateFormat);
        for (SerializerFeature tmpFeature : featuries) {
            featureList.add(tmpFeature);
        }

        if (filter == null) {
            filter = new SimplePropertyPreFilter();
            ((SimplePropertyPreFilter) filter).getExcludes().addAll(getExculdeFields());
        }

        return JSON.toJSONString(object, filter, featureList.toArray(new SerializerFeature[featureList.size()]));
    }

    private Map<String, Object> setSessionId(Map<String, Object> map) {
        String JSESSIONID = RequestContextHolder.getRequestAttributes().getSessionId();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            System.out.println("key : " + key + " --- value : " + value);
        }

        map.put(TAG_SESSION_ID, JSESSIONID);
        logger.info(String.format("本次请求Session标识[ JSESSIONID : %s ]", JSESSIONID));
        return map;
    }

    protected Set<String> getExculdeFields() {
        return new HashSet<String>();
    }
}
