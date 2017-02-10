package com.glu.capitalone.interview.handler;

import com.glu.capitalone.interview.config.*;
import com.glu.capitalone.interview.data.*;
import com.glu.capitalone.interview.filter.TransactionFilter;
import com.glu.capitalone.interview.interfaces.*;

import java.util.*;

public class TransactionDataHandler {

    public String handle(boolean includingTestOnlyData, boolean ignoreDonuts,
        boolean crystalBall, boolean ignoreCCPayment) throws Exception{
        Config config = createNewConfigInstance();
        config.config(includingTestOnlyData, ignoreDonuts, crystalBall, ignoreCCPayment);

        Set<Transaction> allTransactions = new HashSet<>();
        for (ApiDataFetcher f : config.getDataFetcherList()) {
            allTransactions.addAll(f.getTransactionData());
        }

        for (TransactionFilter f : config.getFilterList()) {
            allTransactions = new HashSet<>(f.filter(allTransactions));
        }

        StringBuilder sb = new StringBuilder();
        for (OutputResult o : config.getOutputList()) {
            sb.append(o.output(allTransactions, crystalBall, ignoreCCPayment));
        }

        return sb.toString();
    }

    protected Config createNewConfigInstance() {
        return new DefaultConfig();
    }
}
