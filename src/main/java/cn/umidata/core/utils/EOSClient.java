package cn.umidata.core.utils;

import cn.umidata.core.constants.Constants;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

/**
 * @author qushaohua
 * @Title: EOSClient
 * @ProjectName ueos-server
 * @Description: TODO
 * @date 2018/8/279:06
 */
public class EOSClient {

    private static final Log logger = LogFactory.getLog(EOSClient.class);

    // 版本
    public static final String VERSION = "/v1";

    // 链
    private static final String MODEL_CHAIN = VERSION + "/chain";
    // 获取与节点相关的最新信息
    private static final String CHAIN_GET_INFO = MODEL_CHAIN + "/get_info";//获取与节点相关的最新信息
    private static final String CHAIN_GET_BLOCK = MODEL_CHAIN + "/get_block";//获取区块信息
    private static final String CHAIN_GET_ACCOUNT = MODEL_CHAIN + "/get_account";//获取与账户相关的信息
    private static final String CHAIN_GET_CODE = MODEL_CHAIN + "/get_code";//获取智能合约代码
    private static final String CHAIN_GET_TABLE_ROWS = MODEL_CHAIN + "/get_table_rows";//获取智能合约数据
    private static final String CHAIN_ABI_JSON_TO_BIN = MODEL_CHAIN + "/abi_json_to_bin";//将json序列化为二进制十六进制，得到的二进制十六进制通常用于puch_transaction中的数据字段
    private static final String CHAIN_ABI_BIN_TO_JSON = MODEL_CHAIN + "/abi_bin_to_json";//将二进制十六进制转化为json
    private static final String CHAIN_PUSH_TRANSACTION = MODEL_CHAIN + "/push_transaction";//预期采用JSON格式的事务，并将尝试将其应用于区块链。
    private static final String CHAIN_PUSH_TRANSACTIONS = MODEL_CHAIN + "/push_transactions";//一次推送多个事务
    private static final String CHAIN_GET_REQUIRED_KEYS = MODEL_CHAIN + "/get_required_keys";//获取必需的密钥，从密钥列表中签署交易

    // 钱包
    private static final String MODEL_WALLET = VERSION + "/wallet";
    private static final String WALLET_CREATE = MODEL_WALLET + "/create";//用给定的名称创建一个新的钱包，返回值为钱包的密码,用来解锁钱包；
    private static final String WALLET_OPEN = MODEL_WALLET + "/open";//打开给定名称的现有钱包
    private static final String WALLET_LOCK = MODEL_WALLET + "/lock";//锁定给定名称的钱包
    private static final String WALLET_LOCK_ALL = MODEL_WALLET + "/lock_all";//锁定所有钱包
    private static final String WALLET_UNLOCK = MODEL_WALLET + "/unlock";//使用给定的名称和密码来解锁钱包
    private static final String WALLET_IMPORT_KEY = MODEL_WALLET + "/import_key";//.将私钥导入给定名称的钱包
    private static final String WALLET_LIST_WALLETS = MODEL_WALLET + "/list_wallets";//列出所有钱包(已被打开的钱包)
    private static final String WALLET_LIST_KEYS = MODEL_WALLET + "/list_keys";//列出所有钱包中的所有秘钥对
    private static final String WALLET_GET_PUBLIC_KEYS = MODEL_WALLET + "/get_public_keys";//列出所有钱包中的所有公钥
    private static final String WALLET_SET_TIMEOUT = MODEL_WALLET + "/set_timeout";//设置钱包自动锁定超时（以秒为单位）
    private static final String WALLET_SIGN_TRANSACTION = MODEL_WALLET + "/sign_transaction";//给定一个事务数组的签名事务，需要公钥和链ID

    private final String url;

    public EOSClient(String url) {
        logger.debug(format("new EOSClient %s", url));
        this.url = url;
    }

    public static EOSClient createNewInstance(String url) throws MalformedURLException {
        return new EOSClient(url);
    }

    /**
     * 获取与节点相关的最新信息
     *
     * @return
     * @throws IOException
     */
    public String getInfo() throws IOException {
        String response = doPost(this.url + CHAIN_GET_INFO, JSONObject.toJSONString(new HashMap<>()), "utf-8");
        return response;
    }
    /**
     * 获取区块信息
     *
     * @return
     * @throws IOException
     */
    public String getBlock(String blockNumber) throws IOException {
        logger.debug(format("get_block#blockNumber %s", blockNumber));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("block_num_or_id",blockNumber);
        String response = doPost(this.url + CHAIN_GET_BLOCK, JSONObject.toJSONString(new HashMap<>()), "utf-8");
        return response;
    }









    String doPost(String url, String jsonBody, String charset) {
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
            StringEntity entity = new StringEntity(jsonBody, charset);
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
