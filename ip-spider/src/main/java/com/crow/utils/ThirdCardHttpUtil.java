/**
 * Copyright (c) HANGZHOU ZHICALL TECHNOLOGY Co., LTD.
 * Room 1401, Jiliang Building,Wensan Rd, Hangzhou
 * All rights reserved.
 * <p>
 * "[Description of code or deliverable as appropriate] is the copyrighted,
 * proprietary property of HANGZHOU ZHICALL TECHNOLOGY Co., LTD. and its
 * subsidiaries and affiliates which retain all right and title."
 * <p>
 * Revision History
 * <p>
 * Date            Programmer              Notes
 * ---------    ---------------------  --------------------------------------------
 * 2015-12-25	    Zhao Lijun              Initial
 */
package com.crow.utils;


import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.Map.Entry;

public class ThirdCardHttpUtil {

    private static HttpClient httpClient = null;

    private static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();

    private static final Logger logger = LoggerFactory.getLogger(ThirdCardHttpUtil.class);

    /**
     * 发送post请求
     * @param url
     * @param params
     */
    public static String postRequest(String url, Map<String, Object> params, String encodeType) {
        String response = null;

        if (httpClient == null) {
            httpClient = initHttpClient(encodeType);
        }

        PostMethod method = new PostMethod(url);
        NameValuePair[] postParams = new NameValuePair[params.size()];
        int index = 0;
        for (Entry<String, Object> entry : params.entrySet()) {
            postParams[index] = new NameValuePair(entry.getKey(), (String) entry.getValue());
            index++;
        }

        method.setRequestBody(postParams);
        try {
            int status = httpClient.executeMethod(method);

            logger.debug("[HttpUtil.postRequest]:http post execute status '{}'", status);
            if (status != HttpStatus.SC_OK) {
                logger.error("[HttpUtil.postRequest]:Method failed: " + method.getStatusLine());
                method.abort();
                return null;
            }

            response = readResponse(method.getResponseBodyAsStream(), encodeType);
        } catch (ConnectTimeoutException e) {
            logger.warn("[HttpUtil.postRequest]:timeout to connect target url:{}", url);
            method.abort();
        } catch (SocketTimeoutException e) {
            logger.warn("[HttpUtil.postRequest]:socket timeout to read the response");
            method.abort();
        } catch (HttpException e) {
            logger.warn("[HttpUtil.postRequest]:connection exception:", e);
            method.abort();
        } catch (IOException e) {
            logger.warn("[HttpUtil.postRequest]:io exception:", e);
            method.abort();
        } finally {
            method.releaseConnection();
            connectionManager.closeIdleConnections(0);
        }

        return response;
    }

    public static String getRequest(String url, Map<String, String> headParams, String encodeType) {
        String response = null;

        if(httpClient == null)
        httpClient = initHttpClient(encodeType);

        GetMethod method = new GetMethod(url);
        if (headParams != null) {
            for (Entry<String, String> entry : headParams.entrySet()) {
                method.addRequestHeader(entry.getKey(), entry.getValue());
            }
        }

        try {
            int status = httpClient.executeMethod(method);

            logger.debug("[HttpUtil.getRequest]:http get execute status '{}'", status);
            if (status != HttpStatus.SC_OK) {
                logger.error("[HttpUtil.getRequest]:Method failed: " + method.getStatusLine());
                method.abort();
                return null;
            }

            response = readResponse(method.getResponseBodyAsStream(), encodeType);
        } catch (ConnectTimeoutException e) {
            logger.warn("[HttpUtil.getRequest]:timeout to connect target url:{}", url);
            method.abort();
        } catch (SocketTimeoutException e) {
            logger.warn("[HttpUtil.getRequest]:socket timeout to read the response");
            method.abort();
        } catch (HttpException e) {
            logger.warn("[HttpUtil.getRequest]:connection exception:", e);
            method.abort();
        } catch (IOException e) {
            logger.warn("[HttpUtil.getRequest]:io exception:", e);
            method.abort();
        } finally {
            method.releaseConnection();
            connectionManager.closeIdleConnections(0);
        }

        return response;
    }

    /*
     * read response from the stream
     */
    private static String readResponse(InputStream is, String encodeType) {
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, encodeType));
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            logger.warn("io exception:", e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                logger.warn("fail to close the input stream:", e);
            }
        }

        return sb.toString();
    }


    private static HttpClient initHttpClient( String encodeType) {
        HttpClient httpClient = new HttpClient(connectionManager);
        httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encodeType);
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(10 * 1000);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(10 * 1000);
        httpClient.getHttpConnectionManager().getParams().setDefaultMaxConnectionsPerHost(100);
        httpClient.getHttpConnectionManager().getParams().setStaleCheckingEnabled(true);
        return httpClient;
    }
}
