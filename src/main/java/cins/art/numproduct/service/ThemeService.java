package cins.art.numproduct.service;

import cins.art.numproduct.DTO.ThemeForm;
import cins.art.numproduct.entity.Theme;

import java.util.List;

public interface ThemeService {
    public Object uploadTheme(ThemeForm themeForm);
    public List<Theme> findNewThemeByLimit(String endTime,Integer limit);
    public List<Theme> findThemeByTime(String startTime,String endTime);
}
