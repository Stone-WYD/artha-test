package com.wyd.web.front;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.net.URL;

/**
 * @author xh
 * @date 2024-12-16
 * @Description:
 */
@Configuration
@ConditionalOnProperty(name = "comic.content.resource", havingValue = "local")
public class ResourcesConfig implements WebMvcConfigurer
{
    private static final String classPath;
    static {
        // 获取当前类加载器
        ClassLoader classLoader = ResourcesConfig.class.getClassLoader();
        // 获取资源，"/"代表classpath的根目录
        URL resource = classLoader.getResource("");
        // 获取classpath的绝对路径
        assert resource != null : "未能获取项目路径";
        classPath = resource.getPath();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        /** 通过url访问项目外的目录图片*/
        registry.addResourceHandler("/comic/**")
                .addResourceLocations("file:" + classPath + File.separator + "comicResource" + File.separator);
    }

    public static String getResourcePath() {
        return classPath + File.separator + "comicResource" + File.separator;
    }

    public static String getUrlPrefix() {
        return "/comic/";
    }

}
