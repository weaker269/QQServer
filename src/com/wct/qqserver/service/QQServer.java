package com.wct.qqserver.service;

import com.wct.QQCommon.Message;
import com.wct.QQCommon.MessageType;
import com.wct.QQCommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author WenCT
 * 服务器，监听9999端口，等待客户端连接，并保持通信
 */
public class QQServer {
    private ServerSocket serverSocket = null;
    //用户集合，如果这些用户登录即成功
    private static ConcurrentMap<String,User> validUsers = new ConcurrentHashMap<>();
    static {
        validUsers.put("100",new User("100","123456"));
        validUsers.put("200",new User("200","123456"));
        validUsers.put("温楚涛",new User("温楚涛","123456"));
        validUsers.put("wct",new User("wct","123456"));
    }
    private boolean checkUser(String uid , String pwd){
        User user = validUsers.get(uid);
        if(user == null){
            return false;
        }
        if(!user.getPassword().equals(pwd)){
            return false;
        }
        return true;
    }
    public QQServer(){
        try {
            System.out.println("服务器在9999端口监听...");
            serverSocket = new ServerSocket(9999);
            while(true){ //当和某个客户端连接时会持续监听
                Socket socket = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                User u = (User)ois.readObject();
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                Message message = new Message();
                if(checkUser(u.getUid(), u.getPassword())){ //登录成功
                    message.setMessageType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    //回复客户端
                    oos.writeObject(message);
                    //创建一个线程，和客户端保持通信
                    ServerConnectClientThread serverConnectClientThread = new ServerConnectClientThread(socket, u.getUid());
                    serverConnectClientThread.start();
                    ManageClientThread.addServerConnectClientThread(u.getUid(), serverConnectClientThread);

                } else{ //登录失败
                    System.out.println("用户 id  =" + u.getUid() + "密码 = " + u.getPassword() + "验证失败");
                    message.setMessageType(MessageType.MESSAGE_LOGIN_FAIL);
                    oos.writeObject(message);
                    socket.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //退出while循环表示不再监听
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
