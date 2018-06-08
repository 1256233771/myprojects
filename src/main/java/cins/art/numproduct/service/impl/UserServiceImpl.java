package cins.art.numproduct.service.impl;

import cins.art.numproduct.entity.User;
import cins.art.numproduct.enums.ResultEnum;
import cins.art.numproduct.exception.MyException;
import cins.art.numproduct.mapper.UserMapper;
import cins.art.numproduct.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Slf4j
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByUserAddress(String userAddress) {
        return userMapper.findByUserAddress(userAddress);
    }

    @Override
    @Cacheable(value = "user",key = "#root.targetClass+#emailAddress",unless = "#result eq null")
    public User findUserByEmail(String emailAddress) {
        return userMapper.findUserByEmail(emailAddress);
    }

    @Override
    @CachePut(value = "user", key = "#root.targetClass + #user.emailAddress", unless = "#result eq null")
    public Object addUser(User user) {
        if (userMapper.findUserByEmail(user.getEmailAddress())!=null){
            throw new MyException(ResultEnum.EMAIL_REGISTED);
        }
        log.info("加密前:{}",user.getPassword());
        Md5Hash md5Hash = new Md5Hash(user.getPassword(),user.getEmailAddress());
        user.setPassword(md5Hash.toString());
        log.info("加密后:{}",user.getPassword());
        int result = userMapper.add(user);
        log.info("result:{}",result);
        if (result!=1){
            throw new MyException(ResultEnum.ADD_USER_FAIL);
        }
        return user;
    }

    @Override
    public User getCurrentUser(Principal principal) {
        String emailAddress = principal.getName();
        return findUserByEmail(emailAddress);
    }

    //判断当前用户与传入的userAddress是否对应身份
    @Override
    public boolean checkPrincipal(Principal principal,String userAddress) {
        User user = getCurrentUser(principal);
        if (user.getUserAddress().equals(userAddress)){
            return true;
        }
        return false;
    }

}
