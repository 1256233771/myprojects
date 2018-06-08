package cins.art.numproduct.controller;

import cins.art.numproduct.DTO.ProductChangeDTO;
import cins.art.numproduct.DTO.ProductForm;
import cins.art.numproduct.Util.ResultVOUtil;
import cins.art.numproduct.Util.convert.Convert;
import cins.art.numproduct.entity.Product;
import cins.art.numproduct.entity.User;
import cins.art.numproduct.enums.ResultEnum;
import cins.art.numproduct.service.CatService;
import cins.art.numproduct.service.ProductService;
import cins.art.numproduct.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/product")
@Slf4j
@CrossOrigin
public class ProductController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CatService catService;

    @PostMapping("/upload")
    @RequiresAuthentication
    @ApiOperation("上传画作接口")
    public Object uploadProduct(@Valid ProductForm productForm,
                                BindingResult bindingResult,
                                Principal principal){
        if(bindingResult.hasErrors()){
            log.error(bindingResult.getFieldError().getDefaultMessage());
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR);
        }
        User user = userService.getCurrentUser(principal);
        return productService.uploadProduct(productForm,user);
    }

    /**
     * 得到当前用户的所有画作
     * @param principal
     * @return
     */
    @ApiOperation("查看当前用户仓库的画作")
    @PostMapping("/getUserProducts")
    @RequiresAuthentication
    public Object getUserProducts(Principal principal){
        User user = userService.getCurrentUser(principal);
        List<Product> productList = productService.findByUser(user);
        return ResultVOUtil.success(productList);
    }

    @ApiOperation("改变商品的状态")
    @PostMapping("/setStatus")
    @RequiresAuthentication
    public Object setProductStatus(@ApiParam(value = "画作唯一标识") String picAddress,
                                   @ApiParam(value = "画作状态:0非卖品，1售卖，2竞标拍卖")
                                           Integer status, Principal principal){
        Product product = productService.findByPicAddress(picAddress);
        if (product==null){
            return ResultVOUtil.error(ResultEnum.PRODUCT_NOT_EXIST);
        }
        User user = userService.getCurrentUser(principal);
        if(!product.getUserAddress().equals(user.getUserAddress())){
            return ResultVOUtil.error(ResultEnum.OVER_PERMISSION);
        }
        product = productService.setStatus(picAddress,status);
        if(product.getProductStatus()!=status){
            return ResultVOUtil.error(ResultEnum.CHANGE_INFO_FAIL);
        }
        return ResultVOUtil.success(product);
    }


    @ApiOperation("通过画作标识查询画作信息")
    @PostMapping("/getPicByPicAddress")
    @RequiresAuthentication
    public Object getPicByPicAddress(String picAddress){
        Product product = productService.findByPicAddress(picAddress);
        if(product==null){
            return ResultVOUtil.error(ResultEnum.PRODUCT_NOT_EXIST);
        }
        return ResultVOUtil.success(product);
    }


    @ApiOperation("更新画作部分信息接口")
    @PostMapping("/updateSomeInfo")
    @RequiresAuthentication
    public Object updateSomeInfo(ProductChangeDTO changeDTO,Principal principal){
        User user = userService.getCurrentUser(principal);
        return productService.updateSomeInfo(changeDTO,user);
    }

    @ApiOperation("添加画作到购物车接口")
    @PostMapping("/addToCat")
    @RequiresAuthentication
    public Object addProductToCat(String picAddress,Principal principal){
        User user = userService.getCurrentUser(principal);
        return catService.addProductToCat(picAddress,user.getUserAddress());
    }

    @ApiOperation("清空购物车所有画作")
    @PostMapping("/flushCatAll")
    @RequiresAuthentication
    public Object flushCat(Principal principal){
        User user = userService.getCurrentUser(principal);
        return catService.flushCat(user.getUserAddress());
    }

    @ApiOperation("从购物车中移除画作")
    @PostMapping("/moveOutFromCat")
    @RequiresAuthentication
    public Object moveOutCat(String[] ids,Principal principal){
        User user = userService.getCurrentUser(principal);
        if (ids.length==0){
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR);
        }
        List<String> catDetailIds = Convert.convertIds(ids);
        return catService.moveOutFromCat(catDetailIds,user);
    }

    @ApiOperation("查看购物车信息接口")
    @PostMapping("/catInfo")
    @RequiresAuthentication
    public Object getCatInfo(Principal principal){
        User user = userService.getCurrentUser(principal);
        return catService.getUserCatInfo(user.getUserAddress());

    }

    @ApiOperation("确认购买接口")
    @PostMapping("/confirmBuy")
    @RequiresAuthentication
    public Object confirmBuy(boolean bool,String[] catDetailIds,Principal principal){
        User user = userService.getCurrentUser(principal);
        //TODO,改变画作拥有者身份
        List catDetailIdList = Convert.convertStrsToList(catDetailIds);
        return catService.confirmBuy(catDetailIdList,user);
    }
}


