package cins.art.numproduct.service.impl;

import cins.art.numproduct.Util.RpcRequestUtil;
import cins.art.numproduct.service.RpcService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.RawTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.Future;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class RpcServiceImplTest {

    @Autowired
    private RpcService rpcService;
    @Test
    public void test() {
//        Web3j web3j = Web3j.build(new HttpService("http://119.23.233.57:8082"));
//        try {
//            String v = web3j.web3ClientVersion().send().getWeb3ClientVersion();
//            log.info("version:{}",v);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try {
            RpcRequestUtil.miner_start();
            RpcRequestUtil.miner_stop();
            RpcRequestUtil.unlockAccount(RpcRequestUtil.mainAddress,RpcRequestUtil.mainPassword);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            log.error("error");
        }
//        try {
//            String address = (String) RpcRequestUtil.newAccount("123456");
//            log.info("address:"+address);
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
    }

    @Test
    public void unlock(){
        try {
            Object re = RpcRequestUtil.unlockAccount(RpcRequestUtil.mainAddress,RpcRequestUtil.mainPassword);
            log.info("{}",re);
        } catch (Throwable throwable) {
            log.error("error");
            throwable.printStackTrace();
        }
    }

}