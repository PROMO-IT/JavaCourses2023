package ru.promo.springapp.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.promo.springapp.dto.LoginDto;
import ru.promo.springapp.dto.RegisterDto;
import ru.promo.springapp.mapper.ProfileMapper;
import ru.promo.springapp.model.Profile;
import ru.promo.springapp.service.ProfileService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileMapper profileMapper;

    @PostMapping("/register")
    public void register(@Valid @RequestBody RegisterDto registerDto) {
        Profile profile = profileMapper.fromRegisterDto(registerDto);
        profileService.register(profile);
    }

    @PostMapping("/auth")
    public String auth(@RequestBody LoginDto dto) {
        return profileService.auth(dto.getLogin(), dto.getPassword());
    }

    @GetMapping("/me")
    public Profile me(@AuthenticationPrincipal Profile profile) {
//        return profile;
        return (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
