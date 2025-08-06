package com.financialapp.service.impl;

import com.financialapp.dto.FinancialActivityDTO;
import com.financialapp.model.FinancialActivity;
import com.financialapp.model.User;
import com.financialapp.repository.FinancialActivityRepository;
import com.financialapp.repository.UserRepository;
import com.financialapp.service.FinancialActivityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinancialActivityServiceImpl implements FinancialActivityService {

    @Autowired
    private FinancialActivityRepository activityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FinancialActivityDTO create(FinancialActivityDTO dto) {
        FinancialActivity activity = modelMapper.map(dto, FinancialActivity.class);
        User user = userRepository.findById(dto.getUserId()).orElseThrow();
        activity.setUser(user);
        return modelMapper.map(activityRepository.save(activity), FinancialActivityDTO.class);
    }

    @Override
    public FinancialActivityDTO getById(Integer id) {
        FinancialActivity activity = activityRepository.findById(id).orElseThrow();
        FinancialActivityDTO dto = modelMapper.map(activity, FinancialActivityDTO.class);
        dto.setUserId(activity.getUser().getUserId());
        return dto;
    }

    @Override
    public List<FinancialActivityDTO> getAll() {
        return activityRepository.findAll().stream().map(activity -> {
            FinancialActivityDTO dto = modelMapper.map(activity, FinancialActivityDTO.class);
            dto.setUserId(activity.getUser().getUserId());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public FinancialActivityDTO update(Integer id, FinancialActivityDTO dto) {
        FinancialActivity existing = activityRepository.findById(id).orElseThrow();
        existing.setActivityType(dto.getActivityType());

        existing.setActivityDate(dto.getActivityDate());
        return modelMapper.map(activityRepository.save(existing), FinancialActivityDTO.class);
    }

    @Override
    public void delete(Integer id) {
        activityRepository.deleteById(id);
    }
}
