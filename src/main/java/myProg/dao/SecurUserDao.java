package myProg.dao;

import myProg.domain.SecurUser;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface SecurUserDao extends MyJpaRepository<SecurUser, Long> {
    // Returns null when the query executed does not produce a result.
    @Nullable
    SecurUser findUserById(@NonNull Long id);// Accepts "null" as the value for "id"

    @Nullable
    SecurUser findUserByLogin(@NonNull String login);// Don't accepts "null" as the value for "id"
}
