package com.financialapp.events;

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 2ac909d (Initial commit: Eclipse rewards system project)
import org.springframework.context.ApplicationEvent;

import com.financialapp.entity.FinancialGoal;

<<<<<<< HEAD
=======
import com.financialapp.model.FinancialGoal;
import org.springframework.context.ApplicationEvent;

>>>>>>> 3212540b9f8b79d2607519db820b5cd72cd061e4
=======
>>>>>>> 2ac909d (Initial commit: Eclipse rewards system project)
public class GoalAchievedEvent extends ApplicationEvent {

    private final FinancialGoal goal;

    public GoalAchievedEvent(Object source, FinancialGoal goal) {
        super(source);
        this.goal = goal;
    }

    public FinancialGoal getGoal() {
        return goal;
    }
}
