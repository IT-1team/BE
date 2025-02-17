package seohan.hrmaster.domain.attendance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import seohan.hrmaster.domain.attendance.entity.Attendance;

import java.time.LocalDate;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Page<Attendance> findByToday(LocalDate today, Pageable pageable);
}
