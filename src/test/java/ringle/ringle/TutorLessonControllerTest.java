package ringle.ringle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ringle.ringle.application.lesson.LessonService;
import ringle.ringle.domain.lesson.Lesson;
import ringle.ringle.domain.lesson.LessonStatus;
import ringle.ringle.domain.user.Tutor;
import ringle.ringle.dto.lesson.LessonCreateRequest;
import ringle.ringle.dto.lesson.LessonCreateResponse;
import ringle.ringle.dto.lesson.LessonDeleteRequest;
import ringle.ringle.presentation.TutorLessonController;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class TutorLessonControllerTest {
    @Mock
    private LessonService lessonService;

    @InjectMocks
    private TutorLessonController tutorLessonController;

    private Long tutorId;
    private Long lessonId;
    private LocalDateTime startTime;
    private LessonCreateRequest createRequest;
    private LessonDeleteRequest deleteRequest;
    private Lesson lesson;
    private Tutor tutor;
    private LessonCreateResponse createResponse;

    @BeforeEach
    void setUp() {
        tutorId = 1L;
        lessonId = 1L;

        startTime = LocalDateTime.of(2024, 12, 1, 14, 0);

        tutor = Tutor.builder()
                .id(tutorId)
                .name("장동수")
                .email("ehdtndla123@gmail.com")
                .build();

        lesson = Lesson.builder()
                .id(lessonId)
                .tutor(tutor)
                .startTime(startTime)
                .duration(30)
                .status(LessonStatus.AVAILABLE)
                .build();

        createRequest = new LessonCreateRequest(tutorId, startTime, 30);
        deleteRequest = new LessonDeleteRequest(tutorId, lessonId, "급한일이 생겼습니다.");

        createResponse = LessonCreateResponse.builder()
                .id(lessonId)
                .startTime(startTime)
                .duration(30)
                .build();
    }

    @Test
    @DisplayName("튜터가 수업 시간을 생성할 수 있다")
    void createLesson_Success() {
        // given
        given(lessonService.createLesson(createRequest))
                .willReturn(lesson);

        // when
        ResponseEntity<LessonCreateResponse> response =
                tutorLessonController.createLesson(createRequest);

        // then
        LessonCreateResponse actual = response.getBody();
        assertThat(actual.getId()).isEqualTo(createResponse.getId());
        assertThat(actual.getStartTime()).isEqualTo(createResponse.getStartTime());
        assertThat(actual.getDuration()).isEqualTo(createResponse.getDuration());
        then(lessonService).should().createLesson(createRequest);
    }

    @Test
    @DisplayName("튜터가 수업을 삭제할 수 있다")
    void deleteLesson_Success() {
        // given
        willDoNothing()
                .given(lessonService)
                .deleteLesson(tutorId, lessonId);

        // when
        ResponseEntity<Void> response = tutorLessonController.deleteLesson(deleteRequest);

        // then
        then(lessonService).should().deleteLesson(tutorId, lessonId);
    }
}
