package com.sadalsuud.push.application.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMediaUploadRequest;
import com.dingtalk.api.response.OapiMediaUploadResponse;
import com.google.common.base.Throwables;
import com.sadalsuud.push.client.api.MaterialService;
import com.sadalsuud.push.client.dto.MaterialParam;
import com.sadalsuud.push.client.vo.UploadResponseVo;
import com.sadalsuud.push.common.constant.CommonConstant;
import com.sadalsuud.push.common.constant.DoPushConstant;
import com.sadalsuud.push.common.constant.SendChanelUrlConstant;
import com.sadalsuud.push.common.dto.account.DingDingWorkNoticeAccount;
import com.sadalsuud.push.common.enums.ChannelType;
import com.sadalsuud.push.common.enums.EnumUtil;
import com.sadalsuud.push.common.enums.FileType;
import com.sadalsuud.push.common.enums.RespStatusEnum;
import com.sadalsuud.push.common.vo.BasicResultVO;
import com.sadalsuud.push.domain.channel.AccountService;
import com.sadalsuud.push.infrastructure.gatewayImpl.handler.AccessTokenUtils;
import com.sadalsuud.push.infrastructure.gatewayImpl.repository.MaterialDao;
import com.sadalsuud.push.domain.support.Material;
import com.taobao.api.FileItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Predicate;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/3/6
 * @Project DoPush-Server
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {
    private final AccountService accountUtils;

    private final AccessTokenUtils accessTokenUtils;

    private final MaterialDao materialDao;

    @Value("${dopush.business.upload.crowd.path}")
    private String dataPath;


    @Override
    public BasicResultVO dingDingMaterialUpload(MultipartFile file, String sendAccount, String fileType, String creator, String name) {
        OapiMediaUploadResponse rsp;
        try {
            String path = upload(file, name, Integer.valueOf(fileType), creator).getPath();

            DingDingWorkNoticeAccount account = accountUtils.getAccountById(Integer.valueOf(sendAccount), DingDingWorkNoticeAccount.class);
            String accessToken = accessTokenUtils.getAccessToken(ChannelType.DING_DING_WORK_NOTICE.getCode(), Integer.valueOf(sendAccount), account, false);
            DingTalkClient client = new DefaultDingTalkClient(SendChanelUrlConstant.DING_DING_UPLOAD_URL);
            OapiMediaUploadRequest req = new OapiMediaUploadRequest();
            // 再次获取MF
            File reF = new File(dataPath, path);
            String reN = reF.getName();
            Path path1 = Paths.get(path);
            String contentType = Files.probeContentType(path1);
            byte[] content = Files.readAllBytes(path1);
            FileItem item = new FileItem(IdUtil.fastSimpleUUID() + file.getOriginalFilename(),
                    content);
            req.setMedia(item);
            req.setType(EnumUtil.getDescriptionByCode(Integer.valueOf(fileType), FileType.class));
            rsp = client.execute(req, accessToken);
            if (rsp.isSuccess()) {
                return BasicResultVO.success(UploadResponseVo.builder().id(rsp.getMediaId()).build());
            }
            log.error("MaterialService#dingDingMaterialUpload fail:{}", rsp.getErrmsg());
        } catch (Exception e) {
            log.error("MaterialService#dingDingMaterialUpload fail:{}", Throwables.getStackTraceAsString(e));
        }
        return BasicResultVO.fail(RespStatusEnum.SERVICE_ERROR.getMsg());
    }

    @Override
    public Page<Material> queryList(MaterialParam param) {
        PageRequest pageRequest = PageRequest.of(param.getPage() - 1, param.getPerPage());
        String creator = CharSequenceUtil.isBlank(param.getCreator()) ? DoPushConstant.DEFAULT_CREATOR : param.getCreator();
        return materialDao.findAll((Specification<Material>) (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            // 加搜索条件
            if (CharSequenceUtil.isNotBlank(param.getKeywords())) {
                predicateList.add(cb.like(root.get("name").as(String.class), "%" + param.getKeywords() + "%"));
            }
            predicateList.add(cb.equal(root.get("isDeleted").as(Integer.class), CommonConstant.FALSE));
            predicateList.add(cb.equal(root.get("creator").as(String.class), creator));
            Predicate[] p = new Predicate[predicateList.size()];
            // 查询
            query.where(cb.and(predicateList.toArray(p)));
            // 排序
            query.orderBy(cb.desc(root.get("createTime")));
            return query.getRestriction();
        }, pageRequest);

    }

    @Override
    public Material upload(MultipartFile file, String name, Integer type, String creator) throws Exception {
        String originalFilename = file.getOriginalFilename();
        String filePath = IdUtil.fastSimpleUUID() + originalFilename;
        System.out.println(filePath);
        File localFile;
        try {
            localFile = new File(dataPath, filePath);
            if (!localFile.exists()) {
                localFile.mkdirs();
            }
            file.transferTo(localFile);
        } catch (Exception e) {
            log.error("MessageTemplateController#upload fail! e:{},params{}", Throwables.getStackTraceAsString(e), JSON.toJSONString(file));
            throw e;
        }

        String c = CharSequenceUtil.isBlank(creator) ? DoPushConstant.DEFAULT_CREATOR : creator;
        String n = CharSequenceUtil.isBlank(name) ? originalFilename : name;

        Material save = new Material();
        save.setId(DateUtil.currentSeconds());
        save.setName(n);
        save.setType(type);
        save.setCreator(c);
        save.setPath(localFile.getAbsolutePath());
        save.setIsDeleted(0);

        save.setCreateTime(Math.toIntExact(DateUtil.currentSeconds()));

        return materialDao.save(save);
    }
}
