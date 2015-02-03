package org.kenneth.ctx.news.mapper;

import java.util.List;
import java.util.Map;


/**
 * 所有的Mapper继承这个接口
 * 已经实现民基本的 增,删,改,查接口,不需要重复写
 *
 * @author lanyuan
 * @version 1.0v
 * @date 2014-2-10
 * @Email: mmm333zzz520@163.com
 */
public interface BaseMapper<T> {

    Integer count(Map<String, Object> map);

    T queryOne(Map<String, Object> map);

    List<T> queryByPage(Map<String, Object> map);

    List<T> query(Map<String, Object> map);

    List<T> queryAll();

    void delete(String id);

    void deleteByVo(Map<String, Object> map);

    void update(T t);

    T getById(String id);

    Integer insert(T t);

}
