package ru.karmazin.manager.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.karmazin.manager.entity.Authority;
import ru.karmazin.manager.repository.SelmagUserRepository;

@Service
@RequiredArgsConstructor
public class SelmagUserDetailsService implements UserDetailsService {

    private final SelmagUserRepository selmagUserRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.selmagUserRepository.findByUsername(username)
            .map(user -> User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities().stream()
                    .map(Authority::getAuthority)
                    .map(SimpleGrantedAuthority::new)
                    .toList())
                .build())
            .orElseThrow(() -> new UsernameNotFoundException("User %s not found!".formatted(username)));
    }
}
