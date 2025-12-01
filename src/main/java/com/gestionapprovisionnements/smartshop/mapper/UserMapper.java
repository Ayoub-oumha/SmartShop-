package com.gestionapprovisionnements.smartshop.mapper;

import com.gestionapprovisionnements.smartshop.dto.User.Response.UserResponse;
import com.gestionapprovisionnements.smartshop.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toDto(User user) ;

}
