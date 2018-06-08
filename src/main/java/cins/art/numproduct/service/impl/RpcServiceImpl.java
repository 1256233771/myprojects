package cins.art.numproduct.service.impl;

import cins.art.numproduct.Util.RpcRequestUtil;
import cins.art.numproduct.service.RpcService;
import org.springframework.stereotype.Service;

@Service
public class RpcServiceImpl implements RpcService {
    @Override
    public Object createUser(String password) throws Throwable {
        String address = RpcRequestUtil.newAccount(password).toString();
        RpcRequestUtil.unlockAccount(RpcRequestUtil.mainAddress,RpcRequestUtil.mainPassword);
        RpcRequestUtil.sendTransaction(RpcRequestUtil.mainAddress,address,"0xff");//TODO 主账户的设置
        return address;
    }
}
