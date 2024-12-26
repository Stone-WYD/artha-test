package com.wyd.web.front.service.impl;


import com.wyd.web.front.ResourcesConfig;
import com.wyd.web.front.bean.param.ContentQuery;
import com.wyd.web.front.service.ContentService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xh
 * @date 2024-12-16
 * @Description:
 */
@Service
@ConditionalOnBean(ResourcesConfig.class)
public class LocalContentService implements ContentService {

    @Override
    public List<String> getContent(ContentQuery query) {
        String urlPrefix = ResourcesConfig.getUrlPrefix() + query.getComicName() + File.separator;
        File dir = new File(ResourcesConfig.getResourcePath() + query.getComicName());
        List<String> result = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (query.getType() == 1) {
                    // 图片
                    if (isImage(file)) {
                        result.add(urlPrefix + file.getName());
                    }
                } else {
                    // 视频
                    if (isVideo(file)) {
                        result.add(urlPrefix + file.getName());
                    }
                }
            }
        }
        return result;
    }

    private static boolean isImage(File file) {
        String fileName = file.getName();
        // 获取文件扩展名
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 将扩展名转换为小写，方便比较
        extension = extension.toLowerCase();

        // 常见的图片文件扩展名
        String[] imageExtensions = {"jpg", "jpeg", "png", "gif", "bmp", "webp"};

        for (String imageExtension : imageExtensions) {
            if (extension.equals(imageExtension)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isVideo(File file) {
        String fileName = file.getName();
        // 获取文件扩展名
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 将扩展名转换为小写，方便比较
        extension = extension.toLowerCase();

        // 常见的视频文件扩展名
        String[] videoExtensions = {"mp4", "avi", "mov"};

        for (String videoExtension : videoExtensions) {
            if (extension.equals(videoExtension)) {
                return true;
            }
        }
        return false;
    }
}
