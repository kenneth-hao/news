package org.kenneth.ctx.news.utils.springmvc;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2015/1/13.
 */
public class MyDataBinding implements WebBindingInitializer {

    public void initBinder(WebDataBinder binder, WebRequest request) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        SimpleDateFormat datetimeFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        datetimeFormat.setLenient(true);


        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(
                datetimeFormat, true));

        binder.registerCustomEditor(java.sql.Timestamp.class, new CustomTimestampEditor(datetimeFormat, true));
    }
}
