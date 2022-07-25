package com.ryota.system.config;

/**
 * @Author Ryota
 * @create 2022/6/4 10:55
 */

import com.alibaba.fastjson.JSONObject;
import com.ryota.system.entity.SystemJobLog;
import com.ryota.system.entity.SystemJobParam;
import com.ryota.system.mapper.SystemJobLogMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 调用的是接口
 */
@Component
public class SendSignHttpsClient {

    @Autowired
    private  SystemJobLogMapper systemJobLogMapper;

    /**
     * post请求以及参数是json
     *
     * @param url
     * @param jsonParams
     * @return
     */
//    public JSONObject doPostForJson(String url, String jsonParams,String jobId) {
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        JSONObject jsonObject = null;
//        HttpPost httpPost = new HttpPost(url);
//        RequestConfig requestConfig = RequestConfig.custom().
//                setConnectTimeout(180 * 1000).setConnectionRequestTimeout(180 * 1000)
//                .setSocketTimeout(180 * 1000).setRedirectsEnabled(true).build();
//        httpPost.setConfig(requestConfig);
//        httpPost.setHeader("Content-Type", "application/json");
//        try {
//            httpPost.setEntity(new StringEntity(jsonParams, ContentType.create("application/json", "utf-8")));
//            System.out.println("request parameters" + EntityUtils.toString(httpPost.getEntity()));
//            System.out.println("httpPost:" + httpPost);
//            HttpResponse response = httpClient.execute(httpPost);
//            if (response != null && response.getStatusLine().getStatusCode() == 200) {
//                String result = EntityUtils.toString(response.getEntity());
//                System.out.println("result:" + result);
//                jsonObject = JSONObject.parseObject(result);
//
//                SystemJobLog systemJobLog = new SystemJobLog();
//                systemJobLog.setExecTime(new Date());
//                systemJobLog.setResult((String) jsonObject.get("data"));
//                systemJobLog.setResultType((Integer) jsonObject.get("code"));
//                systemJobLog.setJobId(jobId);
//                systemJobLogMapper.insert(systemJobLog);
//
//                return jsonObject;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (null != httpClient) {
//                try {
//                    httpClient.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            return jsonObject;
//        }
//    }

    public JSONObject doPostForJson(String url, List<SystemJobParam> params, String jobId) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonObject = null;
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(180 * 1000).setConnectionRequestTimeout(180 * 1000)
                .setSocketTimeout(180 * 1000).setRedirectsEnabled(true).build();
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-Type", "application/json");
        try {
            //post请求传递requesbody参数
            JSONObject jo = new JSONObject();
            for (SystemJobParam param : params) {
                jo.put(param.getParamKey(), param.getParamKey());
                jo.put(param.getParamValue(), param.getParamValue());
            }
            httpPost.setEntity(new StringEntity(jo.toString(), "utf-8"));
            System.out.println("request parameters" + EntityUtils.toString(httpPost.getEntity()));
            System.out.println("httpPost:" + httpPost);
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(response.getEntity());
                System.out.println("result:" + result);
                jsonObject = JSONObject.parseObject(result);
                //存储返回结果
                SystemJobLog systemJobLog = new SystemJobLog();
                systemJobLog.setExecTime(new Date());
                systemJobLog.setResult(jsonObject.get("data").toString());
                if (((Integer) jsonObject.get("code") == 200)) {
                    systemJobLog.setResultType(1);
                } else {
                    systemJobLog.setResultType(2);
                }
                systemJobLog.setJobId(jobId);
                systemJobLogMapper.insert(systemJobLog);
                return jsonObject;
            }
        } catch (Exception e) {
            String message = e.getMessage();
            System.out.println(message);
            Throwable cause = e.getCause();
            System.out.println(cause);
            e.printStackTrace();
        } finally {
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return jsonObject;
        }
    }


}