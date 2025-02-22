package seohan.hrmaster.domain.global.user;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import seohan.hrmaster.domain.auth.entity.Admin;

import java.util.Collection;
import java.util.Collections;

@Getter
public class UserDetailsImpl implements UserDetails {

    private final Admin admin;

    public UserDetailsImpl(Admin admin) {
        this.admin = admin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        // 사용자의 암호화된 비밀번호를 반환
        return admin.getPassword();
    }

    @Override
    public String getUsername() {
        // 사용자의 사용자 이름을 반환
        return admin.getLoginId();
    }

    @Override
    public boolean isAccountNonExpired() {
        // 계정이 만료되지 않았는지 여부를 반환
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 계정이 잠기지 않았는지 여부를 반환
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 비밀번호가 만료되지 않았는지 여부를 반환
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 계정이 활성화되었는지 여부를 반환
        return true;
    }
}
