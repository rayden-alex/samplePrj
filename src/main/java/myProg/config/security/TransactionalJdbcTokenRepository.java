package myProg.config.security;

import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TransactionalJdbcTokenRepository implements PersistentTokenRepository {

    private final JdbcTokenRepositoryImpl delegate;


    public TransactionalJdbcTokenRepository(JdbcTokenRepositoryImpl delegate) {
        this.delegate = delegate;
    }

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        delegate.createNewToken(token);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        delegate.updateToken(series, tokenValue, lastUsed);
    }

    @Override
    public void removeUserTokens(String username) {
        delegate.removeUserTokens(username);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        return delegate.getTokenForSeries(seriesId);
    }
}
