package com.dooioo.websocket.utils;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 功能说明：SessionUtils
 * 作者：liuxing(2014-12-26 02:32)
 */
public class SessionUtils {

    public static Map<String, Session> clients = new ConcurrentHashMap<>();

    public static void put(String clientId, Session session){
        clients.put(clientId, session);
    }

    public static Session get(String clientId){
        return clients.get(clientId);
    }

    public static void remove(String clientId){
        clients.remove(clientId);
    }

}
