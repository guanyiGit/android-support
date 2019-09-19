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
public class TDogOwnerCard extends Model<TDogOwnerCard> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "dog_owner_card_id", type = IdType.AUTO)
    private Long dogOwnerCardId;

    private Long dogOwnerId;

    private Integer cardType;

    private String cardNum;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDateTime creationTime;


    @Override
    protected Serializable pkVal() {
        return this.dogOwnerCardId;
    }

}
