package com.yufei.status.controller;

import com.yufei.status.common.Result;
import com.yufei.status.domain.StatusDto;
import com.yufei.status.request.StatusCreateRequest;
import com.yufei.status.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangzhenqing
 * @date 2025/12/01 12:44
 * @description
 */
@RestController
@RequestMapping("/status")
@RequiredArgsConstructor
public class StatusController {
    private final StatusService statusService;

    @PostMapping("/create")
    public Result<Long> create(@RequestBody StatusCreateRequest request) {
        return Result.success(statusService.createStatus(request));
    }

    @GetMapping("/{id}")
    public Result<StatusDto> get(@PathVariable Long id) {
        return Result.success(statusService.getStatus(id));
    }

    @PostMapping("/{id}/like")
    public Result<?> like(@PathVariable Long id, @RequestParam Long userId) {
        statusService.likeStatus(id, userId);
        return Result.success(null);
    }
}
