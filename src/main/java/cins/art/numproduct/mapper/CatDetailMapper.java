package cins.art.numproduct.mapper;

import cins.art.numproduct.entity.CatDetail;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CatDetailMapper {
    @Insert("insert into catDetail(catDetailId,userAddress,picAddress) " +
            "values(#{catDetailId},#{userAddress},#{picAddress})")
    int addCatDetail(CatDetail catDetail);

    @Select("select * from catDetail where userAddress=#{userAddress}")
    List<CatDetail> findUserCatDetail(String userAddress);

    @Select("select * from catDetail where userAddress=#{param1} and picAddress=#{param2}")
    CatDetail findByUserAddressAndPicAddress(String userAddress,String picAddress);

    @Select("select * from catDetail where catDetailId=#{catDetailId}")
    CatDetail findByCatDetailId(String catDetailId);

    @Delete("delete from catDetail where catDetailId=#{catDetailId}")
    int deleteById(String catDetailId);

    @Delete("delete from catDetail where userAddress=#{userAddress}")
    int deleteByUserAddress(String userAddress);

}
