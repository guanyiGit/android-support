package com.soholy.entity.test;

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
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TImmuneCard extends Model<TImmuneCard> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "seq_id", type = IdType.AUTO)
    private Long seqId;

    private Long dogId;

    private Long dogOwnerId;

    private Long memberCardId;

    private String immuneCardNum;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDateTime lssueTime;

    private Integer lssueOrgId;

    private Long lssuerId;

    private Integer status;

    private LocalDateTime creationTime;


    @Override
    protected Serializable pkVal() {
        return this.seqId;
    }

}
