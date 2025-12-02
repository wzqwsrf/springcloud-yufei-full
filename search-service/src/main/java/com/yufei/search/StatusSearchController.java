package com.yufei.search;

import com.yufei.search.dto.StatusQueryDto;
import com.yufei.search.service.IStatusSearch;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
/**
 * @author wangzhenqing
 * @date 2025/12/02 11:43
 * @description
 */
@RestController
@RequestMapping("/status")
public class StatusSearchController {
    @Resource
    private IStatusSearch statusSearch;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public Object statusSearch(@Validated @RequestBody StatusQueryDto queryDto) {
        return statusSearch.search(queryDto);
    }
}
