package org.example.mediqback.hospital;

import lombok.RequiredArgsConstructor;
import org.example.mediqback.hospital.model.HospitalDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping("/hospital")
@RequiredArgsConstructor
@RestController
public class HospitalController {
    private final HospitalService hospitalService;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody HospitalDto.SignupReq dto) {
        HospitalDto.SignupRes result = hospitalService.signup(dto);
        return ResponseEntity.ok(result);
    }
}
