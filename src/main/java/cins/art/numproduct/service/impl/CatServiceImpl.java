package cins.art.numproduct.service.impl;

import cins.art.numproduct.DTO.CatProduct;
import cins.art.numproduct.Util.MyUtil;
import cins.art.numproduct.Util.ResultVOUtil;
import cins.art.numproduct.Util.convert.Convert;
import cins.art.numproduct.ViewObject.CatVO;
import cins.art.numproduct.ViewObject.ResultVO;
import cins.art.numproduct.entity.CatDetail;
import cins.art.numproduct.entity.Product;
import cins.art.numproduct.entity.User;
import cins.art.numproduct.enums.ResultEnum;
import cins.art.numproduct.mapper.CatDetailMapper;
import cins.art.numproduct.service.CatService;
import cins.art.numproduct.service.ProductService;
import cins.art.numproduct.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CatServiceImpl implements CatService {

    @Autowired
    private ProductService productService;

    @Autowired
    private CatDetailMapper catDetailMapper;

    @Autowired
    private UserService userService;
    @Override
    public Object flushCat(String userAddress) {
        int result = catDetailMapper.deleteByUserAddress(userAddress);
        if (result==0){
            return ResultVOUtil.error(ResultEnum.FLUSH_CAT_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public Object addProductToCat(String picAddress,String userAddress) {
        CatDetail mapperCatDetail = catDetailMapper.findByUserAddressAndPicAddress(userAddress,picAddress);
        //判断商品是否已加入购物车
        log.info("判断当前是否已经加入购物车..");
        if (mapperCatDetail!=null){
            log.error("商品已经存在购物车");
            return ResultVOUtil.error(ResultEnum.PRODUCT_HAD_IN_CAT);
        }
        Product product = productService.findByPicAddress(picAddress);
        log.info("判断当前作品是否已经拥有..");
        if (product.getUserAddress().equals(userAddress)){
            return ResultVOUtil.error(ResultEnum.CAN_NOT_ADD_TO_CAT);
        }
        CatDetail catDetail = new CatDetail();
        catDetail.setCatDetailId(MyUtil.getUniqueKey());
        catDetail.setPicAddress(picAddress);
        catDetail.setUserAddress(userAddress);
        int result = catDetailMapper.addCatDetail(catDetail);
        if (result==0){
            ResultVOUtil.error(ResultEnum.ADD_PRODUCT_TO_CAT_ERROR);
        }
        return ResultVOUtil.success();
    }

    @Override
    public Object getUserCatInfo(String userAddress) {
        List<CatDetail> catDetailList= catDetailMapper.findUserCatDetail(userAddress);
        if (catDetailList==null){
            return ResultVOUtil.error(ResultEnum.USER_CAT_IS_NULL);
        }
        List<CatProduct> catProductList = new ArrayList<>();
        //得到购物车的画作的信息
        for (CatDetail catDetail:catDetailList){
            Product product = productService.findByPicAddress(catDetail.getPicAddress());
            catDetail.getCatDetailId();
            CatProduct catProduct = Convert.convertCatProduct(product);
            catProduct.setCatDetailId(catDetail.getCatDetailId());
            catProductList.add(catProduct);
        }
        log.info("得到购物车catProductList"+catProductList.toString());
        User user = userService.findUserByUserAddress(userAddress);
        log.info("获得User"+user.toString());
        CatVO catVO = new CatVO();
        catVO.setUserAddress(userAddress);
        catVO.setEmailAddress(user.getEmailAddress());
        catVO.setCatProductList(catProductList);
        log.info("获得catVO:"+catVO.toString());
        return ResultVOUtil.success(catVO);
    }


    //多个商品的交易
    @Override
    @Transactional
    public Object confirmBuy(List<String> catDetailIds,User user) {
        for (String id:catDetailIds){
            CatDetail catDetail = catDetailMapper.findByCatDetailId(id);
            if (catDetail==null){
                log.error("未找到对应的catDetailId");
                return new ResultVO<String>(404,"未找到对应的catDetailId",id);
            }
            //判断当前的购物车信息是否是当前用户的信息
            if (!user.getUserAddress().equals(catDetail.getUserAddress())){
                log.error("越权操作");
                return ResultVOUtil.error(ResultEnum.OVER_PERMISSION);
            }
            //判断当前添加的商品是否为售卖品状态
            if (productService.findByPicAddress(catDetail.getPicAddress()).getProductStatus()!=1){
                log.error("商品是非卖品");
                ResultVO resultVO = new ResultVO();
                resultVO.setCode(50);
                Product product = productService.findByPicAddress(catDetail.getPicAddress());
                resultVO.setMsg("商品"+product.getName()+"非卖品!");
                resultVO.setData(product);
                return ResultVOUtil.error(resultVO);
            }
            //判断是否交易成功
            if (!productService.changePicOwner(catDetail.getPicAddress(),user)){
                return ResultVOUtil.error(ResultEnum.PRODUCT_DEAL_ERROR);
            }

            //从购物车移除
            List list = new ArrayList();
            list.add(catDetail.getCatDetailId());
            if(moveOutFromCat(list,user).getCode()!=1){
                return ResultVOUtil.error(ResultEnum.MOVE_OUT_CAT_ERROR);
            }
        }
        return ResultVOUtil.success();
    }

    @Override
    @Transactional
    public ResultVO moveOutFromCat(List<String> catDetailIds,User user) {
        for (String id:catDetailIds){
            CatDetail catDetail = catDetailMapper.findByCatDetailId(id);
            if (catDetail==null){
                return ResultVOUtil.error(ResultEnum.CAT_DETAIL_NOT_EXIST);
            }
            if (!user.getUserAddress().equals(catDetail.getUserAddress())){
                return ResultVOUtil.error(ResultEnum.OVER_PERMISSION);
            }
            int result = catDetailMapper.deleteById(id);
            log.info("删除catDetailId:"+result);
            if (result!=1){
                return ResultVOUtil.error(ResultEnum.MOVE_OUT_CAT_ERROR);
            }
        }
        return ResultVOUtil.success();
    }
}
