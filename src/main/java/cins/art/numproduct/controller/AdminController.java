package cins.art.numproduct.controller;

import cins.art.numproduct.DTO.ThemeForm;
import cins.art.numproduct.Util.ResultVOUtil;
import cins.art.numproduct.annoation.RequireAdminPermission;
import cins.art.numproduct.enums.ResultEnum;
import cins.art.numproduct.service.ThemeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/admin")
@RestController
@Slf4j
@CrossOrigin
public class AdminController {

    @Autowired
    private ThemeService themeService;

    @ApiOperation("上传主题信息..")//swagger的接口备注
    @PostMapping("/uploadTheme")
    @RequireAdminPermission
    public Object uploadTheme(@Valid ThemeForm themeForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error(bindingResult.getFieldError().getDefaultMessage());
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR);
        }
        return themeService.uploadTheme(themeForm);
    }



}

