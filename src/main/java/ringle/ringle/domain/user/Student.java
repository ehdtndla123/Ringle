package ringle.ringle.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ringle.ringle.domain.lesson.Lesson;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Student extends User{

    @OneToMany(mappedBy = "student")
    private List<Lesson> lessons = new ArrayList<>();
}
