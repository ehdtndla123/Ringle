package ringle.ringle.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ringle.ringle.application.lesson.LessonService;
import ringle.ringle.application.StudentLessonFacade;
import ringle.ringle.dto.lesson.AvailableTimeResponse;
import ringle.ringle.dto.lesson.LessonEnrollmentRequest;
import ringle.ringle.dto.lesson.LessonEnrollmentResponse;
import ringle.ringle.dto.user.TutorResponse;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentLessonController {
    private final LessonService lessonService;
    private final StudentLessonFacade studentLessonFacade;

    // 수업 가능한 시간대 조회
    @GetMapping("/available-lessons")
    public ResponseEntity<List<AvailableTimeResponse>> getAvailableLessons(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam int duration) {
        List<AvailableTimeResponse> availableLessonTime = lessonService.findAvailableLessonTime(startTime, endTime, duration);
        return ResponseEntity.ok(availableLessonTime);
    }

    // 수업 가능한 튜터 조회
    @GetMapping("/available-tutors")
    public ResponseEntity<List<TutorResponse>> getAvailableTutors(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam int duration) {
        List<TutorResponse> tutors = lessonService.findAvailableTutors(startTime, duration);
        return ResponseEntity.ok(tutors);
    }

    // 수업 신청
    @PostMapping("/lessons")
    public ResponseEntity<LessonEnrollmentResponse> enrollLesson(
            @RequestParam Long studentId,
            @RequestBody LessonEnrollmentRequest request) {
        LessonEnrollmentResponse enrollmentResponse = studentLessonFacade.enrollLesson(studentId, request);
        return ResponseEntity.ok(enrollmentResponse);
    }

    // 신청한 수업 조회
    @GetMapping("/enrollments")
    public ResponseEntity<List<LessonEnrollmentResponse>> getLessonEnrollments(
            @RequestParam Long studentId) {
        List<LessonEnrollmentResponse> enrollments = studentLessonFacade.getLessonEnrollmentsByStudent(studentId);
        return ResponseEntity.ok(enrollments);
    }
}