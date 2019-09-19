package com.soholy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.soholy.entity.TPlatfrom;
import com.soholy.entity.TVersionInfo;
import com.soholy.mapper.PLViewDb;
import com.soholy.mapper.TPlatfromMapper;
import com.soholy.mapper.TVersionInfoMapper;
import com.soholy.pojo.VersionRefPl;
import com.soholy.service.PLViewService;
import com.soholy.utils.ReqPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PLViewServiceImpl implements PLViewService {

    @Autowired
    private PLViewDb plViewDb;


    @Autowired
    private TPlatfromMapper tPlatfromMapper;


    @Autowired
    private TVersionInfoMapper tVersionInfoMapper;


    @Override
    public Page<VersionRefPl> findVersionList(ReqPage.QReqPage reqPage, Integer plSeq, Integer make) {
        Page<VersionRefPl> page = new Page<>();
        page.setTotal(plViewDb.findVersionListCount(reqPage, plSeq, make));
        page.setRecords(plViewDb.findVersionList(reqPage, plSeq, make));
        return page;
    }

    @Override
    public Page<TPlatfrom> findPletforms() {
        Page<TPlatfrom> page = new Page<>();
        LambdaQueryWrapper<TPlatfrom> query = Wrappers.<TPlatfrom>lambdaQuery();
        page.setTotal(tPlatfromMapper.selectCount(query));
        page.setRecords(tPlatfromMapper.selectList(query));
        return page;
    }

    @Override
    @Transactional
    public boolean save(String pkUrl, Integer platformId) {
        if (StringUtils.isNoneBlank(pkUrl) && pkUrl != null) {
            List<TVersionInfo> vs = tVersionInfoMapper.selectList(Wrappers.<TVersionInfo>lambdaQuery().eq(TVersionInfo::getPlatformId, platformId).orderByDesc(TVersionInfo::getVersionInfo));
            int oldV = 0;
            if (vs != null && vs.size() > 0) {
                oldV = Integer.valueOf(vs.get(0).getVersionInfo());
            }
            TVersionInfo info = new TVersionInfo();
            info.setCreationTime(LocalDateTime.now());
            info.setMake(0);
            info.setPkUrl(pkUrl);
            info.setPlatformId(platformId);
            info.setVersionInfo(++oldV + "");

            return tVersionInfoMapper.insert(info) == 1;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean modifyMakeByVSeq(Integer vSeq, Integer stat) {
        return 1 == tVersionInfoMapper.update(null, Wrappers.<TVersionInfo>lambdaUpdate().eq(TVersionInfo::getVSeq, vSeq).set(TVersionInfo::getMake, stat));
    }

    @Override
    @Transactional
    public boolean removeByVSeq(Integer vSeq) {
        return 1 == tVersionInfoMapper.deleteById(vSeq);
    }
}
