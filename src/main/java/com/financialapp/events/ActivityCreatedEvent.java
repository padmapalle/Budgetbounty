package com.financialapp.events;

<<<<<<< HEAD
import org.springframework.context.ApplicationEvent;

import com.financialapp.entity.FinancialActivity;

=======
import com.financialapp.model.FinancialActivity;
import org.springframework.context.ApplicationEvent;

>>>>>>> 3212540b9f8b79d2607519db820b5cd72cd061e4
public class ActivityCreatedEvent extends ApplicationEvent {
    private final FinancialActivity activity;

    public ActivityCreatedEvent(Object source, FinancialActivity activity) {
        super(source);
        this.activity = activity;
    }

    public FinancialActivity getActivity() {
        return activity;
    }
}
