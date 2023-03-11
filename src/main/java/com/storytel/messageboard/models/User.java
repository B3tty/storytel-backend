package com.storytel.messageboard.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
// Initial Value to take into account Admin user
@SequenceGenerator(name = "USER_SEQUENCE_GENERATOR", initialValue = 2)
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQUENCE_GENERATOR")
    private long id;
    @Column(name = "name")
    @NotBlank(message = "Name is mandatory")
    private String name;
}
