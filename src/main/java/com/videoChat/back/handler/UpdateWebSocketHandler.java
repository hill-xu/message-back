package com.videoChat.back.handler;

import com.videoChat.back.utils.WebSocketSessionManager;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

@Component
public class UpdateWebSocketHandler extends AbstractWebSocketHandler {
  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    super.afterConnectionEstablished(session);
    String uri = session.getUri().toString();
    String[] uriArr = uri.split("/");
    String token = uriArr[uriArr.length - 1];
    WebSocketSessionManager.add_update_message(token, session);
    System.out.println("更新消息websocket连接");
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    super.handleTextMessage(session, message);
  }

  @Override
  protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
    super.handleBinaryMessage(session, message);
  }

  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    super.handleTransportError(session, exception);
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    super.afterConnectionClosed(session, status);
    String uri = session.getUri().toString();
    String[] uriArr = uri.split("/");
    String token = uriArr[uriArr.length - 1];
    WebSocketSessionManager.removeAndClose_update_message(token);
    System.out.println("更新消息websocket断开");
  }
}
