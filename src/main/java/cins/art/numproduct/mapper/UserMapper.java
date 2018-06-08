package cins.art.numproduct.mapper;

import cins.art.numproduct.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("select * from user where emailAddress=#{emailAddress}")
    User findUserByEmail(String emailAddress);

    @Insert("insert into user(userAddress,emailAddress,password,role) " +
            "values(#{userAddress},#{emailAddress},#{password},#{role})")
    int add(User user);

    @Select("select * from user where userAddress=#{userAddress}")
    User findByUserAddress(String userAddress);

}
