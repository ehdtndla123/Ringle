package ringle.ringle.application.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ringle.ringle.common.exception.ErrorCode;
import ringle.ringle.common.exception.StudentException;
import ringle.ringle.dao.StudentRepository;
import ringle.ringle.domain.user.Student;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public Student getStudent(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentException(ErrorCode.STUDENT_NOT_FOUND));
    }

    @Transactional
    public Long createStudent(String name, String email) {
        Student student = Student.builder()
                .name(name)
                .email(email)
                .build();

        Student savedStudent = studentRepository.save(student);
        return savedStudent.getId();
    }
}
