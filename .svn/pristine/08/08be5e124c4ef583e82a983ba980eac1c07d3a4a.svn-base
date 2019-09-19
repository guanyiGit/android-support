package com.soholy.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soholy.entity.test.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author GuanY
 * @since 2019-07-13
 */
public interface TDogOwnerMapper extends BaseMapper<TDogOwner> {


    Long saveOwner(@Param("owner") TDogOwner owner);

    Long saveOwnerCard(@Param("ownerCard") TDogOwnerCard ownerCard);

    List<TDictionary> finddDogTypeName(@Param("dogTypeName") String dogTypeName);

    List<TDictionary> finddDics(@Param("type") String type);

    Long saveDogInfo(@Param("dogInfo") TDogInfo dogInfo);

    Integer saveImmuneCard(@Param("immuneCard") TImmuneCard immuneCard);

    Long saveVaccineInjection(@Param("injection") TVaccineInjection injection);

    List<TDogOwner> findOwnerByPhone(@Param("phone") String phone);

}
