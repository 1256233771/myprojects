package cins.art.numproduct.mapper;

import cins.art.numproduct.DTO.ProductChangeDTO;
import cins.art.numproduct.entity.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ProductMapper {
    @Insert("insert into product(picAddress,userAddress,name,pictureUrl," +
            "videoUrl,creteTime,uploadTime,width,height,price,author" +
            ",category,style,theme,productStatus,description) " +
            "values(#{picAddress},#{userAddress},#{name},#{pictureUrl},#{videoUrl},#{creteTime},#{uploadTime}," +
            "#{width},#{height},#{price},#{author},#{category},#{style},#{theme},#{productStatus},#{description})")
    int addProduct(Product product);
    @Update("update product set productStatus=#{param2} where picAddress=#{picAddress}")
    int updateProductStatus(String picAddress, Integer status);
    @Update("update product set userAddress=#{param1} where picAddress=#{param2}")
    int updateProductOwner(String userAddress,String picAddress);
    @Update("update product set name=#{name},pictureUrl=#{pictureUrl}," +
            "videoUrl=#{videoUrl},creteTime=#{creteTime},uploadTime=#{uploadTime}," +
            "width=#{width},height=#{height},price=#{price},author=#{author}," +
            "category=#{category},style=#{style},theme=#{theme},productStatus=#{productStatus}," +
            "description=#{description} where picAddress=#{picAddress}")
    int updateProductInfo(Product product);
    @Update("update product set name=#{name},author=#{author},category=#{category},style=#{style}," +
            "price=#{price},productStatus=#{productStatus} where picAddress=#{picAddress}")
    int updateProductSomeInfo(ProductChangeDTO changeDTO);
    @Select("select * from product where picAddress=#{picAddress}")
    Product findByPicAddress(String picAddress);
    @Select("select * from product where category=#{category}")
    List<Product> findByCategory(String category);
    @Select("select * from product where style=#{style}")
    List<Product> findByStyle(String style);
    @Select("select * from product where theme=#{theme}")
    List<Product> findByTheme(String theme);
    @Select("select * from product where userAddress=#{address}")
    List<Product> findByUserAddress(String address);
    @Select("select * from product where uploadTime>#{param1} and uploadTime<#{param2}")
    List<Product> findByUploadTime(String startTime,String endTime);
    @Select("select * from product where category like #{param1} and uploadTime<#{param2} limit #{param3}")
    List<Product> findByCategoryTimeLimit(String category,String endTime,Integer limit);
    //多条件查询
    @Select("select * from product where productStatus=#{param1} and price>#{param2} and " +
            "price<#{param3} and width>#{param4} and width<#{param5} and height>#{param4} " +
            "and height<#{param5} and author like #{param6} and category " +
            "like #{param7} and style like #{param8} and " +
            "theme like #{param9} order by price desc limit #{param10},#{param11}")
    List<Product> findSelectionsByPriceDesc(Integer productStatus, String minPrice, String maxPrice,
                                            Integer minWidth, Integer maxWidth,
                                            String author, String category, String style,
                                            String theme, Integer startNum, Integer selectNum);
    @Select("select * from product where productStatus=#{param1} and price>#{param2} and " +
            "price<#{param3} and width>#{param4} and width<#{param5} and height>#{param4} " +
            "and height<#{param5} and author like #{param6} and category " +
            "like #{param7} and style like #{param8} and " +
            "theme like #{param9} order by price asc limit #{param10},#{param11}")
    List<Product> findSelectionsByPriceAsc(Integer productStatus, String minPrice, String maxPrice,
                                            Integer minWidth, Integer maxWidth,
                                            String author, String category, String style,
                                            String theme, Integer startNum, Integer selectNum);
    @Select("select * from product where author like #{param1} or name like #{param1}")
    List<Product> findByCompareStr(String compareStr);

    @Select("select * from product order by rand() limit #{limit}")
    List<Product> findByRandomNum(Integer limit);
}
