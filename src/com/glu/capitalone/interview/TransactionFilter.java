package com.glu.capitalone.interview;

import com.glu.capitalone.interview.data.*;

import java.util.*;

/**
 * Created by glu on 2/9/2017.
 */
public interface TransactionFilter {
    Set<Transaction> filter(Set<Transaction> list);
}
