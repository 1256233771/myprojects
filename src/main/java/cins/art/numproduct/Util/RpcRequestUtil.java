package cins.art.numproduct.Util;

import cins.art.numproduct.DTO.jsonData.JsonParams;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;

/**
 * 对接区块链方法请求的工具类
 */
@Slf4j
public class RpcRequestUtil {
    public static String url = "http://119.23.233.57:8084";
//    public static String url = "http://120.79.179.178:8082";
    public static String contentType = "application/json";
//    public static String mainAddress = "";

    public static String mainAddress = "0x3e31f75806e4cbb7a0cea77fdeec7479475fb3bc";
    public static String mainPassword = "123";

    public static String doRequest(String methodName,Object[] params) throws Throwable {

        JsonRpcHttpClient client = new JsonRpcHttpClient(new URL(url));
        client.setContentType(contentType);
        Object address = client.invoke(methodName, params, Object.class);
        if(address==null){
            return null;
        }
        log.info("请求Rpc接口成功,返回address:{}", address.toString());
        return address.toString();
    }


//    public static void main(String[] args) {
//        JsonParams jsonParams = new JsonParams();
//        jsonParams.setFrom("0xbf3617cf0ebe38fad388ef102911e5cf2ad05aab");
//        jsonParams.setTo("0x2bec6b177e3052af78cfd0b314a6205495b28beb");
//        jsonParams.setValue("0xff");
//        Object[] objects = new Object[]{jsonParams};
////        JsonParams[] params = new JsonParams[]{jsonParams};
//        try {
//            JsonRpcHttpClient client = new JsonRpcHttpClient(new URL(url));
//            client.setContentType(contentType);
//            Object address = client.invoke("eth_sendTransaction", objects, Object.class);
//            System.out.println(address);
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//    }

    //5秒间断休眠的挖矿
    public static void miner_start() throws Throwable {
        Object[] params = new Object[]{1};
        doRequest("miner_start",params);
        log.info("开始挖矿....");
    }

    public static Object miner_stop() throws Throwable {
        Object[] params = new Object[]{1};
        log.info("停止挖矿....");
        return doRequest("miner_stop",params);
    }

    public static Object newAccount(String password) throws Throwable{
        Object[] params = new Object[]{password};
        String userAddress = RpcRequestUtil.doRequest("personal_newAccount",params);
        return userAddress;
    }

    public static Object unlockAccount(String address, String password) throws Throwable {
        Object[] params = new Object[]{address,password};
        return RpcRequestUtil.doRequest("personal_unlockAccount",params);
    }

    public static Object sendTransaction(String from, String to, String value) throws Throwable {
        JsonParams jsonParams = new JsonParams();
        jsonParams.setFrom(from);
        jsonParams.setTo(to);
        jsonParams.setValue(value);
        Object[] params = new Object[]{jsonParams};
        return doRequest("eth_sendTransaction",params);
    }
}
