package com.wyd.web.front.bean.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xh
 * @date 2024-12-16
 * @Description:
 */
@Data
public class ContentQuery implements Serializable {

    private String comicName;

    // 1.图片 2.视频
    private Integer type;

    private Integer pageSize;

    private Integer pageNum;
}
