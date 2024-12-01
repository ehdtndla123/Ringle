package ringle.ringle.dto.user;

import lombok.Builder;
import lombok.Getter;
import ringle.ringle.domain.user.Tutor;

@Getter
@Builder
public class TutorResponse {

    private Long id;
    private String name;
    private String email;

    public static TutorResponse from(Tutor tutor) {
        return TutorResponse.builder()
                .id(tutor.getId())
                .name(tutor.getName())
                .email(tutor.getEmail())
                .build();
    }
}
