package com.soholy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.soholy.entity.TPlatfrom;
import com.soholy.pojo.VersionRefPl;
import com.soholy.utils.ReqPage;

public interface PLViewService {
    /**
     * 查看平台列表
     *
     * @param reqPage
     * @param plSeq   平台id
     * @param make    启用标示 0不可用 1启用 2禁用
     * @return
     */
    Page<VersionRefPl> findVersionList(ReqPage.QReqPage reqPage, Integer plSeq, Integer make);


    /**
     * 查询平台列表
     *
     * @param
     * @return
     */
    Page<TPlatfrom> findPletforms();

    /**
     * 新增包
     *
     * @param pkUrl      包的url
     * @param platformId 平台id
     */
    boolean save(String pkUrl, Integer platformId);

    /**
     * 修改版本信息的状态
     *
     * @param vSeq 版本id
     * @param stat 状态 1启用 2禁用
     * @return
     */
    boolean modifyMakeByVSeq(Integer vSeq, Integer stat);

    /**
     * 根据id删除版本信息
     *
     * @param vSeq vSeq 版本id
     * @return
     */
    boolean removeByVSeq(Integer vSeq);
}
