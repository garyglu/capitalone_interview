package com.glu.capitalone.interview.interfaces;

import com.glu.capitalone.interview.data.*;

import java.util.*;

/**
 * Created by glu on 2/9/2017.
 */
public interface OutputResult {
    String output(Set<Transaction> transaction, boolean crystalBall, boolean ignoreCCPayment);
}
