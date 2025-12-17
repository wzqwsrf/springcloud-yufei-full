package com.yufei.search.controller;

import com.yufei.search.dto.StatusQueryDto;
import com.yufei.search.service.IStatusSearchService;
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
    private IStatusSearchService statusSearch;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public Object statusSearch(@Validated @RequestBody StatusQueryDto queryDto) {
        return statusSearch.search(queryDto);
    }
}
