package com.glu.capitalone.interview.output;

import com.glu.capitalone.interview.data.*;
import com.glu.capitalone.interview.interfaces.*;

import java.math.*;
import java.text.*;
import java.util.*;

public class TransactionOutput implements OutputResult {
    private static final String AVERAGE_KEY = "average";
    public static final SimpleDateFormat monthFormat =
        new SimpleDateFormat("yyyy-MM");

    @Override
    public String output(Set<Transaction> transactions, boolean crystalBall,
        boolean ignoreCCPayment) {
        try {
            Collection<MonthSummary> results = normalizeResult(transactions, crystalBall, ignoreCCPayment);
            return outputResult(results);
        } catch (Exception ex) {
            return "Failed to com.glu.capitalone.interview.output transaction result";
        }
    }

    protected Collection<MonthSummary> normalizeResult(Set<Transaction> set,
        boolean crystalBall, boolean ignoreCCPayment) throws Exception {
        Map<String, MonthSummary> resultMap = new HashMap<>();
        for (Transaction trans : set) {
            if ((crystalBall || !trans.isPending()) &&
                (!ignoreCCPayment || !trans.isCCPyament())) {
                String key = monthFormat.format(trans.getTransactionTime());
                MonthSummary summary = resultMap.get(key);
                if (summary == null) {
                    summary = new MonthSummary(key);
                    resultMap.put(key, summary);
                }
                summary.addTransaction(trans.getAmount());
            }
        }

        BigInteger months = BigInteger.valueOf(resultMap.size());

        BigInteger averageSpend = new BigInteger("0");
        BigInteger averageIncome = new BigInteger("0");
        for (MonthSummary sum : resultMap.values()) {
            averageSpend = averageSpend.add(sum.getSpend());
            averageIncome = averageIncome.add(sum.getIncome());
        }
        resultMap.put(
            AVERAGE_KEY, new MonthSummary(
            AVERAGE_KEY, averageSpend.divide(months),
            averageIncome.divide(months)));
        return resultMap.values();
    }

    protected String outputResult(Collection<MonthSummary> col)
        throws Exception {
        List<MonthSummary> summaryList = new ArrayList<>(col);
        Collections.sort(
            summaryList, new Comparator<MonthSummary>() {
                @Override
                public int compare(MonthSummary o1, MonthSummary o2) {
                    if (o1.getId().equals(AVERAGE_KEY)) {
                        return 1;
                    }
                    if (o2.getId().equals(AVERAGE_KEY)) {
                        return -1;
                    }
                    return o1.getId().compareTo(o2.getId());
                }
            });

        StringBuilder sb = new StringBuilder("{\n");
        for (MonthSummary sum : summaryList) {
            sb.append(sum.toString()).append("\n");
        }
        sb.append("}\n");
        return sb.toString();
    }

    private class MonthSummary {
        private final String id;
        private BigInteger spend;
        private BigInteger income;

        MonthSummary(String id) {
            this(id, new BigInteger("0"), new BigInteger("0"));
        }

        MonthSummary(String id, BigInteger spend, BigInteger income) {
            this.id = id;
            this.spend = spend;
            this.income = income;
        }

        public BigInteger getSpend() {
            return spend;
        }

        public BigInteger getIncome() {
            return income;
        }

        public void addTransaction(BigInteger amount) {
            if (amount.longValue() > 0) {
                this.income = this.income.add(amount);
            } else {
                this.spend = this.spend.add(amount.negate());
            }
        }

        public String getId() {
            return id;
        }

        public String toDollor(BigInteger value) {
            return String.format("%1$.2f", value.doubleValue()/10000.00);
        }


        public String getSpendInDollar() {
            return toDollor(this.spend);
        }

        public String getIncomeInDollar() {
            return toDollor(this.income);
        }

        @Override
        public String toString() {
            return String.format(
                "\"%s\":{\"spent\":\"$%s\",\"income\":\"$%s\"},", getId(),
                getSpendInDollar(), getIncomeInDollar());
        }
    }

}
