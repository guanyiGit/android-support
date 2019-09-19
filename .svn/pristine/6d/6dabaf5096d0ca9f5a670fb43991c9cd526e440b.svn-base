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
public class TDogOwner extends Model<TDogOwner> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "dog_owner_id", type = IdType.AUTO)
    private Long dogOwnerId;

    private Long districtId;

    private String community;

    /**
     * 0个体户，1企业
     */
    private Integer dogOwnerType;

    private String dogOwnerName;

    private String dogOwnerPhone;

    private String address;

    /**
     * 0男，1女
     */
    private Integer sex;

    private String eMail;

    private String postalCode;

    private Integer status;

    private LocalDateTime creationTime;

    private LocalDateTime birthDate;

    private String ethnic;

    private Integer orgId;

    private Long userId;


    @Override
    protected Serializable pkVal() {
        return this.dogOwnerId;
    }

}
