package app.token.service;

import app.token.model.Token;
import app.token.model.TokenType;
import app.token.repository.TokenRepository;
import app.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public void initializeToken(User user, String token) {

        Token generatedToken = Token.builder()
                .token(token)
                .type(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .owner(user)
                .build();

        tokenRepository.save(generatedToken);
    }

    public void revokedAllUserTokens(User user) {

        List<Token> allUnexpiredAndUnRevokedTokens = tokenRepository.findAllValidTokensByUser(user.getId());

        if (allUnexpiredAndUnRevokedTokens.isEmpty()){
            return;
        }

        allUnexpiredAndUnRevokedTokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });

        tokenRepository.saveAll(allUnexpiredAndUnRevokedTokens);
    }

    public Optional<Token> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public void revokeToken(Token token) {
        token.setRevoked(true);
        token.setExpired(true);
        tokenRepository.save(token);
    }
}
