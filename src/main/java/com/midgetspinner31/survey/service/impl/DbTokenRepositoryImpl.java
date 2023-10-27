package com.midgetspinner31.survey.service.impl;

import com.midgetspinner31.survey.db.dao.PersistentLoginRepository;
import com.midgetspinner31.survey.db.entity.PersistentLogin;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DbTokenRepositoryImpl implements PersistentTokenRepository {
    PersistentLoginRepository persistentLoginRepository;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        if (persistentLoginRepository.existsBySeries(token.getSeries()))
            throw new DataIntegrityViolationException("Series Id '" + token.getSeries() + "' already exists!");

        var dbToken = PersistentLogin.builder()
                .series(token.getSeries())
                .token(token.getTokenValue())
                .username(token.getUsername())
                .lastUsed(token.getDate()).build();
        persistentLoginRepository.save(dbToken);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        var oToken = persistentLoginRepository.findBySeries(series);
        if (oToken.isEmpty())
            return;

        var token = oToken.get();
        token.setToken(tokenValue);
        token.setLastUsed(lastUsed);
        persistentLoginRepository.save(token);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        var token = persistentLoginRepository.findBySeries(seriesId);
        return token.map(persistentLogin ->
                new PersistentRememberMeToken(
                        persistentLogin.getUsername(),
                        persistentLogin.getSeries(),
                        persistentLogin.getToken(),
                        persistentLogin.getLastUsed()))
                .orElse(null);
    }

    @Override
    public void removeUserTokens(String username) {
        persistentLoginRepository.deleteAllByUsername(username);
    }
}
