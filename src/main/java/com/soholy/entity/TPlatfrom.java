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
public class TPlatfrom extends Model<TPlatfrom> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "pl_seq", type = IdType.AUTO)
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

    private LocalDateTime creationTime;


    @Override
    protected Serializable pkVal() {
        return this.plSeq;
    }

}
