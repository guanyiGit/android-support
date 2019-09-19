package com.soholy.service;

import com.soholy.entity.TPlatfrom;
import com.soholy.pojo.VersionRefPl;

import java.util.List;

public interface TVersionInfoServer {

    /**
     * 检查平台版本信息
     *
     * @param version   版本信息
     * @param checkType 1根据平台id检查 2 根据平台名称检查
     * @param platform  平台信息（名称或id）
     * @return
     */
    VersionRefPl checkVersion(Integer version, int checkType, String platform);

    /**
     * 查询所有平台信息
     *
     * @return
     */
    List<TPlatfrom> findPlatforms();
}

