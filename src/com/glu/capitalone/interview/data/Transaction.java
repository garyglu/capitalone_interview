package com.glu.capitalone.interview.data;

import com.glu.capitalone.interview.interfaces.*;

import java.math.*;
import java.util.*;

public class Transaction {

    private final String transactionId;
    private final String previousTransactionId;
    private final String accountId;
    private final String rawMerchant;
    private final String merchant;
    private final boolean isPending;
    private final Date transactionTime;
    private final BigInteger amount;
    private final Date aggregationTime;
    private final Date clearDate;
    private final String categorization;
    private boolean isCCPyament;


    public Transaction(String transactionId, String accountId,
        String rawMerchant, String merchant, boolean isPending,
        Date transactionTime, BigInteger amount, long aggregationTime,
        long clearDate, String categorization) {
        this(transactionId, "", accountId, rawMerchant, merchant, isPending, transactionTime, amount, aggregationTime, clearDate, categorization);
    }

    public Transaction(String transactionId, String previousTransactionId,
        String accountId, String rawMerchant, String merchant,
        boolean isPending, Date transactionTime, BigInteger amount,
        long aggregationTime, long clearDate, String categorization) {
        this.transactionId = transactionId;
        this.previousTransactionId = previousTransactionId;
        this.accountId = accountId;
        this.rawMerchant = rawMerchant;
        this.merchant = merchant;
        this.isPending = isPending;
        this.transactionTime = transactionTime;
        this.amount = amount;
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(aggregationTime);
        this.aggregationTime = cal1.getTime();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(clearDate);
        this.clearDate = cal2.getTime();
        this.categorization = categorization;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public String getCategorization() {
        return categorization;
    }


    public boolean isPending() {
        return isPending;
    }

    public String getMerchant() {
        return merchant;
    }

    public String getRawMerchant() {
        return rawMerchant;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public String toDateTimeString(Date date) {
        return date == null ? "" : ApiDataParser.simpleDateFormat.format(date);
    }

    public boolean isCCPyament() {
        return isCCPyament;
    }

    public void setCCPyament(boolean isCCPyament) {
        this.isCCPyament = isCCPyament;
    }

    public String toDollar(BigInteger amount) {
        return "$" + ((double)amount.longValueExact()/10000);
    }

    @Override
    public String toString() {
        return String.format(
            "[transactionId:%s; previousTransactionId:%s; accountId:%s; rawMerchant:%s; merchant:%s; isPending:%s; transactionTime:%s; amount:%s aggregationTime:%s; clearDate:%s; categorization:%s]",
            transactionId, previousTransactionId, accountId, rawMerchant,merchant, isPending, toDateTimeString(transactionTime),toDollar(amount), toDateTimeString(aggregationTime),toDateTimeString(clearDate), categorization);
    }
}
