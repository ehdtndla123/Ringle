package ringle.ringle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ringle.ringle.application.StudentLessonFacade;
import ringle.ringle.application.lesson.LessonService;
import ringle.ringle.domain.lesson.EnrollmentStatus;
import ringle.ringle.dto.lesson.AvailableTimeResponse;
import ringle.ringle.dto.lesson.LessonEnrollmentRequest;
import ringle.ringle.dto.lesson.LessonEnrollmentResponse;
import ringle.ringle.dto.user.TutorResponse;
import ringle.ringle.presentation.StudentLessonController;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class StudentLessonControllerTest {
    @Mock
    private LessonService lessonService;

    @Mock
    private StudentLessonFacade studentLessonFacade;

    @InjectMocks
    private StudentLessonController studentLessonController;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long studentId;
    private Long lessonId;
    private AvailableTimeResponse availableTime;
    private TutorResponse tutor;
    private LessonEnrollmentResponse enrollment;
    private LessonEnrollmentRequest enrollmentRequest;

    @BeforeEach
    void setUp() {
        startTime = LocalDateTime.of(2024, 12, 1, 14, 0);
        endTime = startTime.plusDays(7);

        studentId = 1L;
        lessonId = 1L;

        availableTime = AvailableTimeResponse.builder()
                .lessonId(lessonId)
                .startTime(startTime)
                .duration(30)
                .build();

        tutor = TutorResponse.builder()
                .id(1L)
                .name("장동수")
                .email("ehdtndla123@gmail.com")
                .build();

        enrollmentRequest = new LessonEnrollmentRequest(lessonId);
        enrollment = LessonEnrollmentResponse.builder()
                .id(1L)
                .studentId(studentId)
                .lessonId(lessonId)
                .startTime(startTime)
                .duration(30)
                .status(EnrollmentStatus.ENROLLED)
                .build();
    }

    @Test
    @DisplayName("기간과 수업 길이로 수업 가능한 시간대를 조회할 수 있다")
    void getAvailableLessons_Success() {
        // given
        given(lessonService.findAvailableLessonTime(startTime, endTime, 30))
                .willReturn(List.of(availableTime));

        // when
        ResponseEntity<List<AvailableTimeResponse>> response =
                studentLessonController.getAvailableLessons(startTime, endTime, 30);

        // then
        AvailableTimeResponse actual = response.getBody().get(0);
        assertThat(actual.getLessonId()).isEqualTo(availableTime.getLessonId());
        assertThat(actual.getStartTime()).isEqualTo(availableTime.getStartTime());
        assertThat(actual.getDuration()).isEqualTo(availableTime.getDuration());
        then(lessonService).should().findAvailableLessonTime(startTime, endTime, 30);
    }

    @Test
    @DisplayName("시간대와 수업 길이로 수업 가능한 튜터를 조회할 수 있다")
    void getAvailableTutors_Success() {
        // given
        given(lessonService.findAvailableTutors(startTime, 30))
                .willReturn(List.of(tutor));

        // when
        ResponseEntity<List<TutorResponse>> response =
                studentLessonController.getAvailableTutors(startTime, 30);

        // then
        TutorResponse actual = response.getBody().get(0);
        assertThat(actual.getId()).isEqualTo(tutor.getId());
        assertThat(actual.getName()).isEqualTo(tutor.getName());
        assertThat(actual.getEmail()).isEqualTo(tutor.getEmail());
        then(lessonService).should().findAvailableTutors(startTime, 30);
    }

    @Test
    @DisplayName("학생이 수업을 신청할 수 있다")
    void enrollLesson_Success() {
        // given
        given(studentLessonFacade.enrollLesson(studentId, enrollmentRequest))
                .willReturn(enrollment);

        // when
        ResponseEntity<LessonEnrollmentResponse> response =
                studentLessonController.enrollLesson(studentId, enrollmentRequest);

        // then
        LessonEnrollmentResponse actual = response.getBody();
        assertThat(actual.getId()).isEqualTo(enrollment.getId());
        assertThat(actual.getStudentId()).isEqualTo(enrollment.getStudentId());
        assertThat(actual.getLessonId()).isEqualTo(enrollment.getLessonId());
        assertThat(actual.getStartTime()).isEqualTo(enrollment.getStartTime());
        assertThat(actual.getDuration()).isEqualTo(enrollment.getDuration());
        assertThat(actual.getStatus()).isEqualTo(enrollment.getStatus());
        then(studentLessonFacade).should().enrollLesson(studentId, enrollmentRequest);
    }

    @Test
    @DisplayName("학생이 신청한 수업 목록을 조회할 수 있다")
    void getLessonEnrollments_Success() {
        // given
        given(studentLessonFacade.getLessonEnrollmentsByStudent(studentId))
                .willReturn(List.of(enrollment));

        // when
        ResponseEntity<List<LessonEnrollmentResponse>> response =
                studentLessonController.getLessonEnrollments(studentId);

        // then
        LessonEnrollmentResponse actual = response.getBody().get(0);
        assertThat(actual.getId()).isEqualTo(enrollment.getId());
        assertThat(actual.getStudentId()).isEqualTo(enrollment.getStudentId());
        assertThat(actual.getLessonId()).isEqualTo(enrollment.getLessonId());
        assertThat(actual.getStartTime()).isEqualTo(enrollment.getStartTime());
        assertThat(actual.getDuration()).isEqualTo(enrollment.getDuration());
        assertThat(actual.getStatus()).isEqualTo(enrollment.getStatus());
        then(studentLessonFacade).should().getLessonEnrollmentsByStudent(studentId);
    }
}