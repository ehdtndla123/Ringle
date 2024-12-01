package ringle.ringle.application.lesson;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ringle.ringle.common.exception.*;
import ringle.ringle.dao.LessonRepository;
import ringle.ringle.dao.TutorRepository;
import ringle.ringle.domain.lesson.Lesson;
import ringle.ringle.domain.lesson.LessonDuration;
import ringle.ringle.domain.lesson.LessonStartTime;
import ringle.ringle.domain.lesson.LessonStatus;
import ringle.ringle.domain.user.Tutor;
import ringle.ringle.dto.lesson.AvailableTimeResponse;
import ringle.ringle.dto.lesson.LessonCreateRequest;
import ringle.ringle.dto.user.TutorResponse;
import ringle.ringle.common.event.LessonDeletedEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final TutorRepository tutorRepository;

    public List<AvailableTimeResponse> findAvailableLessonTime(
            LocalDateTime startTime,
            LocalDateTime endTime,
            int duration) {
        validateLessonRequest(startTime, duration);
        List<Lesson> lessons = lessonRepository.findAvailableLessons(startTime, endTime, duration);

        return lessons.stream()
                .map(AvailableTimeResponse::from)
                .collect(Collectors.toList());
    }

    public List<TutorResponse> findAvailableTutors(LocalDateTime startTime, int duration) {
        validateLessonRequest(startTime, duration);
        List<Tutor> tutors = tutorRepository.findAvailableTutors(startTime, duration);
        return tutors.stream()
                .map(TutorResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public Lesson createLesson(LessonCreateRequest dto) {
        Tutor tutor = tutorRepository.findById(dto.getTutorId())
                .orElseThrow(() -> new TutorException(ErrorCode.TUTOR_NOT_FOUND));

        validateLessonRequest(dto.getStartTime(),dto.getDuration());

        Lesson lesson = Lesson.builder()
                .tutor(tutor)
                .startTime(dto.getStartTime())
                .duration(dto.getDuration())
                .status(LessonStatus.AVAILABLE)
                .build();

        return lessonRepository.save(lesson);
    }

    @Transactional
    public void deleteLesson(Long lessonId, Long tutorId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonException(ErrorCode.LESSON_NOT_FOUND));

        // 튜터 검증
        validateTutorAuthorization(lesson, tutorId);
        // 삭제 가능 여부 검증
        validateDeletion(lesson);

        lesson.softDelete();
        eventPublisher.publishEvent(new LessonDeletedEvent(lesson));
    }



    private void validateTutorAuthorization(Lesson lesson, Long tutorId) {
        if (!lesson.getTutor().getId().equals(tutorId)) {
            throw new LessonException(ErrorCode.UNAUTHORIZED_TUTOR);
        }
    }

    private void validateDeletion(Lesson lesson) {
        if (lesson.getStatus() != LessonStatus.AVAILABLE) {
            throw new LessonException(ErrorCode.INVALID_LESSON_STATUS);
        }
    }

    private void validateLessonRequest(LocalDateTime startTime,int duration) {
        if (!LessonDuration.isValid(duration)) {
            throw new LessonException(ErrorCode.INVALID_DURATION);
        }

        if (!LessonStartTime.isValid(startTime)) {
            throw new LessonException(ErrorCode.INVALID_START_TIME);
        }

        LocalDateTime now = LocalDateTime.now();
        if (startTime.isBefore(now)) {
            throw new LessonException(ErrorCode.PASS_START_TIME);
        }
    }
}
