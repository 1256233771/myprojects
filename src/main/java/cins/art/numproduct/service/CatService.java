package cins.art.numproduct.service;

import cins.art.numproduct.entity.User;

import java.util.List;

public interface CatService {
    public Object flushCat(String userAddress);
    public Object addProductToCat(String picAddress,String userAddress);
    public Object getUserCatInfo(String userAddress);
    public Object confirmBuy(List<String> catDetailIds, User user);//确认购买
    public Object moveOutFromCat(List<String> catDetailIds,User user);//从购物车移除
}
