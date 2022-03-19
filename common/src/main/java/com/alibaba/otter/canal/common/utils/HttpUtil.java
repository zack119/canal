package com.alibaba.otter.canal.common.utils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liubin
 * @date 2022/03/17 09:23
 */
public class HttpUtil {

    private final static Logger logger                   = LoggerFactory.getLogger(HttpUtil.class);
    public static final String CHARSET = "UTF-8";

    private static CloseableHttpClient httpclient;

    static {
        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.setMaxConnPerRoute(50);
        builder.setMaxConnTotal(100);

        // 创建支持忽略证书的https
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (x509Certificates, s) -> true).build();

            httpclient = HttpClientBuilder.create()
                    .setSSLContext(sslContext)
                    .setConnectionManager(new PoolingHttpClientConnectionManager(RegistryBuilder.<ConnectionSocketFactory> create()
                            .register("http", PlainConnectionSocketFactory.INSTANCE)
                            .register("https", new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE))
                            .build()))
                    .build();
        } catch (Throwable e) {
            // ignore
        }
    }

    public static String post(String url, String content) {
        logger.info("API，POST过去的数据是：{}", content);
        HttpPost httpPost = new HttpPost(url);
        StringEntity postEntity = new StringEntity(content, CHARSET);
        httpPost.addHeader("Content-type", "application/json");
        httpPost.setEntity(postEntity);

        logger.debug("执行请求 " + httpPost.getRequestLine());

        String result = null;
        try {
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity,CHARSET);
            logger.debug(result);
        } catch (ConnectionPoolTimeoutException e) {
            logger.error("Exception: 连接池超时.", e);
        } catch (ConnectTimeoutException e) {
            logger.error("Exception: 连接超时", e);
        } catch (SocketTimeoutException e) {
            logger.error("Exception: Socket超时.", e);
        } catch (ConnectException e){
            logger.error("Exception: 连接被拒绝.", e);
        } catch (Exception e) {
            logger.error("HTTP Exception", e);
        } finally {
            httpPost.abort();
        }
        return result;
    }


    public static void main(String[] args) throws Exception {
        System.out.println(post("http://api.map.baidu.com/geocoder?location=104.085014,30.64938&output=json&key=G6OznXDUd9qAF0FCdIYz0l3UvzQuSbam", ""));
    }


}
