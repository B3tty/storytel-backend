package com.storytel.messageboard.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Message {

  @Id
  @GeneratedValue
  private Integer id;
  @NotBlank(message = "Message is mandatory")
  private String content;
  @NotBlank(message = "Name is mandatory")
  private String author;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}

