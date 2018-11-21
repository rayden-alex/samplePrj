package myProg.services;

import myProg.dao.SecurUserDao;
import myProg.domain.SecurRole;
import myProg.domain.SecurUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Lazy
public class SecurUserService implements UserDetailsService {

    private final SecurUserDao userDao;

    @Autowired
    public SecurUserService(SecurUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        SecurUser user = userDao.findUserByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid login or password.");
        }

        // org.springframework.security.core.userdetails.User
        return new User(
                user.getLogin(),
                user.getPass(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(@NonNull Collection<SecurRole> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
