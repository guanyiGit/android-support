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
public class TVaccineRegister extends Model<TVaccineRegister> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "vaccine_register_id", type = IdType.AUTO)
    private Long vaccineRegisterId;

    private Long dogId;

    private Integer orgId;

    private Integer injectionType;

    private Long refId;

    private String vaccineName;

    private String vaccineProducer;

    private String vaccineNum;

    private Integer vaccineCount;

    private Integer injectionIntervalDays;

    private LocalDateTime firstInjectionDate;

    private Integer schedule;

    private LocalDateTime creationTime;


    @Override
    protected Serializable pkVal() {
        return this.vaccineRegisterId;
    }

}
