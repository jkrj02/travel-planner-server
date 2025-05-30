package com.example.travelplanner.repository;

import com.example.travelplanner.entity.Budget;
import com.example.travelplanner.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    
    List<Expense> findByBudget(Budget budget);
    
    List<Expense> findByBudgetOrderByExpenseDateDesc(Budget budget);
    
    List<Expense> findByBudgetAndCategory(Budget budget, Expense.ExpenseCategory category);
} 