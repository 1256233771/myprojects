package cins.art.numproduct.service.impl;

import cins.art.numproduct.Util.ResultVOUtil;
import cins.art.numproduct.entity.Product;
import cins.art.numproduct.entity.ProductBid;
import cins.art.numproduct.entity.User;
import cins.art.numproduct.enums.ResultEnum;
import cins.art.numproduct.mapper.ProductBidMapper;
import cins.art.numproduct.service.BidService;
import cins.art.numproduct.service.ProductService;
import cins.art.numproduct.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Service
public class BidServiceImpl implements BidService {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductBidMapper productBidMapper;
    @Autowired
    private UserService userService;

    @Override
    public ProductBid addBid(ProductBid productBid) {
        if (productBidMapper.addProductBid(productBid)==1){
            return productBid;
        }
        return null;
    }

    //查询当前用户的所有竞标出价信息
    @Override
    public List<ProductBid> findBidByUser(Principal principal) {
        User user = userService.getCurrentUser(principal);
        return productBidMapper.getByUserAddress(user.getUserAddress());
    }

    //查询当前商品竞标的最高价
    @Override
    public ProductBid findPicHighestBid(String picAddress) {
        return productBidMapper.getHighestByPicAddress(picAddress);
    }

    @Override
    public ProductBid findBidByUserAndPic(String userAddress, String picAddress) {
        return productBidMapper.getByUserAddressAndPicAddress(userAddress,picAddress);
    }

    //更改出价
    @Override
    public ProductBid updateBid(String picAddress,String userAddress,BigDecimal price) {
        User user = userService.findUserByUserAddress(userAddress);
        productBidMapper.updateProductBidPrice(price,user.getUserAddress(),picAddress);
        return findBidByUserAndPic(user.getUserAddress(),picAddress);
    }


    //查询商品所有竞标出价信息
    @Override
    public List<ProductBid> findPicAllBidInfo(String picAddress) {
        return productBidMapper.getByPicAddress(picAddress);
    }

    //客户竞标某件商品
    @Override
    public Object bidProduct(String picAddress, BigDecimal price,Principal principal) {
        User user = userService.getCurrentUser(principal);
        Product product = productService.findByPicAddress(picAddress);
        if (product==null){
            return ResultVOUtil.error(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (product.getProductStatus()!=2){
            ResultVOUtil.error(ResultEnum.NOT_BID_PRODUCT);
        }
        ProductBid productBid = findBidByUserAndPic(user.getUserAddress(),picAddress);
        if (productBid==null){
            productBid = new ProductBid();
        }else {
            return ResultVOUtil.success(updateBid(picAddress,user.getUserAddress(),price));
        }
        productBid.setPicAddress(picAddress);
        productBid.setPrice(price);
        productBid.setUserAddress(user.getUserAddress());
        if (addBid(productBid)!=null){
            return ResultVOUtil.success(productBid);
        }
        return ResultVOUtil.error(ResultEnum.ADD_BID_FAIL);
    }

    //拥有者同意当前最高出价
    @Override
    public Object ownerAgreeBid(String picAddress,Principal principal) {
        User user = userService.getCurrentUser(principal);
        Product product = productService.findByPicAddress(picAddress);
        if (product==null){
            return ResultVOUtil.error(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (!product.getUserAddress().equals(user.getUserAddress())){
            return ResultVOUtil.error(ResultEnum.OVER_PERMISSION);
        }
        ProductBid productBid = findPicHighestBid(picAddress);
        User customer = userService.findUserByUserAddress(productBid.getUserAddress());
        if (productService.changePicOwner(picAddress,customer)){
            productService.setStatus(picAddress,0);
            return ResultVOUtil.success();
        }
        return ResultVOUtil.error(ResultEnum.PRODUCT_DEAL_ERROR);
    }

    //拥有者取消竞标操作
    @Override
    public Object ownerCancelBid(String picAddress,Principal principal) {
        User user = userService.getCurrentUser(principal);
        if (!productService.checkOwner(picAddress,user.getUserAddress())){
            return ResultVOUtil.error(ResultEnum.OVER_PERMISSION);
        }
        productService.setStatus(picAddress,0);
        return ResultVOUtil.success();
    }

    //拥有者将某件商品进行竞标
    @Override
    public Object ownerIntoBid(String picAddress,Principal principal) {
        User user = userService.getCurrentUser(principal);
        if (!productService.checkOwner(picAddress,user.getUserAddress())){
            return ResultVOUtil.error(ResultEnum.OVER_PERMISSION);
        }
        productService.setStatus(picAddress,2);
        return ResultVOUtil.success();
    }
}
