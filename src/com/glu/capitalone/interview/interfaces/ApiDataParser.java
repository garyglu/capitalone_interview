package com.glu.capitalone.interview.interfaces;

import com.glu.capitalone.interview.data.*;
import com.glu.capitalone.interview.utils.*;
import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.entity.*;
import org.apache.http.impl.client.*;
import org.apache.http.message.*;
import org.apache.http.util.*;

import java.text.*;
import java.text.ParseException;
import java.util.*;

public abstract class ApiDataParser implements ApiDataFetcher {

    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private static final String BASE_API_URL =
        "https://2016.api.levelmoney.com/api/v2/core";

    public String getAPIUrl() {
        return BASE_API_URL + "/" + getApiPath();
    }

    @Override
    public List<Transaction> getTransactionData(boolean includingTestOnlyData) {
        try {
            String rawJasonData = getApiData();
            return parseJasonString(rawJasonData, includingTestOnlyData);
        } catch (Exception ex) {
            OutputUtils.println("Failed to get Transactions data from API");
            return Collections.emptyList();
        }
    }

    public String getApiData() throws Exception {
        HttpClient httpClient = HttpClientBuilder.create().build();
        System.out.println(getAPIUrl());
        HttpPost httpPost = new HttpPost(getAPIUrl());
        httpPost.addHeader(new BasicHeader("Accept", "application/json"));
        httpPost.addHeader(new BasicHeader("Content-Type", "application/json"));
        httpPost.setEntity(new StringEntity(getEntityContent()));

        HttpResponse response = httpClient.execute(httpPost);
        return EntityUtils.toString(response.getEntity());
    }

    public static Date toDate(String dateString) {
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    protected abstract List<Transaction> parseJasonString(String rawJason, boolean includingTestOnlyData) throws Exception;
    protected abstract String getEntityContent();
    protected abstract String getApiPath();
}
