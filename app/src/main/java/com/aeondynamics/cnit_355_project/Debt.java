package com.aeondynamics.cnit_355_project;

public class Debt {
    private String debt_name;
    private String debt_description;
    private String debt_monthly_contribution;
    private String debt_payoff_months;

    public Debt(String debt_name, String debt_description,
                String debt_monthly_contribution, String debt_payoff_months) {
        this.debt_name = debt_name;
        this.debt_description = debt_description;
        this.debt_monthly_contribution = String.format("MonthlyContribution: $%s",
                debt_monthly_contribution);
        this.debt_payoff_months = String.format("Payoff Time: %s Months",
                debt_payoff_months);
    }

    public String getDebtName() {
        return debt_name;
    }

    public String getDebtDescription() {
        return debt_description;
    }

    public String getDebtMonthlyContribution() {
        return debt_monthly_contribution;
    }

    public String getDebtPayoffMonths() {
        return debt_payoff_months;
    }
}
