package ringle.ringle.domain.lesson;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ringle.ringle.domain.user.Student;
import ringle.ringle.domain.user.Tutor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tutor tutor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    private LocalDateTime startTime;

    private int duration;

    @Enumerated(EnumType.STRING)
    private LessonStatus status;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void softDelete() {
        this.status = LessonStatus.CANCELLED;
    }
}
