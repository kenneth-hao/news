package org.kenneth.ctx.news.mapper;

import org.kenneth.ctx.news.entity.Resource;

import java.util.List;

public interface ResourceMapper extends BaseMapper<Resource> {

    List<Resource> queryAccountResources(String accountId);

}
