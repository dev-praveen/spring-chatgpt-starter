package com.spring.gpt.service.impl;

import com.spring.gpt.dto.ChatRequest;
import com.spring.gpt.dto.ChatResponse;
import com.spring.gpt.exception.ChatGptException;
import com.spring.gpt.properties.ChatGptProperties;
import com.spring.gpt.service.ChatGptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class DefaultChatGptService implements ChatGptService {

  private final WebClient webClient = WebClient.create();
  protected final ChatGptProperties chatgptProperties;
  private final String AUTHORIZATION;

  public DefaultChatGptService(ChatGptProperties chatgptProperties) {
    this.chatgptProperties = chatgptProperties;
    AUTHORIZATION = "Bearer " + chatgptProperties.getApiKey();
  }

  @Override
  public String sendMessage(String message) {
    final var chatRequest =
        new ChatRequest(
            chatgptProperties.getModel(),
            message,
            chatgptProperties.getMaxTokens(),
            chatgptProperties.getTemperature(),
            chatgptProperties.getTopP());
    ChatResponse chatResponse =
        getResponse(chatRequest, ChatResponse.class, chatgptProperties.getUrl());
    try {
      return chatResponse.getChoices().get(0).getText();
    } catch (Exception e) {
      log.error("parse chat gpt message error", e);
      throw e;
    }
  }

  @Override
  public ChatResponse sendChatRequest(ChatRequest request) {
    return getResponse(request, ChatResponse.class, chatgptProperties.getUrl());
  }

  private <T> T getResponse(ChatRequest chatRequest, Class<T> responseType, String url) {

    log.info("request url: {}, chatRequest: {}", url, chatRequest);
    return webClient
        .post()
        .uri(url)
        .contentType(MediaType.parseMediaType("application/json; charset=UTF-8"))
        .header("Authorization", AUTHORIZATION)
        .body(chatRequest, ChatRequest.class)
        .retrieve()
        .onStatus(
            HttpStatusCode::isError,
            clientResponse ->
                Mono.error(
                    new ChatGptException(
                        ("error response status :" + clientResponse.statusCode().value()))))
        .bodyToMono(responseType)
        .block();
  }
}
