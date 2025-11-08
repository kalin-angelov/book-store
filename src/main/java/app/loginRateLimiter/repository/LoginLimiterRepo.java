package app.loginRateLimiter.repository;

import app.loginRateLimiter.model.LoginRateLimiter;
import app.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LoginLimiterRepo extends JpaRepository<LoginRateLimiter, UUID> {
    Optional<LoginRateLimiter> findByUser(User user);
}
