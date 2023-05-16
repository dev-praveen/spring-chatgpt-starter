package io.github.dev.praveen.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "chatgpt")
public class ChatGptProperties {

  private String apiKey = "";
  private String url = "https://api.openai.com/v1/completions";
  private String model = "text-davinci-003";
  private Integer maxTokens = 500;
  private Double temperature = 1.0;
  private Double topP = 1.0;
}
