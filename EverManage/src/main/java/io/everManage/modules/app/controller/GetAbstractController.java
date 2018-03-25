package io.everManage.modules.app.controller;

import com.google.gson.Gson;
import io.everManage.common.utils.HttpClientUtil;
import io.everManage.common.utils.R;
import io.everManage.modules.app.entity.OAuthEntity;
import io.everManage.modules.app.entity.OAuthErrorEntity;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


/**
 * getAbstract的API
 * 代理API的所有参数都按照字符串进行处理（透明转发）
 *
 * @author wangshuai
 * @email shuaiwang126@163.com
 * @date 2018-02-24 22:28:58
 */
@RestController
@RequestMapping("/api/library/v1")
public class GetAbstractController {
    private final String URL = "https://www.getabstract.com";
    private final String API_VERSION = "/api/library/v1";
    private OAuthEntity oAuthEntity;//授权信息

    /**
     * 格式化GET请求参数
     *
     * @param parm
     * @return
     */
    private String formatGetParm(String uri, Hashtable<String, String> parm) {
        String re = URL + API_VERSION + uri;
        String pa_str = "";
        Iterator iterator = parm.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next().toString();
            pa_str = pa_str + key + "=" + parm.get(key) + "&";
        }
        if (pa_str.endsWith("&")) {
            pa_str = pa_str.substring(0, pa_str.length() - 1);
        }
        if (!StringUtils.isBlank(pa_str)) {
            re += "?" + pa_str;
        }
        System.out.println(re);
        return re;
    }

    /**
     * 初始化token
     */
    private void initToken() {
        String url = URL + "/api/oauth/token";
        List<NameValuePair> urlParameters = new ArrayList<>();
        HashMap headers = new HashMap();
        urlParameters.add(new BasicNameValuePair("grant_type", "client_credentials"));
        headers.put("Authorization", HttpClientUtil.getHeader());
        CloseableHttpResponse response = HttpClientUtil.doHttpsPost(url, urlParameters, headers);
        String res = HttpClientUtil.toString(response);
        oAuthEntity = new Gson().fromJson(res, OAuthEntity.class);
        System.out.println("res= " + res);
    }

    private CloseableHttpResponse doHttpsGet(String url) {
        HashMap<String, String> headers = new HashMap<>();
        if (oAuthEntity == null) {
            initToken();
        }
        headers.put("Authorization", oAuthEntity.getAuthorization());
        return HttpClientUtil.doHttpsGet(url, headers);
    }

    private CloseableHttpResponse doHttpsPost(String url, List<NameValuePair> values) {
        HashMap<String, String> headers = new HashMap<>();
        if (oAuthEntity == null) {
            initToken();
        }
        headers.put("Authorization", oAuthEntity.getAuthorization());
        return HttpClientUtil.doHttpsPost(url, values);
    }

    /**
     * 对接口调用进行容错处理（ 首次接口调用出错之后尝试重新获取token，并再次调用接口）
     *
     * @param uri          资源地址
     * @param formerResult 首次调用接口的返回值
     * @return
     */
    private R resultOptimize(String uri, String formerResult) {
        try {
            OAuthErrorEntity authErrorEntity = new Gson().fromJson(formerResult, OAuthErrorEntity.class);
            if (authErrorEntity != null && authErrorEntity.isTokenError()) {
                initToken();
                CloseableHttpResponse response2 = doHttpsGet(uri);
                formerResult = HttpClientUtil.toString(response2);
                System.out.println(String.format("授权失败，接口重试[%s]", uri));
            }
        } finally {
            JSONObject obj = JSONObject.fromObject(formerResult);//原始接口返回的是字符串格式的response，此处需要转换为json对象返回
            System.out.println("res2= " + formerResult);
            if (obj.containsKey("response_code") && obj.getInt("response_code") == 0) {
                return R.ok().put(obj);
            } else {
                return R.error().put(obj);
            }
        }
    }

    /**
     * 请求参数过滤
     *
     * @return
     */
    private void optStringParm(Hashtable<String, String> parm, String name, String value) {
        if (StringUtils.isNotBlank(value)) {
            parm.put(name, value);
        }
    }

    @RequestMapping("/abstract/featured")
    public R abstractFeatured(String language) {
        String uri = "/abstract/featured";
        Hashtable<String, String> parm = new Hashtable<>();
        optStringParm(parm, "language", language);
        uri = formatGetParm(uri, parm);
//        System.out.println("uri=" + uri);
        CloseableHttpResponse response1 = doHttpsGet(uri);
        String res = HttpClientUtil.toString(response1);
//        System.out.println("res= " + res);
        return resultOptimize(uri, res);
    }

    /**
     * 代理API的所有参数都按照字符串进行处理（透明转发）
     *
     * @param language
     * @param start
     * @param limit
     * @param modifiedFrom
     * @return
     */
    @RequestMapping("/abstract/list")
    public R abstractList(String language, String start, String limit, String modifiedFrom, String modifiedTo, String categoryId, String sumGroupId, String globalRightsOnly, String activeOnly) {
        String uri = "/abstract/list";
        Hashtable<String, String> parm = new Hashtable<>();
        optStringParm(parm, "language", language);
        optStringParm(parm, "start", start);
        optStringParm(parm, "limit", limit);
        optStringParm(parm, "modifiedFrom", modifiedFrom);
        optStringParm(parm, "modifiedTo", modifiedTo);
        optStringParm(parm, "categoryId", categoryId);
        optStringParm(parm, "sumGroupId", sumGroupId);
        optStringParm(parm, "globalRightsOnly", globalRightsOnly);
        optStringParm(parm, "activeOnly", activeOnly);

        uri = formatGetParm(uri, parm);
        CloseableHttpResponse response1 = doHttpsGet(uri);
        String res = HttpClientUtil.toString(response1);
        return resultOptimize(uri, res);
    }

    @RequestMapping("/abstract/newest")
    public R abstractNewest(String language, String start, String limit, String categoryId, String sumGroupId) {
        String uri = "/abstract/newest";
        Hashtable<String, String> parm = new Hashtable<>();
        optStringParm(parm, "language", language);
        optStringParm(parm, "start", start);
        optStringParm(parm, "limit", limit);
        optStringParm(parm, "sumGroupId", sumGroupId);
        optStringParm(parm, "categoryId", categoryId);

        uri = formatGetParm(uri, parm);
        CloseableHttpResponse response1 = doHttpsGet(uri);
        String res = HttpClientUtil.toString(response1);
        return resultOptimize(uri, res);
    }

    @RequestMapping("/abstract/oftheweek")
    public R abstractOftheweek(String language) {
        String uri = "/abstract/oftheweek";
        Hashtable<String, String> parm = new Hashtable<>();
        optStringParm(parm, "language", language);

        uri = formatGetParm(uri, parm);
        CloseableHttpResponse response1 = doHttpsGet(uri);
        String res = HttpClientUtil.toString(response1);
        return resultOptimize(uri, res);
    }

    @RequestMapping("/abstract/popular")
    public R abstractPopular(String language, String start, String limit, String categoryId) {
        String uri = "/abstract/popular";
        Hashtable<String, String> parm = new Hashtable<>();
        optStringParm(parm, "language", language);
        optStringParm(parm, "start", start);
        optStringParm(parm, "limit", limit);
        optStringParm(parm, "categoryId", categoryId);

        uri = formatGetParm(uri, parm);
        CloseableHttpResponse response1 = doHttpsGet(uri);
        String res = HttpClientUtil.toString(response1);
        return resultOptimize(uri, res);
    }

    @RequestMapping("/abstract/{dataId}")
    public R abstractInfo(@PathVariable("dataId") String dataId) {
        String uri = "/abstract/" + dataId;
        Hashtable<String, String> parm = new Hashtable<>();

        uri = formatGetParm(uri, parm);
        CloseableHttpResponse response1 = doHttpsGet(uri);
        String res = HttpClientUtil.toString(response1);
        return resultOptimize(uri, res);
    }

    @RequestMapping("/category/list")
    public R categoryList(String language) {
        String uri = "/category/list";
        Hashtable<String, String> parm = new Hashtable<>();
        optStringParm(parm, "language", language);

        uri = formatGetParm(uri, parm);
        CloseableHttpResponse response1 = doHttpsGet(uri);
        String res = HttpClientUtil.toString(response1);
        return resultOptimize(uri, res);
    }

    @RequestMapping("/competency/list")
    public R competencyList(String language, String start, String limit) {
        String uri = "/competency/list";
        Hashtable<String, String> parm = new Hashtable<>();
        optStringParm(parm, "language", language);
        optStringParm(parm, "start", start);
        optStringParm(parm, "limit", limit);

        uri = formatGetParm(uri, parm);
        CloseableHttpResponse response1 = doHttpsGet(uri);
        String res = HttpClientUtil.toString(response1);
        return resultOptimize(uri, res);
    }

    @RequestMapping("/competency/{competencyId}")
    public R competencyInfo(@PathVariable("competencyId") String competencyId) {
        String uri = "/competency/" + competencyId;
        Hashtable<String, String> parm = new Hashtable<>();

        uri = formatGetParm(uri, parm);
        CloseableHttpResponse response1 = doHttpsGet(uri);
        String res = HttpClientUtil.toString(response1);
        return resultOptimize(uri, res);
    }

    @RequestMapping("/languages")
    public R languages() {
        String uri = "/languages";
        Hashtable<String, String> parm = new Hashtable<>();

        uri = formatGetParm(uri, parm);
        CloseableHttpResponse response1 = doHttpsGet(uri);
        String res = HttpClientUtil.toString(response1);
        return resultOptimize(uri, res);
    }

    @RequestMapping("/search")
    public R search(String query, String audioOnly, String language, String publicationYear, String ratingOverall, String ratingApplicability,
                    String ratingInnovation, String ratingStyle, String sumGroupId, String categoryId, String start, String limit, String searchMode) {
        String uri = "/search";
        Hashtable<String, String> parm = new Hashtable<>();
        optStringParm(parm, "query", query);
        optStringParm(parm, "audioOnly", audioOnly);
        optStringParm(parm, "language", language);
        optStringParm(parm, "publicationYear", publicationYear);
        optStringParm(parm, "ratingOverall", ratingOverall);
        optStringParm(parm, "ratingApplicability", ratingApplicability);
        optStringParm(parm, "ratingInnovation", ratingInnovation);
        optStringParm(parm, "ratingStyle", ratingStyle);
        optStringParm(parm, "sumGroupId", sumGroupId);
        optStringParm(parm, "categoryId", categoryId);
        optStringParm(parm, "start", start);
        optStringParm(parm, "limit", limit);
        optStringParm(parm, "searchMode", searchMode);

        uri = formatGetParm(uri, parm);
        CloseableHttpResponse response1 = doHttpsGet(uri);
        String res = HttpClientUtil.toString(response1);
        return resultOptimize(uri, res);
    }

    @RequestMapping("/summarygroups")
    public R summarygroups() {
        String uri = "/summarygroups";
        Hashtable<String, String> parm = new Hashtable<>();

        uri = formatGetParm(uri, parm);
        CloseableHttpResponse response1 = doHttpsGet(uri);
        String res = HttpClientUtil.toString(response1);
        return resultOptimize(uri, res);
    }
}
