package com.wct.qqserver.service;

import com.wct.QQCommon.Message;
import com.wct.QQCommon.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author WenCT
 */
public class ServerService {

      public static void UserList(Message ms){
          System.out.println(ms.getSender() + " 要在线用户列表");
          ServerConnectClientThread serverConnectClientThread =
                  ManageClientThread.getServerConnectClientThread(ms.getSender());
          Socket socket = serverConnectClientThread.getSocket();
          try {
              ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
              Message retMes = new Message();
              retMes.setSender("Server");
              retMes.setMessageType(MessageType.MESSAGE_RET_ONLINE_USER);
              retMes.setContent(ManageClientThread.getOnlineUserList());
              retMes.setReceiver(ms.getSender());
              oos.writeObject(retMes);
          } catch (IOException e) {
              e.printStackTrace();
          }
      }
      public static void ErrorMessage(Message ms){
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

      public static void ExitMessage(Message ms){
          System.out.println(ms.getSender() + " 请求退出");
          Message retMes = new Message();
          ServerConnectClientThread serverConnectClientThread =
                  ManageClientThread.getServerConnectClientThread(ms.getSender());
          Socket socket = serverConnectClientThread.getSocket();
          try {
              ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
              retMes.setSender("Server");
              retMes.setReceiver(ms.getSender());
              retMes.setMessageType(MessageType.MESSAGE_CLIENT_EXIT_SUCCESS);
              oos.writeObject(retMes);
              ManageClientThread.removeServerConnectClientThread(ms.getSender());
              System.out.println("服务端成功使该用户退出");
          } catch (IOException e) {
              e.printStackTrace();
          }
      }

      public static void sendMessage(Message ms){
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
