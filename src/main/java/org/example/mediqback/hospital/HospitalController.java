package org.example.mediqback.hospital;

import lombok.RequiredArgsConstructor;
import org.example.mediqback.hospital.model.HospitalDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("/hospital")
@RequiredArgsConstructor
@RestController
public class HospitalController {
    private final HospitalService hospitalService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody HospitalDto.SignupReq dto) {
        HospitalDto.SignupRes result = hospitalService.signup(dto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody HospitalDto.LoginReq dto) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword(), null);

        Authentication authentication = authenticationManager.authenticate(token);


        HospitalDto.LoginRes result = hospitalService.login(dto);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/list")
    public ResponseEntity<List<HospitalDto.SearchRes>> getHospitalList() {
        List<HospitalDto.SearchRes> list = hospitalService.getRegisteredHospitals();
        return ResponseEntity.ok(list);
    }

}
