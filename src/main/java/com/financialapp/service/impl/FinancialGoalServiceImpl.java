package com.financialapp.service.impl;

import com.financialapp.dto.FinancialGoalDTO;
import com.financialapp.model.FinancialGoal;
import com.financialapp.model.User;
import com.financialapp.repository.FinancialGoalRepository;
import com.financialapp.repository.UserRepository;
import com.financialapp.service.FinancialGoalService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinancialGoalServiceImpl implements FinancialGoalService {

    @Autowired
    private FinancialGoalRepository goalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FinancialGoalDTO createGoal(FinancialGoalDTO dto) {
        FinancialGoal goal = modelMapper.map(dto, FinancialGoal.class);
        User user = userRepository.findById(dto.getUserId()).orElseThrow();
        goal.setUser(user);
        return modelMapper.map(goalRepository.save(goal), FinancialGoalDTO.class);
    }

    @Override
    public FinancialGoalDTO getGoalById(Integer id) {
        FinancialGoal goal = goalRepository.findById(id).orElseThrow();
        FinancialGoalDTO dto = modelMapper.map(goal, FinancialGoalDTO.class);
        dto.setUserId(goal.getUser().getUserId());
        return dto;
    }

    @Override
    public List<FinancialGoalDTO> getAllGoals() {
        return goalRepository.findAll().stream()
            .map(goal -> {
                FinancialGoalDTO dto = modelMapper.map(goal, FinancialGoalDTO.class);
                dto.setUserId(goal.getUser().getUserId());
                return dto;
            }).collect(Collectors.toList());
    }

    @Override
    public FinancialGoalDTO updateGoal(Integer id, FinancialGoalDTO dto) {
        FinancialGoal existing = goalRepository.findById(id).orElseThrow();
        existing.setDomain(dto.getDomain());
        existing.setCustomAttrs(dto.getCustomAttrs());
        existing.setAchieved(dto.isAchieved());
        return modelMapper.map(goalRepository.save(existing), FinancialGoalDTO.class);
    }

    @Override
    public void deleteGoal(Integer id) {
        goalRepository.deleteById(id);
    }
}
