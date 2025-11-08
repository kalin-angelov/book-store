package app.loginRateLimiter.model;

import app.user.model.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
public class LoginRateLimiter {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    private User user;

    @Column(nullable = false)
    private Integer attempts;

    private LocalDateTime limitExpiredAt;

}
