package cins.art.numproduct.Util.web3;


import cins.art.numproduct.Util.RpcRequestUtil;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.parity.Parity;

public class ParityClient {


    private ParityClient(){}

    private static class ClientHolder{
        private static final Parity parity = Parity.build(new HttpService(RpcRequestUtil.url));
    }

    public static final  Parity getParity(){
        return ClientHolder.parity;
    }
}
