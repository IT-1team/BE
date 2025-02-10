package seohan.hrmaster.domain.global.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import seohan.hrmaster.domain.auth.entity.Admin;
import seohan.hrmaster.domain.auth.repository.AdminRepository;
import seohan.hrmaster.domain.global.exception.CustomException;
import seohan.hrmaster.domain.global.exception.ErrorCode;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        Admin admin = adminRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        return new UserDetailsImpl(admin);
    }
}