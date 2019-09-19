package com.soholy.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.soholy.entity.TPlatfrom;
import com.soholy.exception.RspException;
import com.soholy.mapper.PLViewDb;
import com.soholy.mapper.TPlatfromMapper;
import com.soholy.pojo.VersionRefPl;
import com.soholy.service.TVersionInfoServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TVersionInfoServerImpl implements TVersionInfoServer {

    @Autowired
    private PLViewDb plViewDb;

    @Autowired
    private TPlatfromMapper tPlatfromMapper;

    @Override
    public VersionRefPl checkVersion(Integer version, int checkType, String platform) {
        List<VersionRefPl> plvs = plViewDb.findPlVersion(null, checkType, platform);
        if (plvs == null || plvs.size() == 0) {
            throw new RspException("所查询平台不存在");
        }
        VersionRefPl plv = plvs.get(0);
        if (!version.equals(Integer.valueOf(plv.getVersionInfo()))) {
            return plv;//返回最新的版本信息
        }
        return null;
    }

    @Override
    public List<TPlatfrom> findPlatforms() {
        return tPlatfromMapper.selectList(Wrappers.<TPlatfrom>lambdaQuery().orderByAsc(TPlatfrom::getCreationTime));
    }

}
