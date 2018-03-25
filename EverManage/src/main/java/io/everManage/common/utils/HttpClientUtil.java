package io.everManage.common.utils;

import com.google.gson.Gson;
import io.everManage.modules.app.entity.OAuthEntity;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

import static io.everManage.common.utils.ConfigConstant.CLIENT_ID;
import static io.everManage.common.utils.ConfigConstant.CLIENT_SECRET;

/**
 * Created by Administrator on 2015/11/28.
 */
public class HttpClientUtil {
    private static HttpClientContext context = HttpClientContext.create();
    private static RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(120000).setSocketTimeout(60000)
            .setConnectionRequestTimeout(60000).setCookieSpec(CookieSpecs.STANDARD_STRICT).
                    setExpectContinueEnabled(true).
                    setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST)).
                    setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();

    //https
    private static SSLConnectionSocketFactory socketFactory;
    private static TrustManager manager = new X509TrustManager() {

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    };

    private static void enableSSL() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{manager}, null);
            socketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    /**
     * https get
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static CloseableHttpResponse doHttpsGet(String url) {
        return doHttpsGet(url, new HashMap<>());
    }

    /**
     * https get
     *
     * @param url
     * @param headers
     * @return
     * @throws IOException
     */
    public static CloseableHttpResponse doHttpsGet(String url, HashMap<String, String> headers) {
        enableSSL();
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE).register("https", socketFactory).build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig).build();
        HttpGet httpGet = new HttpGet(url);
        Iterator iterator = headers.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next().toString();
            httpGet.setHeader(key, headers.get(key));
        }
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet, context);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    /**
     * https post
     *
     * @param url
     * @param values
     * @return
     * @throws IOException
     */
    public static CloseableHttpResponse doHttpsPost(String url, List<NameValuePair> values) {
        return doHttpsPost(url, values, new HashMap<>());
    }

    /**
     * https post
     *
     * @param url
     * @param values
     * @return
     * @throws IOException
     */
    public static CloseableHttpResponse doHttpsPost(String url, List<NameValuePair> values, HashMap<String, String> headers) {
        enableSSL();
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE).register("https", socketFactory).build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig).build();
        HttpPost httpPost = new HttpPost(url);
        Iterator iterator = headers.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next().toString();
            httpPost.setHeader(key, headers.get(key));
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(values, Consts.UTF_8);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost, context);
        } catch (Exception e) {
        }
        return response;
    }

    /**
     * http get
     *
     * @param url
     * @return
     */
    public static CloseableHttpResponse doGet(String url, String data) {
        CookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpClient = HttpClientBuilder.create().
                setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy()).
                setRedirectStrategy(new DefaultRedirectStrategy()).
                setDefaultCookieStore(cookieStore).
                setDefaultRequestConfig(requestConfig).build();

        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet, context);
        } catch (Exception e) {
        }
        return response;
    }

    /**
     * http post
     *
     * @param url
     * @param values
     * @return
     */
    public static CloseableHttpResponse doPost(String url, List<NameValuePair> values) {
        CookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpClient = HttpClientBuilder.create().
                setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy()).
                setRedirectStrategy(new DefaultRedirectStrategy()).
                setDefaultCookieStore(cookieStore).
                setDefaultRequestConfig(requestConfig).build();

        HttpPost httpPost = new HttpPost(url);
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(values, Consts.UTF_8);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost, context);
        } catch (Exception e) {
        }
        return response;
    }


    /**
     * 直接把Response内的Entity内容转换成String
     *
     * @param httpResponse
     * @return
     */
    public static String toString(CloseableHttpResponse httpResponse) {
        // 获取响应消息实体
        String result = null;
        try {
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (Exception e) {
        } finally {
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 构造Basic Auth认证头信息
     *
     * @return
     */
    public static String getHeader() {
        String auth = CLIENT_ID + ":" + CLIENT_SECRET;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
        String authHeader = "Basic " + new String(encodedAuth);
        return authHeader;
    }

    public static void main(String[] args) {
        String url = "https://www.getabstract.com/api/oauth/token";
        List<NameValuePair> urlParameters = new ArrayList<>();
        HashMap headers = new HashMap();
        urlParameters.add(new BasicNameValuePair("grant_type", "client_credentials"));
        headers.put("Authorization", getHeader());
        CloseableHttpResponse response = HttpClientUtil.doHttpsPost(url, urlParameters, headers);
        String res = HttpClientUtil.toString(response);
        OAuthEntity oAuthEntity = new Gson().fromJson(res, OAuthEntity.class);
        System.out.println("res= " + res);

        String url2 = "https://www.getabstract.com/api/library/v1/abstract/featured?language=zh";
        headers.clear();
        headers.put("Authorization", oAuthEntity.getAuthorization());
        CloseableHttpResponse response2 = HttpClientUtil.doHttpsGet(url2, headers);
        String res2 = HttpClientUtil.toString(response2);
        System.out.println("res2= " + res2);
    }
}