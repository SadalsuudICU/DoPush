package com.sadalsuud.push.infrastructure.gatewayImpl.repository;

import com.sadalsuud.push.domain.support.Material;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/4/21
 * @Project DoPush-Server
 */
@Repository
public interface MaterialDao extends JpaRepository<Material, Long>, JpaSpecificationExecutor<Material> {

    /**
     * 查询 列表（分页)
     *
     * @param deleted  0：未删除 1：删除
     * @param pageable 分页对象
     * @return
     */
    List<Material> findAllByIsDeletedEqualsOrderByCreateTimeDesc(Integer deleted, Pageable pageable);
}
