package com.dooioo.websocket.handler;

import com.dooioo.websocket.utils.SessionUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * 功能说明：websocket处理类, 使用J2EE7的标准
 * 作者：liuxing(2014-11-14 04:20)
 */
@ServerEndpoint("/testwebsocket/{inquiryId}/{empNo}")
public class WebsocketEndPoint {

    /**
     * 打开连接时触发
     * @param inquiryId
     * @param empNo
     * @param session
     */
    @OnOpen
    public void onOpen(@PathParam("inquiryId") String inquiryId,
                       @PathParam("empNo") int empNo,
                       Session session){
        SessionUtils.put(inquiryId + "_" + empNo, session);
    }

    /**
     * 收到客户端消息时触发
     * @param inquiryId
     * @param empNo
     * @param message
     * @return
     */
    @OnMessage
    public String onMessage(@PathParam("inquiryId") String inquiryId,
                            @PathParam("empNo") int empNo,
                            String message) {
        return "Got your message (" + message + ").Thanks !";
    }

    /**
     * 异常时触发
     * @param inquiryId
     * @param empNo
     * @param session
     */
    @OnError
    public void onError(@PathParam("inquiryId") String inquiryId,
                        @PathParam("empNo") int empNo,
                        Session session) {
        SessionUtils.remove(inquiryId + "_" + empNo);
    }

    /**
     * 关闭连接时触发
     * @param inquiryId
     * @param empNo
     * @param session
     */
    @OnClose
    public void onClose(@PathParam("inquiryId") String inquiryId,
                        @PathParam("empNo") int empNo,
                        Session session) {
        SessionUtils.remove(inquiryId + "_" + empNo);
    }

}