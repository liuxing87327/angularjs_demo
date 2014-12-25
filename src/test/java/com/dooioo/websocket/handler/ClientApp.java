package com.dooioo.websocket.handler;

import javax.websocket.*;

/**
 * 功能说明：ClientApp
 * 作者：liuxing(2014-12-26 02:26)
 */
@ClientEndpoint
public class ClientApp {

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("客户端已连接");
//        System.out.println("Connected to endpoint: " + session.getBasicRemote());
//        try {
//            session.getBasicRemote().sendText("Hello");
//        } catch (IOException ex) {
//        }
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("收到消息：" + message);
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }

}
