package com.sadalsuud.push.adapter.web;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import com.sadalsuud.push.adapter.facade.annotation.DoPushAspect;
import com.sadalsuud.push.adapter.facade.annotation.DoPushResult;
import com.sadalsuud.push.adapter.facade.exception.CommonException;
import com.sadalsuud.push.client.api.ChannelAccountService;
import com.sadalsuud.push.common.constant.DoPushConstant;
import com.sadalsuud.push.common.enums.RespStatusEnum;
import com.sadalsuud.push.domain.support.gateway.domain.ChannelAccount;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description 渠道账号管理接口
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 12/12/2023
 * @Package com.sadalsuud.push.adapter.web
 */
@Slf4j
@DoPushAspect
@DoPushResult
@RestController
@RequestMapping("/account")
@Api(tags = {"渠道账号管理接口"})
@RequiredArgsConstructor
public class ChannelAccountController {

    private final ChannelAccountService channelAccountService;

    //private LoginUtils loginUtils;

    /**
     * 如果Id存在，则修改
     * 如果Id不存在，则保存
     */
    @PostMapping("/save")
    @ApiOperation("/保存数据")
    public ChannelAccount saveOrUpdate(@RequestBody ChannelAccount channelAccount) {
        if (
                //loginUtils.needLogin() &&
                        CharSequenceUtil.isBlank(channelAccount.getCreator())) {
            throw new CommonException(RespStatusEnum.NO_LOGIN.getCode(), RespStatusEnum.NO_LOGIN.getMsg());
        }
        channelAccount.setCreator(CharSequenceUtil.isBlank(channelAccount.getCreator()) ? DoPushConstant.DEFAULT_CREATOR : channelAccount.getCreator());

        return channelAccountService.save(channelAccount);
    }

    /**
     * 根据渠道标识查询渠道账号相关的信息
     */
    @GetMapping("/queryByChannelType")
    @ApiOperation("/根据渠道标识查询相关的记录")
    public List<ChannelAccount> query(Integer channelType, String creator) {
        if (
                //loginUtils.needLogin() &&
                CharSequenceUtil.isBlank(creator)) {
            throw new CommonException(RespStatusEnum.NO_LOGIN.getCode(), RespStatusEnum.NO_LOGIN.getMsg());
        }
        creator = CharSequenceUtil.isBlank(creator) ? DoPushConstant.DEFAULT_CREATOR : creator;

        return channelAccountService.queryByChannelType(channelType, creator);
    }

    /**
     * 所有的渠道账号信息
     */
    @GetMapping("/list")
    @ApiOperation("/渠道账号列表信息")
    public List<ChannelAccount> list(String creator) {
        if (
                //loginUtils.needLogin() &&
                        CharSequenceUtil.isBlank(creator)) {
            throw new CommonException(RespStatusEnum.NO_LOGIN.getCode(), RespStatusEnum.NO_LOGIN.getMsg());

        }
        creator = CharSequenceUtil.isBlank(creator) ? DoPushConstant.DEFAULT_CREATOR : creator;

        return channelAccountService.list(creator);
    }

    /**
     * 根据Id删除
     * id多个用逗号分隔开
     */
    @DeleteMapping("delete/{id}")
    @ApiOperation("/根据Ids删除")
    public void deleteByIds(@PathVariable("id") String id) {
        if (CharSequenceUtil.isNotBlank(id)) {
            List<Long> idList = Arrays.stream(id.split(StrPool.COMMA)).map(Long::valueOf).collect(Collectors.toList());
            channelAccountService.deleteByIds(idList);
        }
    }

}