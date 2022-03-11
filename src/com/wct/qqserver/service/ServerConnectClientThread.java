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

    public Socket getSocket() {
        return socket;
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
                    Message retMes = new Message();
                    retMes.setSender("Server");
                    retMes.setMessageType(MessageType.MESSAGE_RET_ONLINE_USER);
                    retMes.setContent(ManageClientThread.getOnlineUserList());
                    retMes.setReceiver(ms.getSender());
                    oos.writeObject(retMes);
                }
                else if(ms.getMessageType().equals(MessageType.MESSAGE_CLIENT_EXIT)){
                    System.out.println(ms.getSender() + " 请求退出");
                    Message retMes = new Message();
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    retMes.setSender("Server");
                    retMes.setReceiver(ms.getSender());
                    retMes.setMessageType(MessageType.MESSAGE_CLIENT_EXIT_SUCCESS);
                    oos.writeObject(retMes);
                    ManageClientThread.removeServerConnectClientThread(uid);
                    System.out.println("服务端成功使该用户退出");
                    socket.close();
                    break;
                }
                else if(ms.getMessageType().equals(MessageType.MESSAGE_COMM_MES)){
                    System.out.println(ms.getSender() + " 请求发送消息给 " + ms.getReceiver());
                    if(!ManageClientThread.containsUser(ms.getReceiver())){
                        System.out.println("接收方离线，无法发送! 返回错误信息");
                        Message ErrorMes = new Message();
                        ErrorMes.setContent("接收方 " + ms.getReceiver() + " 离线，发送失败!");
                        ErrorMes.setReceiver(ms.getSender());
                        ErrorMes.setMessageType(MessageType.MESSAGE_ERROR_RECEIVER_OFFLINE);
                        try {
                            ServerConnectClientThread serverConnectClientThread =
                                    ManageClientThread.getServerConnectClientThread(ms.getSender());
                            Socket socket = serverConnectClientThread.getSocket();
                            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                            oos.writeObject(ErrorMes);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                    else{
                        try {
                            ServerConnectClientThread serverConnectClientThread =
                                    ManageClientThread.getServerConnectClientThread(ms.getReceiver());
                            Socket socket = serverConnectClientThread.getSocket();
                            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                            oos.writeObject(ms);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                }
                else{//其他

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
