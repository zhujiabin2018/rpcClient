package com.jiabin.exercise.rpc.client;

import com.jiabin.exercise.rpc.service.IHelloService;
import com.jiabin.exercise.rpc.utils.RPCClientUtil;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by jiabi on 2018/3/16.
 */
public class RpcClient {
    public static void main(String[] args) throws IOException{
        IHelloService helloService = RPCClientUtil.getRemoteProxyObj(IHelloService.class, new InetSocketAddress("localhost", 8088));
        System.out.println(helloService.sayHi("朱先生"));
    }
}
