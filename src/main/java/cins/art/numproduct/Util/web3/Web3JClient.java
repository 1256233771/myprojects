package cins.art.numproduct.Util.web3;


import cins.art.numproduct.Util.RpcRequestUtil;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class Web3JClient {


    private Web3JClient(){}

    private volatile static Web3j web3j;

    public static Web3j getClient(){
        if(web3j==null){
            synchronized (Web3JClient.class){
                if(web3j==null){
                    web3j = Web3j.build(new HttpService(RpcRequestUtil.url));
                }
            }
        }
        return web3j;
    }

}
