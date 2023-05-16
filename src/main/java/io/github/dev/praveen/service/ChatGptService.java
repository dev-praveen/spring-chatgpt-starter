package io.github.dev.praveen.service;

import io.github.dev.praveen.dto.ChatRequest;
import io.github.dev.praveen.dto.ChatResponse;

public interface ChatGptService {
  String sendMessage(String message);

  ChatResponse sendChatRequest(ChatRequest request);
}
