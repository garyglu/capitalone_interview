package com.glu.capitalone.interview.interfaces;

import com.glu.capitalone.interview.data.*;

import java.util.*;

public interface ApiDataFetcher {
    public List<Transaction> getTransactionData(boolean includingTestOnlyData) throws Exception;
}
