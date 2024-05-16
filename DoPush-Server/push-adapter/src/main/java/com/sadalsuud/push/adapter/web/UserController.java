package com.sadalsuud.push.adapter.web;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import com.sadalsuud.push.adapter.facade.annotation.DoPushAspect;
import com.sadalsuud.push.client.dto.UserParam;
import com.sadalsuud.push.client.vo.UserVo;
import com.sadalsuud.push.common.vo.BasicResultVO;
import com.sadalsuud.push.domain.user.User;
import com.sadalsuud.push.infrastructure.gatewayImpl.repository.UserDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/5/16
 * @Project DoPush-Server
 */
@Slf4j
@DoPushAspect
@RestController
@RequestMapping("/user")
@Api(tags = {"用户管理接口"})
@RequiredArgsConstructor
public class UserController {

    private final UserDao userDao;

    /**
     * 列表数据
     */
    @GetMapping("/list")
    @ApiOperation("/列表页")
    public UserVo queryList(@Validated UserParam param) {

        PageRequest pageRequest = PageRequest.of(param.getPage() - 1, param.getPerPage());
        Page<User> users = userDao.findAll((Specification<User>) (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();

            // 加搜索条件
            if (CharSequenceUtil.isNotBlank(param.getKeywords())) {
                predicateList.add(cb.like(root.get("username").as(String.class), "%" + param.getKeywords() + "%"));
            }
            Predicate[] p = new Predicate[predicateList.size()];
            // 查询
            query.where(cb.and(predicateList.toArray(p)));
            // 排序
            query.orderBy(cb.desc(root.get("updated")));
            return query.getRestriction();
        }, pageRequest);
        List<User> list = users.toList();
        list.forEach(User::desensitivity);
        return UserVo.builder()
                .count(users.getTotalElements())
                .rows(list)
                .build();
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
            idList.forEach(userDao::deleteById);
        }
    }

    @GetMapping("change")
    @ApiOperation("/用户更改")
    public BasicResultVO change(Long id, String password, Integer role) {
        Optional<User> user = userDao.findById(id);
        if (!user.isPresent()) {
            return BasicResultVO.fail("用户不存在");
        }

        User user1 = user.get();
        user1.setPassword(password);
        user1.setRole(role);

        userDao.save(user1);
        return BasicResultVO.success();
    }
}
