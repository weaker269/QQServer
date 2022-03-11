package com.wct.QQCommon;

/**
 * @author WenCT
 * 表示消息类型
 */
public interface MessageType {
   //在接口中定义常量，不同常量的值表示不同的消息类型
   String MESSAGE_LOGIN_SUCCEED = "1";//表示登录成功
   String MESSAGE_LOGIN_FAIL = "2";//表示登录失败
   String MESSAGE_COMM_MES = "3"; //普通信息包
   String MESSAGE_GET_ONLINE_USER = "4"; //请求在线用户列表
   String MESSAGE_RET_ONLINE_USER = "5"; //返回在线用户列表
   String MESSAGE_CLIENT_EXIT = "6"; //客户端请求退出
   String MESSAGE_CLIENT_EXIT_SUCCESS = "7"; //客户端成功退出
   String MESSAGE_ERROR_RECEIVER_OFFLINE = "8"; // 接收者离线，发送失败
}
