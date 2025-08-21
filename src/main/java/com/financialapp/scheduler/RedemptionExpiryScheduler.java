package com.financialapp.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
<<<<<<< HEAD
import com.financialapp.entity.Redemption;
import com.financialapp.entity.RedemptionStatus;
=======
import com.financialapp.model.Redemption;
import com.financialapp.model.RedemptionStatus;
>>>>>>> 3212540b9f8b79d2607519db820b5cd72cd061e4
=======
import com.financialapp.entity.Redemption;
import com.financialapp.entity.RedemptionStatus;
>>>>>>> 2ac909d (Initial commit: Eclipse rewards system project)
import com.financialapp.repository.RedemptionRepository;
import com.financialapp.service.RedemptionService;

import jakarta.transaction.Transactional;

@Service
public class RedemptionExpiryScheduler {

    @Autowired
    private RedemptionRepository redemptionRepository;

    @Transactional
    @Scheduled(cron = "0 0 * * * *") // runs every hour
    public void expireRedemptions() {
        // Find all redemption that are SUCCESS and have expiryDate in the past
        List<Redemption> expiredList = redemptionRepository.findAllByStatusAndExpiryDateBefore(
                RedemptionStatus.SUCCESS, LocalDateTime.now());

        for (Redemption r : expiredList) {
            r.setStatus(RedemptionStatus.EXPIRED);
            // No refund logic here per rules
            redemptionRepository.save(r);
        }
    }
}
