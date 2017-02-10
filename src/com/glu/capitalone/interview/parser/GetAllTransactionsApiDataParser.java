package com.glu.capitalone.interview.parser;

import com.glu.capitalone.interview.*;
import com.glu.capitalone.interview.data.*;
import com.glu.capitalone.interview.interfaces.*;
import org.json.*;

import java.util.*;

public class GetAllTransactionsApiDataParser extends ApiDataParser {

    private static final String ALL_TRANSACTION_HTTP_POST_ENTITY_STRING =
        "{\"args\": {\"uid\":  1110590645, \"token\":  \"450D422FB51D21E43E2D2350B4088C10\", " +
            "\"api-token\":  \"AppTokenForInterview\", \"json-strict-mode\": false, " +
            "\"json-verbose-response\": false}};";


    @Override
    protected String getEntityContent() {
        return ALL_TRANSACTION_HTTP_POST_ENTITY_STRING;
    }

    @Override
    protected String getApiPath() {
        return ApiServiceEnum.getAllTransactions.getApiPath();
    }


    /**
     * Description: parse jason string from getAllTransactionApi, including all TEST-ONLY data as well.
     * @param rawJason Jason string
     * @return transaction list
     * @throws Exception
     */
    @Override
    protected List<Transaction> parseJasonString(String rawJason)
        throws Exception {
        List<Transaction> transactions = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(rawJason);
        JSONArray array = jsonObject.getJSONArray("transactions");

        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            if (obj != null) {
                transactions.add(
                    new Transaction(
                        obj.getString("transaction-id"),
                        obj.getString("account-id"),
                        obj.getString("raw-merchant"),
                        obj.getString("merchant"), obj.getBoolean("is-pending"),
                        toDate(obj.getString("transaction-time")),
                        obj.getBigInteger("amount"),
                        obj.getLong("aggregation-time"),
                        obj.getLong("clear-date"),
                        obj.getString("categorization"),
                        obj.has("memo-only-for-testing")));
            }
        }
        return transactions;
    }
}
