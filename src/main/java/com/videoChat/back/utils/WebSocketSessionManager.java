package com.videoChat.back.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionManager {
  // 单独聊天会话池
  public static ConcurrentHashMap<String, WebSocketSession> CHAT_ALONE_SESSION_POLL = new ConcurrentHashMap<>();
  // 群聊会话池
  public static ConcurrentHashMap<String, WebSocketSession> CHAT_GROUP_SESSION_POLL = new ConcurrentHashMap<>();
  // 更新消息会话池
  public static ConcurrentHashMap<String, WebSocketSession> UPDATE_MESSAGE_SESSION_POLL = new ConcurrentHashMap<>();

  public static void add_chat_alone(String key, WebSocketSession session) {
    CHAT_ALONE_SESSION_POLL.put(key, session);
  }

  public static void add_chat_group(String key, WebSocketSession session) {
    CHAT_GROUP_SESSION_POLL.put(key, session);
  }

  public static void add_update_message(String key, WebSocketSession session) {
    UPDATE_MESSAGE_SESSION_POLL.put(key, session);
  }


  public static WebSocketSession remove_chat_alone(String key) {
    return CHAT_ALONE_SESSION_POLL.remove(key);
  }

  public static WebSocketSession remove_chat_group(String key) {
    return CHAT_GROUP_SESSION_POLL.remove(key);
  }

  public static WebSocketSession remove_update_message(String key) {
    return UPDATE_MESSAGE_SESSION_POLL.remove(key);
  }

  public static void removeAndClose_chat_alone(String key) {
    WebSocketSession session = CHAT_ALONE_SESSION_POLL.get(key);
    if (session != null) {
      try {
        session.close();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        CHAT_ALONE_SESSION_POLL.remove(key);
      }
    }
  }

  public static void removeAndClose_chat_group(String key) {
    WebSocketSession session = CHAT_GROUP_SESSION_POLL.get(key);
    if (session != null) {
      try {
        session.close();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        CHAT_GROUP_SESSION_POLL.remove(key);
      }
    }
  }

  public static void removeAndClose_update_message(String key) {
    WebSocketSession session = UPDATE_MESSAGE_SESSION_POLL.get(key);
    if (session != null) {
      try {
        session.close();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        UPDATE_MESSAGE_SESSION_POLL.remove(key);
      }
    }
  }

  public static WebSocketSession get_chat_alone(String key) {
    return CHAT_ALONE_SESSION_POLL.get(key);
  }

  public static WebSocketSession get_chat_group(String key) {
    return CHAT_GROUP_SESSION_POLL.get(key);
  }

  public static WebSocketSession get_update_message(String key) {
    return UPDATE_MESSAGE_SESSION_POLL.get(key);
  }
}
