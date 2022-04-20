package com.lunar.controller.admin;

import com.lunar.domain.ResponseResult;
import com.lunar.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class SystemController {

    @Autowired
    private SystemService systemService;

    @PutMapping("/refresh")
    public ResponseResult refreshSystem() {
        return systemService.refreshSystem();
    }
}
