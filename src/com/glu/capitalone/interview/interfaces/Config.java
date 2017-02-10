package com.glu.capitalone.interview.interfaces;

import com.glu.capitalone.interview.filter.*;

import java.util.*;

/**
 * Created by glu on 2/10/2017.
 */
public interface Config {
    void config(boolean includingTestOnlyData, boolean ignoreDonuts, boolean crystalBall, boolean ignoreCCPayment);
    List<ApiDataFetcher> getDataFetcherList();
    List<TransactionFilter> getFilterList();
    List<OutputResult> getOutputList();
}
