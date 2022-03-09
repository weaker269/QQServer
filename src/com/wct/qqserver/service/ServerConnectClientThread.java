package com.wct.qqserver.service;

import com.wct.QQCommon.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * @author WenCT
 * 该类对应的对象和某个客户端保持连接
 */
public class ServerConnectClientThread extends Thread{
    private Socket socket;
    private String uid;//连接到服务端的uid
    public ServerConnectClientThread(Socket socket,String uid){
           this.socket = socket;
           this.uid = uid;
    }

    @Override
    public void run() { //run状态，线程可以接受和发送消息
        while(true){
            try {
                System.out.println("服务端和客户端" + uid + "保持通信，读取数据....");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message ms = (Message) ois.readObject();
                //后面会使用message
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
