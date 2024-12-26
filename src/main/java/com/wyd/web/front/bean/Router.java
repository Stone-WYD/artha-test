package com.wyd.web.front.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xh
 * @date 2024-12-17
 * @Description:
 */
@Data
public class Router implements Serializable {

    private String name;

    private String to;

    private Integer order;
}
