package com.expensesplitter.service;

import com.expensesplitter.model.*;
import java.util.*;

/**
 * SplitService: calculate balances and simplify transactions.
 */
public interface SplitService {
    Map<Person, Double> calculateBalances(List<Expense> expenses, List<Person> members);
    List<String> simplifyTransactions(Map<Person, Double> balances);
}
