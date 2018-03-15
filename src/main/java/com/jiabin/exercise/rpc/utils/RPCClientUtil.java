package com.jiabin.exercise.rpc.utils;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by jiabi on 2018/3/15.
 */
public class RPCClientUtil<T> {
    public static <T> T getRemoteProxyObj(final Class<?> serviceInterface, final InetSocketAddress addr){
        return (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(), new Class<?>[]{serviceInterface}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket socket = null;
                ObjectOutputStream objectOutputStream = null;
                ObjectInputStream objectInputStream = null;
                try {
                    // 2.����Socket�ͻ��ˣ�����ָ����ַ����Զ�̷����ṩ��
                    socket = new Socket();
                    socket.connect(addr);

                    // 3.��Զ�̷����������Ľӿ��ࡢ�������������б�ȱ�����͸������ṩ��
                    objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeUTF(serviceInterface.getName());
                    objectOutputStream.writeUTF(method.getName());
                    objectOutputStream.writeObject(method.getParameterTypes());
                    objectOutputStream.writeObject(args);

                    // 4.ͬ�������ȴ�����������Ӧ�𣬻�ȡӦ��󷵻�
                    objectInputStream = new ObjectInputStream(socket.getInputStream());
                    return objectInputStream.readObject();
                }finally {
                    if (socket != null) socket.close();
                    if (objectOutputStream != null) objectOutputStream.close();
                    if (objectInputStream != null) objectInputStream.close();
                }
            }
        });
    }
}
