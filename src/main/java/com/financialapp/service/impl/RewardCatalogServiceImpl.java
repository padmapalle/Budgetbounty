package com.financialapp.service.impl;

import com.financialapp.dto.RewardCatalogDTO;
<<<<<<< HEAD
<<<<<<< HEAD
import com.financialapp.entity.Partner;
import com.financialapp.entity.RewardCatalog;
=======
import com.financialapp.model.Partner;
import com.financialapp.model.RewardCatalog;
>>>>>>> 3212540b9f8b79d2607519db820b5cd72cd061e4
=======
import com.financialapp.entity.Partner;
import com.financialapp.entity.RewardCatalog;
>>>>>>> 2ac909d (Initial commit: Eclipse rewards system project)
import com.financialapp.repository.PartnerRepository;
import com.financialapp.repository.RewardCatalogRepository;
import com.financialapp.service.RewardCatalogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RewardCatalogServiceImpl implements RewardCatalogService {

    @Autowired
    private RewardCatalogRepository catalogRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RewardCatalogDTO createRewardCatalog(RewardCatalogDTO dto) {
        RewardCatalog catalog = modelMapper.map(dto, RewardCatalog.class);
        catalog.setPartner(partnerRepository.findById(dto.getPartnerId())
                .orElseThrow(() -> new RuntimeException("Partner not found")));
        return modelMapper.map(catalogRepository.save(catalog), RewardCatalogDTO.class);
    }

    @Override
    public RewardCatalogDTO getRewardCatalogById(Integer id) {
        RewardCatalog catalog = catalogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catalog item not found"));
        return modelMapper.map(catalog, RewardCatalogDTO.class);
    }

    @Override
    public List<RewardCatalogDTO> getAllRewardCatalogs() {
        return catalogRepository.findAll()
                .stream()
                .map(catalog -> modelMapper.map(catalog, RewardCatalogDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RewardCatalogDTO updateRewardCatalog(Integer id, RewardCatalogDTO dto) {
        RewardCatalog existing = catalogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catalog item not found"));

        existing.setName(dto.getName());
        existing.setRewardType(dto.getRewardType());
        existing.setPointsRequired(dto.getPointsRequired());
        existing.setConfiguration(dto.getConfiguration());
        existing.setActive(dto.isActive());

        if (dto.getPartnerId() != null) {
            Partner partner = partnerRepository.findById(dto.getPartnerId())
                    .orElseThrow(() -> new RuntimeException("Partner not found"));
            existing.setPartner(partner);
        }

        return modelMapper.map(catalogRepository.save(existing), RewardCatalogDTO.class);
    }

    @Override
    public void deleteRewardCatalog(Integer id) {
        catalogRepository.deleteById(id);
    }
}
