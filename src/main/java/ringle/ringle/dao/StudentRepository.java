package ringle.ringle.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ringle.ringle.domain.user.Student;

public interface StudentRepository extends JpaRepository<Student,Long> {
}
