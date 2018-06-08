package cins.art.numproduct.Util.convert;

import cins.art.numproduct.DTO.CatProduct;
import cins.art.numproduct.DTO.ProductForm;
import cins.art.numproduct.DTO.ThemeForm;
import cins.art.numproduct.DTO.UserRegisterForm;
import cins.art.numproduct.entity.Product;
import cins.art.numproduct.entity.Theme;
import cins.art.numproduct.entity.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Convert {


    //将表单的用户信息转换为用户类
    public static User convertUser(UserRegisterForm form){
        User user = new User();
        user.setEmailAddress(form.getEmailAddress());
        user.setPassword(form.getPassword());
        return user;
    }
    public static Product convertCatProduct(ProductForm productForm){
        Product product = new Product();
        //只有userAddress,保存文件的url未设置
        product.setPicAddress(productForm.getPicAddress());
        product.setName(productForm.getName());
        product.setCreteTime(productForm.getCreateTime());
        product.setUploadTime(new Date());
        product.setWidth(productForm.getWidth());
        product.setHeight(productForm.getHeight());
        product.setPrice(BigDecimal.valueOf(productForm.getPrice()));
        product.setAuthor(productForm.getAuthor());
        product.setCategory(productForm.getCategory());
        product.setStyle(productForm.getStyle());
        product.setTheme(productForm.getTheme());
        product.setProductStatus(productForm.getProductStatus());
        product.setDescription(productForm.getDescription());
        return product;
    }

    public static CatProduct convertCatProduct(Product product){
        CatProduct catProduct = new CatProduct();
        catProduct.setName(product.getName());
        catProduct.setPicAddress(product.getPicAddress());
        catProduct.setUserAddress(product.getUserAddress());
        catProduct.setPicUrl(product.getPictureUrl());
        catProduct.setAuthor(product.getAuthor());
        catProduct.setHeight(product.getHeight());
        catProduct.setWidth(product.getWidth());
        catProduct.setPrice(product.getPrice());
        return catProduct;
    }
    public static Theme convertTheme(ThemeForm themeForm){
        Theme theme = new Theme();
        theme.setTitle(themeForm.getTitle());
        theme.setDescription(themeForm.getDescription());
        return theme;
    }

    public static List<String> convertIds(String[] ids){
        List<String> idList = new ArrayList<>();
        for (String id:ids){
            idList.add(id);
        }
        return idList;
    }

    public static List convertStrsToList(String[] Strs){
        List list = new ArrayList();
        for (String s : Strs){
            list.add(s);
        }
        return list;
    }
}
