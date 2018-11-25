package cins.art.numproduct.service;

import cins.art.numproduct.entity.ProductBid;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

public interface BidService {
    ProductBid addBid(ProductBid productBid);
    List<ProductBid> findBidByUser(Principal principal);
    ProductBid findPicHighestBid(String picAddress);
    ProductBid findBidByUserAndPic(String userAddress,String picAddress);
    ProductBid updateBid(String picAddress,String userAddress,BigDecimal price);
    List<ProductBid> findPicAllBidInfo(String picAddress);
    Object bidProduct(String picAddress, BigDecimal price,Principal principal);
    Object ownerAgreeBid(String picAddress,Principal principal);
    Object ownerCancelBid(String picAddress,Principal principal);
    Object ownerIntoBid(String picAddress,Principal principal);

}
