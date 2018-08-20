/**
 * @(#)Constants.java 1.0 2016年4月11日
 * @Copyright:  Copyright 2016 - 2016 Meyli. Co. Ltd. All Rights Reserved.
 * @Description: 
 * 
 * Modification History:
 * Date:        2016年4月11日
 * Author:      gaobing
 * Version:     V1.0.0.1
 * Description: (Initialize)
 * Reviewer:    
 * Review Date: 
 */
package cn.umidata.core.constants;

import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Properties;

public class Constants {

    // 版本
    public static final String VERSION = "/v1";

    // 链
    public static final String MODEL_CHAIN = "/chain";
    // 获取与节点相关的最新信息
    public static final String CHAIN_GET_INFO = "/get_info";//获取与节点相关的最新信息
    public static final String CHAIN_GET_BLOCK = "/get_block";//获取区块信息
    public static final String CHAIN_GET_ACCOUNT = "/get_account";//获取与账户相关的信息
    public static final String CHAIN_GET_CODE = "/get_code";//获取智能合约代码
    public static final String CHAIN_GET_TABLE_ROWS = "/get_table_rows";//获取智能合约数据
    public static final String CHAIN_ABI_JSON_TO_BIN = "/abi_json_to_bin";//将json序列化为二进制十六进制，得到的二进制十六进制通常用于puch_transaction中的数据字段
    public static final String CHAIN_ABI_BIN_TO_JSON = "/abi_bin_to_json";//将二进制十六进制转化为json
    public static final String CHAIN_PUSH_TRANSACTION = "/push_transaction";//预期采用JSON格式的事务，并将尝试将其应用于区块链。
    public static final String CHAIN_PUSH_TRANSACTIONS = "/push_transactions";//一次推送多个事务
    public static final String CHAIN_GET_REQUIRED_KEYS = "/get_required_keys";//获取必需的密钥，从密钥列表中签署交易

    // 钱包
    public static final String MODEL_WALLET = "/wallet";
    public static final String WALLET_CREATE = "/create";//用给定的名称创建一个新的钱包，返回值为钱包的密码,用来解锁钱包；
    public static final String WALLET_OPEN = "/open";//打开给定名称的现有钱包
    public static final String WALLET_LOCK = "/lock";//锁定给定名称的钱包
    public static final String WALLET_LOCK_ALL = "/lock_all";//锁定所有钱包
    public static final String WALLET_UNLOCK = "/unlock";//使用给定的名称和密码来解锁钱包
    public static final String WALLET_IMPORT_KEY = "/import_key";//.将私钥导入给定名称的钱包
    public static final String WALLET_LIST_WALLETS = "/list_wallets";//列出所有钱包(已被打开的钱包)
    public static final String WALLET_LIST_KEYS = "/list_keys";//列出所有钱包中的所有秘钥对
    public static final String WALLET_GET_PUBLIC_KEYS = "/get_public_keys";//列出所有钱包中的所有公钥
    public static final String WALLET_SET_TIMEOUT = "/set_timeout";//设置钱包自动锁定超时（以秒为单位）
    public static final String WALLET_SIGN_TRANSACTION = "/sign_transaction";//给定一个事务数组的签名事务，需要公钥和链ID



}
