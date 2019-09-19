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
public class TVaccineInjection extends Model<TVaccineInjection> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "vaccine_injection_id", type = IdType.AUTO)
    private Long vaccineInjectionId;

    private Long vaccineRegisterId;

    private Integer vaccineInjectionCount;

    private LocalDateTime injectionDate;

    private LocalDateTime factInjectionDate;

    private LocalDateTime nextInjectionDate;

    private Long injectionStatus;

    private Long operatorId;

    private LocalDateTime createDate;

    private String remarks;

    private String vaccineName;

    private String vaccineProducer;

    private String vaccineNum;

    private Integer send;

    private String veterinarian;

    /**
     * 机构id
     */
    private Integer vacOrg;

    private Long dogId;


    @Override
    protected Serializable pkVal() {
        return this.vaccineInjectionId;
    }

    public void setVeterinarian(String veterinarian) {
        this.veterinarian =veterinarian;
    }
}
