package cins.art.numproduct.mapper;

import cins.art.numproduct.entity.ProductBid;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;

public interface ProductBidMapper {
    @Delete("delete from productBid where picAddress=#{picAddress}")
    int deleteBidById(String picAddress);
    @Select("select * from productBid where userAddress=#{userAddress}")
    List<ProductBid> getByUserAddress(String userAddress);
    @Select("select * from productBid where picAddress=#{picAddress} order by price desc")
    List<ProductBid> getByPicAddress(String picAddress);
    @Select("select * from productBid where picAddress=#{picAddress} order by price desc limit 1")
    ProductBid getHighestByPicAddress(String picAddress);
    @Select("select * from productBid where userAddress=#{param1} and picAddress=#{param2}")
    ProductBid getByUserAddressAndPicAddress(String userAddress,String picAddress);
    @Insert("insert into productBid(picAddress,userAddress,price) " +
            "values(#{picAddress},#{userAddress},#{price})")
    int addProductBid(ProductBid productBid);
    @Update("update productBid set price=#{param1} where userAddress=#{param2} and picAddress=#{param3}")
    int updateProductBidPrice(BigDecimal price,String userAddress,String picAddress);
}
