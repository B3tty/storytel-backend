package com.storytel.messageboard.models;

import java.util.Date;

public class Message {
  private long id;
  private String text;
  private String author;
  private Date timestamp;

  public String getText() {
    return text;
  }

  public String getAuthor() {
    return author;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }
}

