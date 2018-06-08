package cins.art.numproduct.service.impl;

import cins.art.numproduct.DTO.ThemeForm;
import cins.art.numproduct.DTO.UploadProperties;
import cins.art.numproduct.Util.MyUtil;
import cins.art.numproduct.Util.ResultVOUtil;
import cins.art.numproduct.Util.convert.Convert;
import cins.art.numproduct.entity.Theme;
import cins.art.numproduct.enums.ResultEnum;
import cins.art.numproduct.mapper.ThemeMapper;
import cins.art.numproduct.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ThemeServiceImpl implements ThemeService{
    @Autowired
    private ThemeMapper themeMapper;
    @Override
    public Object uploadTheme(ThemeForm themeForm) {
        UploadProperties uploadProperties = MyUtil.getUploadPicPath();
        if (!MyUtil.uploadFile(themeForm.getFile(),uploadProperties.getUploadPath())){
            return ResultVOUtil.error(ResultEnum.UPLOAD_THEME_FAIL);
        }
        Theme theme = Convert.convertTheme(themeForm);
        theme.setId(MyUtil.getUniqueKey());
        theme.setPicUrl(uploadProperties.getPictureUrl());
        theme.setUploadTime(new Date());
        int result = themeMapper.add(theme);
        if(result==1){
            return ResultVOUtil.success(theme);
        }
        return ResultVOUtil.error(ResultEnum.ADD_THEME_ERROR);
    }

    @Override
    public List<Theme> findNewThemeByLimit(String endTime, Integer limit) {
        return themeMapper.findByTimeLimit(endTime,limit);
    }

    @Override
    public List<Theme> findThemeByTime(String startTime, String endTime) {
        return themeMapper.findByTime(startTime,endTime);
    }
}
