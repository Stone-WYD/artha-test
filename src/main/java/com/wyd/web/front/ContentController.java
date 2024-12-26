package com.wyd.web.front;


import com.wyd.web.common.AjaxResult;
import com.wyd.web.front.bean.Router;
import com.wyd.web.front.bean.param.ContentQuery;
import com.wyd.web.front.service.ContentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xh
 * @date 2024-12-16
 * @Description:
 */
@RestController
@RequestMapping("/comic")
public class ContentController {

    @Resource
    private ContentService contentService;

    @GetMapping(value = "/router")
    public AjaxResult<List<Router>> getRouter() {
        List<Router> result = new ArrayList<>();
        Router router = new Router();
        router.setName("银魂");
        router.setTo("/comic/yinhun");
        router.setOrder(1);
        result.add(router);

        /*Router router2 = new Router();
        router2.setName("齐木楠雄的灾难");
        router2.setTo("/comic/qmnxdzn");
        router2.setOrder(2);
        result.add(router2);*/
        return AjaxResult.getTrueAjaxResult(result);
    }

    @PostMapping(value = "/content")
    public AjaxResult<List<String>> getContent(@RequestBody ContentQuery query) {
        List<String> result = contentService.getContent(query);
        return AjaxResult.getTrueAjaxResult(result);
    }

}
