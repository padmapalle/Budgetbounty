package com.financialapp.events;

import com.financialapp.model.FinancialActivity;
import org.springframework.context.ApplicationEvent;

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
