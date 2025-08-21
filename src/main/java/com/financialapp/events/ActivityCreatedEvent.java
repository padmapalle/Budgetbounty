package com.financialapp.events;

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 2ac909d (Initial commit: Eclipse rewards system project)
import org.springframework.context.ApplicationEvent;

import com.financialapp.entity.FinancialActivity;

<<<<<<< HEAD
=======
import com.financialapp.model.FinancialActivity;
import org.springframework.context.ApplicationEvent;

>>>>>>> 3212540b9f8b79d2607519db820b5cd72cd061e4
=======
>>>>>>> 2ac909d (Initial commit: Eclipse rewards system project)
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
