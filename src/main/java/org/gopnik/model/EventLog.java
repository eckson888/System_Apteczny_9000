package org.gopnik.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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

    private Long drugstoreId;
    @Column(updatable = true, name="event_description", nullable = true, columnDefinition = "varchar(99999)") // najmniejszy varchar na umcsie
    private String eventDescription;

    public EventLog(String username, Long drugstoreId, String eventDescription) {
        this.timestamp = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        this.username = username;
        this.drugstoreId = drugstoreId;
        this.eventDescription = eventDescription;
    }

    @Override
    public String toString() {
        // TODO: ROZKMINIC JAK MAJA WYGLADAC LOGINY (np. na stala ilosc znakow wtedy logi nie beda sie rozjezdzac)
        return timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " | " + String.format("%-5s",username) + " | " + drugstoreId + " | " + eventDescription;
    }

    public String toCsv() {
        return timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ";" + username + ";" + drugstoreId + ";" + eventDescription;
    }
}
