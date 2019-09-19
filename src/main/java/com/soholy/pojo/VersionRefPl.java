package com.soholy.pojo;

import com.soholy.entity.TVersionInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
public class VersionRefPl extends TVersionInfo {


    private Integer plSeq;

    /**
     * 品台名称
     */
    private String plName;

    /**
     * 平台ip
     */
    private String ipInfo;

    /**
     * 域名地址
     */
    private String addr;

    private LocalDateTime plCreationTime;

}
