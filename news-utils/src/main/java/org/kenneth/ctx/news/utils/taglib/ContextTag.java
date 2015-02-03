package org.kenneth.ctx.news.utils.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by Administrator on 2015/1/4.
 */
public class ContextTag extends TagSupport {

    /**
     * 输出ContextPath
     */
    public int doEndTag() {

        try {
            HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
            //输出contextPath
            pageContext.getOut().print(request.getContextPath());
        } catch (IOException ignored) {
        }
        return EVAL_PAGE;
    }

}
