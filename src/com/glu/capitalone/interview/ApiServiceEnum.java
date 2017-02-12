package com.glu.capitalone.interview;

import com.glu.capitalone.interview.parser.*;
import com.glu.capitalone.interview.parser.ApiDataParser;

public enum ApiServiceEnum {
    getAllTransactions              (1, "get-all-transactions",            new GetAllTransactionsApiDataParser()),
    getProjectedTransactionsForMonth(2, "projected-transactions-for-month",new GetProjectedTransactionsForMonthParser()),

    ;
    private int id;
    private String apiPath;
    private ApiDataParser parser;

    private ApiServiceEnum(int index, String apiPath, ApiDataParser parser) {
        this.id = index;
        this.apiPath = apiPath;
        this.parser = parser;
    }

    public int getId() {
        return id;
    }

    public String getApiPath() {
        return apiPath;
    }

    public ApiDataParser getParser() {
        return parser;
    }

    public static ApiServiceEnum getApiEnum(int index) {
        for (ApiServiceEnum apiServiceEnum : ApiServiceEnum.values()) {
            if (apiServiceEnum.getId() == index) {
                return apiServiceEnum;
            }
        }
        return null;
    }
}
