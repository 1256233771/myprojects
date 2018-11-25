package cins.art.numproduct.service.impl;

import cins.art.numproduct.DTO.ProductChangeDTO;
import cins.art.numproduct.DTO.ProductForm;
import cins.art.numproduct.DTO.UploadProperties;
import cins.art.numproduct.Util.MyUtil;
import cins.art.numproduct.Util.ResultVOUtil;
import cins.art.numproduct.Util.convert.Convert;
import cins.art.numproduct.entity.Product;
import cins.art.numproduct.entity.User;
import cins.art.numproduct.enums.ResultEnum;
import cins.art.numproduct.mapper.ProductMapper;
import cins.art.numproduct.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductMapper productMapper;


    @Override
    @CachePut(value = "product",key = "#root.targetClass+#product.picAddress",unless = "#result eq null")
    public Integer addProduct(Product product) {
//        product.setUserAddress();
        int result = productMapper.addProduct(product);
        log.info("result:{}",result);
        return result;
    }

    @Override
    @Cacheable(value = "product",key = "#root.targetClass+#picAddress",unless = "#result eq null")
    public Product findByPicAddress(String picAddress) {
//        String key = (String) SecurityUtils.getSubject().getPrincipal()+"_address_pics";
//        log.info("当前用户:"+key);
//        if (redisService.haskey(key)){
//            return redisService.getKey(key);
//        }
        log.info("根据picAddress查找画作..");
        Product product = productMapper.findByPicAddress(picAddress);
        return product;
    }

    @Override
    public List<Product> findByStyle(String style) {
        List<Product> productList = productMapper.findByStyle(style);
        return productList;
    }

    @Override
//    @Cacheable(value = "products", key = "#root.targetClass + #user.userAddress", unless = "#result eq null")
    public List<Product> findByUser(User user) {
        String address = user.getUserAddress();
        return productMapper.findByUserAddress(address);
    }

    @Override
    public Object uploadProduct(ProductForm productForm, User user) {
        if (findByPicAddress(productForm.getPicAddress())!=null){
            return ResultVOUtil.error(ResultEnum.KEY_REPEAT);
        }
        UploadProperties uploadProperties = MyUtil.getUploadPicPath();//指定上传的路径
        if (!MyUtil.uploadFile(productForm.getPicFile(),uploadProperties.getUploadPath())){
            return ResultVOUtil.error(ResultEnum.PIC_UPLOAD_FILE_ERROR);
        }
        String videoPath = null;
//        if (productForm.getVideoFile()!=null){
//            videoPath = MyUtil.getUploadVideoPath(productForm.getVideoFile());//*
//            if (!MyUtil.uploadFile(productForm.getVideoFile(),videoPath)){
//                return ResultVOUtil.error(ResultEnum.VIDEO_UPLOAD_FILE_ERROR);
//            }
//        }
        Product product = Convert.convertCatProduct(productForm);
        product.setUserAddress(user.getUserAddress());
        product.setPictureUrl(uploadProperties.getPictureUrl());
        product.setVideoUrl(videoPath);


        int result = addProduct(product);
        if (result!=1){
            log.error("上传失败..");
            return ResultVOUtil.error(ResultEnum.UPLOAD_FAIL);
        }
        log.info("上传作品成功..");
        return ResultVOUtil.success();
    }

    @Override
    public List<Product> findAllByCategory(String category) {
        return productMapper.findByCategory(category);
    }

    @Override
    public List<Product> findByCategoryTimeLimit(String category, String endTime, Integer limit) {
        if (category==null){
            category="%%";
        }
        return productMapper.findByCategoryTimeLimit(category,endTime,limit);
    }

    @Override
    public List<Product> findBySelections(Integer productStatus,String minPrice,
                                          String maxPrice,Integer minWidth,
                                          Integer maxWidth, String author,
                                          String category, String style,
                                          String theme,Integer pageNum,
                                          Integer orderRule) {//orderRule:0:asc,1:desc
        //默认进行直买品的筛选
        if (productStatus==null){
            productStatus=1;
        }
        //默认排序方式是价格的升序
        if (orderRule==null){
            orderRule=0;
        }
        if (author==null){
            author = "%%";
        }else {
            author = "%"+author+"%";
        }
        if (category==null){
            category = "%%";
        }else {
            category = "%"+category+"%";
        }
        if (style==null||"".equals(style)){
            style = "%%";
        }else {
            style = "%"+style+"%";
        }
        if (theme==null||"".equals(theme)){
            theme = "%%";
        }else {
            theme = "%"+theme+"%";
        }
        Integer startNum = (pageNum-1)*21;
        if (orderRule==0){
            return productMapper.findSelectionsByPriceAsc(productStatus,minPrice,maxPrice,
                    minWidth,maxWidth,author,category,style,theme,startNum,21);
        }
        //orderRule等于1时降序
        return productMapper.findSelectionsByPriceDesc(productStatus,minPrice,maxPrice,
                minWidth,maxWidth,author,category,style,theme,startNum,21);
    }

    @Override
    public List<Product> findByCompareStr(String compareStr){
        compareStr = "%"+compareStr+"%";
        return productMapper.findByCompareStr(compareStr);
    }

    @Override
    @CachePut(value = "product",key = "#root.targetClass+#picAddress",unless = "#result eq null")
    public Product setStatus(String picAddress,Integer status) {
        productMapper.updateProductStatus(picAddress,status);
        return productMapper.findByPicAddress(picAddress);
    }

    @Override
    public List<Product> getRandomProducts(Integer limit) {
        return productMapper.findByRandomNum(limit);
    }


    @Override
    public Object updateSomeInfo(ProductChangeDTO changeDTO, User user) {
        if (user.getUserAddress().equals(findByPicAddress(changeDTO.getPicAddress()).getUserAddress())){
            int re = productMapper.updateProductSomeInfo(changeDTO);
            if (re==1){
                return ResultVOUtil.success();
            }
        }
        return ResultVOUtil.error(ResultEnum.OVER_PERMISSION);
    }

    @Override
    public boolean changePicOwner(String picAddress, User user) {
        int re = productMapper.updateProductOwner(user.getUserAddress(),picAddress);
        if (re==1){
            productMapper.updateProductStatus(picAddress,0);
            return true;
        }
        return false;
    }

    @Override
    public boolean checkOwner(String picAddress, String userAddress) {
        if (findByPicAddress(picAddress).getUserAddress().equals(userAddress)){
            return true;
        }
        return false;
    }


}
