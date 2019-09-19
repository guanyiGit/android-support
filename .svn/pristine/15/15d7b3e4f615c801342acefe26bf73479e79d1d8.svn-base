package com.soholy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author GuanY
 * @since 2019-07-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TVersionInfo extends Model<TVersionInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "v_seq", type = IdType.AUTO)
    private Integer vSeq;

    /**
     * 平台id
     */
    private Integer platformId;

    /**
     * 版本信息
     */
    private String versionInfo;

    /**
     * 启用标示 0不可用 1启用 2禁用
     */
    private Integer make;

    /**
     * 包url
     */
    private String pkUrl;

    private LocalDateTime creationTime;


    @Override
    protected Serializable pkVal() {
        return this.vSeq;
    }

}
