package ringle.ringle.application.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ringle.ringle.common.exception.ErrorCode;
import ringle.ringle.common.exception.TutorException;
import ringle.ringle.dao.TutorRepository;
import ringle.ringle.domain.user.Tutor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TutorService {
    private final TutorRepository tutorRepository;

    public Tutor getTutor(Long tutorId) {
        return tutorRepository.findById(tutorId)
                .orElseThrow(() -> new TutorException(ErrorCode.TUTOR_NOT_FOUND));
    }

    @Transactional
    public Long createTutor(String name, String email) {
        Tutor tutor = Tutor.builder()
                .name(name)
                .email(email)
                .build();

        Tutor savedTutor = tutorRepository.save(tutor);
        return savedTutor.getId();
    }
}
