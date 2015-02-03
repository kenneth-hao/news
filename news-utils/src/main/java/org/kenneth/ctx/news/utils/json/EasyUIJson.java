package org.kenneth.ctx.news.utils.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import org.kenneth.ctx.news.utils.mybatis.PageParameter;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/13.
 */
public class EasyUIJson {

    public static <T> String datagrid(List<T> list, PageParameter page) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rows", list);
        map.put("total", page.getTotalCount());
        return JSON.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
    }

    public static <T> String tree(List<T> list, final String idField, final String textField, final String childrenField) {
        NameFilter nf = new NameFilter() {
            @Override
            public String process(Object object, String name, Object value) {
                if (name.equals(idField)) {
                    return "id";
                }
                if (name.equals(textField)) {
                    return "text";
                }
                if (name.equals(childrenField)) {
                    return "children";
                }
                return name;
            }

        };
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
        filter.getIncludes().add(idField);
        filter.getIncludes().add(textField);
        filter.getIncludes().add(childrenField);
        SerializeFilter[] filters = new SerializeFilter[2];
        filters[0] = nf;
        filters[1] = filter;

        return JSON.toJSONString(list, filters);
    }

    public static <T> String treegrid(List<T> list) {
        return JSON.toJSONString(list);
    }
}
