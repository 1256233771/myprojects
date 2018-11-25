package cins.art.numproduct.Util.thread;


import cins.art.numproduct.Util.RpcRequestUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyThread extends Thread {
    @Override
    public void run() {
        while (true){
            try {
                RpcRequestUtil.miner_start();
                sleep(60000*5);//挖矿五秒休眠时间
                RpcRequestUtil.miner_stop();
            } catch (Throwable throwable) {
                log.error("挖矿异常...");
            }
        }
    }
}
