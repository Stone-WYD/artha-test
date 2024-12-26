package com.wyd.web.front.service;



import com.wyd.web.front.bean.param.ContentQuery;

import java.util.List;

/**
 * @author xh
 * @date 2024-12-16
 * @Description:
 */
public interface ContentService {
    List<String> getContent(ContentQuery query);
}
