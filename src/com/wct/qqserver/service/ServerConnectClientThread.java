package com.wct.qqserver.service;

import com.wct.QQCommon.Message;
import com.wct.QQCommon.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
                System.out.println("服务端和客户端  " + uid + "  保持通信，读取数据....");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message ms = (Message) ois.readObject();
                //使用message
                if(ms.getMessageType().equals(MessageType.MESSAGE_GET_ONLINE_USER)){//拉取在线用户列表请求
                    System.out.println(ms.getSender() + " 要在线用户列表");
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    Message retMs = new Message();
                    retMs.setMessageType(MessageType.MESSAGE_RET_ONLINE_USER);
                    retMs.setContent(ManageClientThread.getOnlineUserList());
                    retMs.setReceiver(ms.getSender());
                    oos.writeObject(retMs);
                }
                else{//其他

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
