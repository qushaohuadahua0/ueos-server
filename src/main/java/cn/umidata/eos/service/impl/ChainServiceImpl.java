/*
 * Copyright (c) 2018. Aberic - All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.umidata.eos.service.impl;

import cn.umidata.core.constants.Constants;
import cn.umidata.core.utils.HttpClientUtil;
import cn.umidata.core.utils.HttpUtil;
import cn.umidata.eos.service.ChainService;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service("appService")
public class ChainServiceImpl implements ChainService {

    @Resource
    private Environment env;
    
    @Override
    public String getInfo() throws IOException {
        String response = HttpUtil.doPost(env.getProperty("eos.node.ip") + Constants.VERSION + Constants.MODEL_CHAIN + Constants.CHAIN_GET_INFO, new HashMap<>(), "utf-8");
        return response;
    }

    @Override
    public String getBlock(String blockNumber) {
        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("block_num_or_id",blockNumber);
        String response = HttpUtil.doPost(env.getProperty("eos.node.ip") + Constants.VERSION + Constants.MODEL_CHAIN + Constants.CHAIN_GET_BLOCK, objectObjectHashMap, "utf-8");
        return response;
    }
}