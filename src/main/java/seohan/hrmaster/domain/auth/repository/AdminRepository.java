package seohan.hrmaster.domain.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seohan.hrmaster.domain.auth.entity.Admin;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByLoginId(String loginId);
}
