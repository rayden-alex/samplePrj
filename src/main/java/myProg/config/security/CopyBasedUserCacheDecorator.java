package myProg.config.security;

import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;

public class CopyBasedUserCacheDecorator implements UserCache {
    private final UserCache delegateCache;

    public CopyBasedUserCacheDecorator(UserCache delegateCache) {
        this.delegateCache = delegateCache;
    }
    // If the Authentication contains a reference to an object in the cache (such as a UserDetails instance)
    // and this has its credentials removed, then it will no longer be possible to authenticate against the cached value.
    // You need to take this into account if you are using a cache.
    // An obvious solution is to make a copy of the object first, either in the cache implementation
    // or in the AuthenticationProvider which creates the returned Authentication object.

    @Nullable
    @Override
    public UserDetails getUserFromCache(String username) {
        UserDetails userDetails = delegateCache.getUserFromCache(username);

        if (userDetails == null) {
            return null;
        } else {
            // always return outside copy of "userDetails" !
            return User.withUserDetails(userDetails).build();
        }
    }

    @Override
    public void putUserInCache(UserDetails userDetails) {
        if (userDetails != null && userDetails.getPassword() != null) {
            // Put in cache only "userDetails" with valid password
            // because of "Erasing Credentials on Successful Authentication"
            // @see myProg/config/security/SecurityConfig.java:149
            UserDetails copyUserDetails = User.withUserDetails(userDetails).build();
            delegateCache.putUserInCache(copyUserDetails);
        }
    }

    @Override
    public void removeUserFromCache(String username) {
        delegateCache.removeUserFromCache(username);
    }
}
