package cn.umidata.eos.controller;

import cn.umidata.core.utils.HttpClientUtil;
import cn.umidata.eos.service.ChainService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 获取与节点相关的最新信息
 */
@CrossOrigin
@RestController
@RequestMapping("v1/chain")
public class ChainController {

    @Resource
    private ChainService appService;

    @PostMapping(value = "getInfo")
    public String getInfo(@RequestParam("token") String token) throws IOException {
        return appService.getInfo();
    }

    @PostMapping(value = "getBlock")
    public String getInfo(@RequestParam("token") String token,@RequestParam("blockNumber") String blockNumber) throws IOException {
        return appService.getBlock(blockNumber);
    }

}
