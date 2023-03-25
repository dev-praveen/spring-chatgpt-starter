package com.spring.gpt.service;

import com.spring.gpt.dto.ChatRequest;
import com.spring.gpt.dto.ChatResponse;

public interface ChatGptService {
  String sendMessage(String message);

  ChatResponse sendChatRequest(ChatRequest request);
}
