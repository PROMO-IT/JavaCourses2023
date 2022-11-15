package ru.promo.springapp.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.promo.springapp.dto.RegisterDto;
import ru.promo.springapp.model.Profile;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    Profile fromRegisterDto(RegisterDto dto);
}
