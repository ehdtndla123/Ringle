package ringle.ringle.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ringle.ringle.application.user.StudentService;
import ringle.ringle.application.user.TutorService;

@RestController
@RequestMapping("/api/example")
@RequiredArgsConstructor
public class ExampleController {
    private final StudentService studentService;
    private final TutorService tutorService;

    @PostMapping("/student")
    public ResponseEntity<Long> createStudent(@RequestParam String name, @RequestParam String email) {
        Long studentId = studentService.createStudent(name, email);
        return ResponseEntity.ok(studentId);
    }

    @PostMapping("/tutor")
    public ResponseEntity<Long> createTutor(@RequestParam String name, @RequestParam String email) {
        Long tutorId = tutorService.createTutor(name, email);
        return ResponseEntity.ok(tutorId);
    }
}
