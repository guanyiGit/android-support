package com.soholy.entity.test;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
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
public class TDogInfo extends Model<TDogInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "dog_id", type = IdType.AUTO)
    private Long dogId;

    private Long dogOwnerId;

    private Integer dogBreedType;

    private Integer dogColorType;

    private String dogName;

    private LocalDateTime birthTime;

    /**
     * 0雄，1雌
     */
    private Integer dogGender;

    private String dogCharacter;

    private String dogRemarks;

    private Double weight;

    /**
     * 0正常，1待领养，2收容犬，3已被处理
     */
    private Integer dogStatus;

    /**
     * 注销原因
     */
    private String cancellationReason;

    /**
     * 注销备注
     */
    private String cancellationNote;

    /**
     * 注销时间
     */
    private LocalDateTime cancellationTime;

    private Double deductionSum;

    private Long fatherId;

    private Long motherId;

    /**
     * 0健康，1不健康
     */
    private Integer healthStatus;

    private Long operationId;

    private LocalDateTime creationTime;

    @TableField("dogAge")
    private Double dogAge;


    @Override
    protected Serializable pkVal() {
        return this.dogId;
    }

}
