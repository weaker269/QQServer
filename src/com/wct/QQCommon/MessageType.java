package com.wct.QQCommon;

/**
 * @author WenCT
 * 表示消息类型
 */
public interface MessageType {
   //在接口中定义常量，不同常量的值表示不同的消息类型
   String MESSAGE_LOGIN_SUCCEED = "1";//表示登录成功
   String MESSAGE_LOGIN_FAIL = "2";//表示登录失败
}
