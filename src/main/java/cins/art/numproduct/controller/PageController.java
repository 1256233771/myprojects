package cins.art.numproduct.controller;

import cins.art.numproduct.Util.ResultVOUtil;
import cins.art.numproduct.Util.time.DateTimeUtil;
import cins.art.numproduct.ViewObject.HomePictureVO;
import cins.art.numproduct.entity.Product;
import cins.art.numproduct.entity.Theme;
import cins.art.numproduct.enums.ResultEnum;
import cins.art.numproduct.service.ProductService;
import cins.art.numproduct.service.ThemeService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/page")
@Slf4j
@CrossOrigin
public class PageController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ThemeService themeService;

    /**
     * 得到主页面信息
     * @return
     */
    @ApiOperation("主页面接口")
    @PostMapping("/home")
    public Object getHomePage(){
        String dateStr = DateTimeUtil.convertDateToString(new Date());
        String category = "油画";//默认油画
        List<Product> oilPaintings = productService.findByCategoryTimeLimit(category,dateStr,4);
        List<Product> newProducts = productService.findByCategoryTimeLimit(null,dateStr,10);
        List<Theme> themes = themeService.findNewThemeByLimit(dateStr,2);
        HomePictureVO homePictureVO = new HomePictureVO();
        homePictureVO.setOilPaintings(oilPaintings);
        homePictureVO.setNewProducts(newProducts);
        homePictureVO.setThemes(themes);
        return ResultVOUtil.success(homePictureVO);
    }

    /**
     * 得到分类主页面
     * @param category
     * @return
     */
    @ApiOperation("主界面查询分类接口")
    @PostMapping("/getHomeCatPic")
    public Object getHomeCategoryPic(String category){
        log.info("getHomeCatPic...,category:{}",category);
        String dateStr = DateTimeUtil.convertDateToString(new Date());

        List<Product> productList = productService.findByCategoryTimeLimit(category,dateStr,4);
        log.info("productList:{}",productList.toString());
        return ResultVOUtil.success(productList);
    }

    //换一换模块
    @ApiOperation("刷新接口")
    @PostMapping("/flush")
    public Object flushOne(){
        List<Product> productList = productService.getRandomProducts(10);
        if (productList==null||productList.size()<10){
            return ResultVOUtil.error(ResultEnum.DB_NOT_ENOUGH);
        }
        return ResultVOUtil.success(productList);
    }

    //用户添加参数进行条件筛选作品
    @ApiOperation("多条件筛选接口")
    @PostMapping("/selectPic")
    public Object selectPic(Integer productStatus, String minPrice,
                            String maxPrice, Integer minWidth,
                            Integer maxWidth, String author,
                            String category, String style,
                            String theme, Integer pageNum,
                            Integer orderRule){
        List<Product> productList = productService.findBySelections(productStatus,minPrice,
                maxPrice,minWidth,maxWidth,author,category,style,theme,pageNum,orderRule);
        if(productList==null){
            return ResultVOUtil.error(ResultEnum.NO_SUCH_PRODUCT);
        }
        return ResultVOUtil.success(productList);
    }

    @ApiOperation("查询画作详细信息接口")
    @PostMapping("/getPicInfo")
    public Object getPicInfo(String picAddress){
        if (picAddress==null){
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR);
        }
        Product product = productService.findByPicAddress(picAddress);
        if (product==null){
            return ResultVOUtil.error(ResultEnum.PRODUCT_NOT_EXIST);
        }
        return ResultVOUtil.success(product);
    }

    /**
     * 输入作者名或作品名称模糊查找
     * @param info
     * @return
     */
    @ApiOperation("根据作者和画作名进行筛选画作")
    @PostMapping("/selectRandom")
    public Object selectByPutInfo(String info){
        if (info==null||"".equals(info)){
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR);
        }
        List<Product> productList = productService.findByCompareStr(info);
        if (productList==null){
            return ResultVOUtil.error(ResultEnum.NO_SUCH_PRODUCT);
        }
        return ResultVOUtil.success(productList);
    }

    /**
     * 根据拍卖区显示作品
     * @param productStatus
     * @param orderRule
     * @return
     */
    @ApiOperation("显示拍卖区的作品信息")
    @PostMapping("/getByStatus")
    public Object getProductByStatus(Integer productStatus,Integer orderRule){
        List<Product> productList = productService.findBySelections(productStatus,"0",
                String.valueOf(System.currentTimeMillis()),
                0,Integer.MAX_VALUE,null,null,
                null,null,1,1);
        if (productList==null){
            return ResultVOUtil.error(ResultEnum.NO_SUCH_PRODUCT);
        }
        return ResultVOUtil.success(productList);
    }


}
