package com.financialapp.events;

import com.financialapp.model.FinancialGoal;
import org.springframework.context.ApplicationEvent;

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
