package ringle.ringle.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ringle.ringle.domain.lesson.Lesson;
import ringle.ringle.dto.lesson.LessonCreateRequest;
import ringle.ringle.dto.lesson.LessonCreateResponse;
import ringle.ringle.dto.lesson.LessonDeleteRequest;
import ringle.ringle.application.lesson.LessonService;

@RestController
@RequestMapping("/api/tutor")
@RequiredArgsConstructor
public class TutorLessonController {
    private final LessonService lessonService;

    // 수업 생성
    @PostMapping("/lessons")
    public ResponseEntity<LessonCreateResponse> createLesson(@RequestBody LessonCreateRequest dto) {
        Lesson lesson = lessonService.createLesson(dto);
        return ResponseEntity.ok(LessonCreateResponse.from(lesson));
    }

    // 수업 삭제
    @DeleteMapping("/lessons")
    public ResponseEntity<Void> deleteLesson(@RequestBody LessonDeleteRequest dto) {
        lessonService.deleteLesson(dto.getLessonId(), dto.getTutorId());
        return ResponseEntity.noContent().build();
    }

}
