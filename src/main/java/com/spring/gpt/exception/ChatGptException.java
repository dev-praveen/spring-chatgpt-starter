package com.spring.gpt.exception;

public class ChatGptException extends RuntimeException {

  public ChatGptException() {
    super();
  }

  public ChatGptException(String message) {
    super(message);
  }
}
