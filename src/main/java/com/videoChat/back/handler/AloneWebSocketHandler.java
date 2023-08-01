package com.videoChat.back.handler;



import com.google.gson.Gson;
import com.videoChat.back.entityVo.MessageVo;
import com.videoChat.back.utils.WebSocketSessionManager;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;


@Component
public class AloneWebSocketHandler extends AbstractWebSocketHandler {

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    super.afterConnectionEstablished(session);
    String uri = session.getUri().toString();
    String[] uriArr = uri.split("/");
    String token = uriArr[uriArr.length - 1];
    WebSocketSessionManager.add_chat_alone(token, session);
    System.out.println("单独聊天websocket连接");
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    super.handleTextMessage(session, message);
    Gson gson = new Gson();
    MessageVo acceptMessage = gson.fromJson(message.getPayload(), MessageVo.class);
    Integer userId = acceptMessage.getUserId();
    Integer friendId = acceptMessage.getFriendId();
//    String token = friendId.toString() + "_" + userId.toString();
    String token = userId.toString() + "_" + friendId.toString();
    WebSocketSession sendSession = WebSocketSessionManager.get_chat_alone(token);
    if (sendSession != null) {
      sendSession.sendMessage(new TextMessage(message.getPayload()));
    } else {
      WebSocketSession updateSession = WebSocketSessionManager.get_update_message(friendId.toString());
      if (updateSession != null) {
        updateSession.sendMessage(new TextMessage(message.getPayload()));
      }
    }
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
    WebSocketSessionManager.removeAndClose_chat_alone(token);
    System.out.println("单独聊天websocket断开");
  }
}
