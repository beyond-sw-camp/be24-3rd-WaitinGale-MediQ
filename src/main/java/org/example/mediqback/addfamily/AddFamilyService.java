package org.example.mediqback.addfamily;

import lombok.RequiredArgsConstructor;
import org.example.mediqback.addfamily.model.Family;
import org.example.mediqback.addfamily.model.FamilyDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddFamilyService {
    private final AddFamilyRepository addFamilyRepository;

    public FamilyDto.Res reg(FamilyDto.Req dto) {
        Family family = addFamilyRepository.save(dto.toEntity(dto));
        return FamilyDto.Res.from(family);
    }
}
