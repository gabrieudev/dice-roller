package com.api.diceRoller.service;

import com.api.diceRoller.dto.*;
import com.api.diceRoller.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MappingService {

    @Autowired
    private ModelMapper modelMapper;

    public User toModel(RegisterDTO registerDTO) {
        return modelMapper.map(registerDTO, User.class);
    }

    public UserDTO toDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public Roll toModel(RollDTO rollDTO) {
        return modelMapper.map(rollDTO, Roll.class);
    }

    public RollDTO toDto(Roll roll) {
        return modelMapper.map(roll, RollDTO.class);
    }

}
