package com.glu.capitalone.interview.parser;

import com.glu.capitalone.interview.data.*;
import com.glu.capitalone.interview.interfaces.*;
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
    public List<Transaction> getTransactionData() throws Exception {
        String rawJasonData = getApiData();
        return parseJasonString(rawJasonData);
    }

    public String getApiData() throws Exception {
        HttpClient httpClient = HttpClientBuilder.create().build();
        System.out.println(getAPIUrl());
        HttpPost httpPost = new HttpPost(getAPIUrl());
        httpPost.addHeader(new BasicHeader("Accept", "application/json"));
        httpPost.addHeader(new BasicHeader("Content-Type", "application/json"));
        httpPost.setEntity(new StringEntity(getEntityContent()));

        HttpResponse response = httpClient.execute(httpPost);

        //status 200: OK
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new Exception("Failed to get API Data. Response status code = " + response.getStatusLine().getStatusCode());
        }
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

    protected abstract List<Transaction> parseJasonString(String rawJason) throws Exception;
    protected abstract String getEntityContent();
    protected abstract String getApiPath();
}
