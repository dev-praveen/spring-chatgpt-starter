package com.spring.gpt.config;

import com.spring.gpt.properties.ChatGptProperties;
import com.spring.gpt.service.ChatGptService;
import com.spring.gpt.service.impl.DefaultChatGptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableConfigurationProperties(ChatGptProperties.class)
public class ChatGptAutoConfiguration {

  @Autowired private ChatGptProperties chatgptProperties;

  public ChatGptAutoConfiguration() {
    log.debug("chatgpt-springboot-starter loaded.");
  }

  @Bean
  @ConditionalOnMissingBean(ChatGptService.class)
  public ChatGptService chatgptService() {
    return new DefaultChatGptService(chatgptProperties);
  }
}
