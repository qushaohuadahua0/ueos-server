package cn.umidata.core.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by liutao on 2018-1-30.
 */
public class HttpUtil {

    /**
     *
     * @param url
     *            要提交的目标url
     * @param map
     *            参数集合
     * @param charset
     *            编码
     * @return
     */
    public static String doPost(String url, Map<String, String> map, String charset) {
        // 定义一个可关闭的httpClient的对象
        CloseableHttpClient httpClient = null;

        // 定义httpPost对象
        HttpPost post = null;

        // 返回结果
        String result = null;

        try {
            // 1.创建httpClient的默认实例
            httpClient = createSSLClientDefault();

            // 2.提交post
            post = new HttpPost(url);

            // 5.编码
            StringEntity entity = new StringEntity(JSONObject.toJSONString(map), charset);
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(entity);
            // 执行
            CloseableHttpResponse response = httpClient.execute(post);
            try {
                if (response != null&& response.getStatusLine().getStatusCode()==200) {
                    HttpEntity httpEntity = response.getEntity();
                    // 如果返回的内容不为空
                    if (httpEntity != null) {
                        result = EntityUtils.toString(httpEntity,"UTF-8");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                //关闭response
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                //关闭资源
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
    public static String doPost(String url, List<NameValuePair> list, String charset) {
        // 定义一个可关闭的httpClient的对象
        CloseableHttpClient httpClient = null;

        // 定义httpPost对象
        HttpPost post = null;

        // 返回结果
        String result = null;

        try {
            // 1.创建httpClient的默认实例
            httpClient = HttpClients.createDefault();
            // 2.提交post
            post = new HttpPost(url);

            // 5.编码
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                post.setEntity(entity);
            }

            // 执行
            CloseableHttpResponse response = httpClient.execute(post);
            try {
                if (response != null&& response.getStatusLine().getStatusCode()==200) {
                    HttpEntity httpEntity = response.getEntity();
                    // 如果返回的内容不为空
                    if (httpEntity != null) {
                        result = EntityUtils.toString(httpEntity,"UTF-8");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                //关闭response
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                //关闭资源
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public static CloseableHttpClient createSSLClientDefault(){

        try {
            //SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            // 在JSSE中，证书信任管理器类就是实现了接口X509TrustManager的类。我们可以自己实现该接口，让它信任我们指定的证书。
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            //信任所有
            X509TrustManager x509mgr = new X509TrustManager() {

                //　　该方法检查客户端的证书，若不信任该证书则抛出异常
                public void checkClientTrusted(X509Certificate[] xcs, String string) {
                }
                // 　　该方法检查服务端的证书，若不信任该证书则抛出异常
                public void checkServerTrusted(X509Certificate[] xcs, String string) {
                }
                // 　返回受信任的X509证书数组。
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[] { x509mgr }, null);
            ////创建HttpsURLConnection对象，并设置其SSLSocketFactory对象
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            //  HttpsURLConnection对象就可以正常连接HTTPS了，无论其证书是否经权威机构的验证，只要实现了接口X509TrustManager的类MyX509TrustManager信任该证书。
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();


        } catch (KeyManagementException e) {

            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();

        }

        // 创建默认的httpClient实例.
        return  HttpClients.createDefault();

    }
}