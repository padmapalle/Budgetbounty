package com.financialapp.events;

<<<<<<< HEAD
import org.springframework.context.ApplicationEvent;

import com.financialapp.entity.FinancialGoal;

=======
import com.financialapp.model.FinancialGoal;
import org.springframework.context.ApplicationEvent;

>>>>>>> 3212540b9f8b79d2607519db820b5cd72cd061e4
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
