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

    public User toModel(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
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

    public void toModel(RollDTO rollDTO, Roll roll) {
        modelMapper.map(rollDTO, roll);
    }

    public Preset toModel(PresetDTO presetDTO) {
        return modelMapper.map(presetDTO, Preset.class);
    }

    public PresetDTO toDto(Preset preset) {
        return modelMapper.map(preset, PresetDTO.class);
    }

    public void toModel(PresetDTO presetDTO, Preset preset) {
        modelMapper.map(presetDTO, preset);
    }

}
