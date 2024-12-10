package org.gopnik.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
@Getter
@NoArgsConstructor
public class EventLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;

    @NotBlank
    private String username;

    private String eventDescription;

    public EventLog(String username, String eventDescription) {
        this.timestamp = LocalDateTime.now();
        this.username = username;
        this.eventDescription = eventDescription;
    }

    @Override
    public String toString() {
        return timestamp + " | " + username + " | " + eventDescription;
    }
}
