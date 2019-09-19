package com.soholy.mapper;

import com.soholy.pojo.VersionRefPl;
import com.soholy.utils.ReqPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PLViewDb {

    /**
     * 查看平台列表
     *
     * @param reqPage
     * @param plSeq   平台id
     * @param make    启用标示 0不可用 1启用 2禁用
     * @return
     */
    List<VersionRefPl> findVersionList(@Param("page") ReqPage.QReqPage reqPage, @Param("plSeq") Integer plSeq, @Param("make") Integer make);


    /**
     * 查看平台列表 count
     *
     * @param reqPage
     * @param plSeq   平台id
     * @param make    启用标示 0不可用 1启用 2禁用
     * @return
     */
    Integer findVersionListCount(@Param("page") ReqPage.QReqPage reqPage, @Param("plSeq") Integer plSeq, @Param("make") Integer make);

    /**
     * 查询平台版本信息
     *
     * @param version   版本信息
     * @param checkType 1根据平台id检查 2 根据平台名称检查
     * @param platform  平台信息（名称或id）
     * @return
     */
    List<VersionRefPl> findPlVersion(@Param("version") Integer version, @Param("checkType") int checkType, @Param("platform") String platform);
}
