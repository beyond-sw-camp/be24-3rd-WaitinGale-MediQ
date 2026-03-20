package org.example.mediqback.Surveyform;

import lombok.RequiredArgsConstructor;
import org.example.mediqback.Surveyform.model.Surveyform;
import org.example.mediqback.Surveyform.model.SurveyformDto;
import org.example.mediqback.user.UserRepository;
import org.example.mediqback.user.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SurveyformService {

    private final SurveyformRepository surveyformRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createSurvey(Long userIdx, SurveyformDto.CreateReq dto) {
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Surveyform surveyform = dto.toEntity();
        surveyform.setUser(user);

        surveyformRepository.save(surveyform);
    }
}