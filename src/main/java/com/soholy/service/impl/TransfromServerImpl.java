package com.soholy.service.impl;

import com.soholy.entity.test.*;
import com.soholy.exception.RspException;
import com.soholy.mapper.TDogOwnerMapper;
import com.soholy.service.TransfromServer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransfromServerImpl implements TransfromServer {


    private List<TDictionary> breedTypeDics;

    private List<TDictionary> colorDics;

    private static LocalDateTime NOW = LocalDateTime.now();

    private static List<DateTimeFormatter> dtfs;

    static {
        dtfs = Arrays.asList(DateTimeFormatter.ofPattern("yyyy/MM/dd"), DateTimeFormatter.ofPattern("yyyyMMdd"), DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }

    @Autowired
    private TDogOwnerMapper tDogOwnerMapper;


    @Override
    @Transactional
    public void transfrom(Map<Integer, String> rowMap, boolean isIgnoreRepetOwner) {
        if (breedTypeDics == null) breedTypeDics = tDogOwnerMapper.finddDics("dog_breed_type");
        if (colorDics == null) colorDics = tDogOwnerMapper.finddDics("dog_color_type");
        int callCount = 0;
        //犬主电话	犬主姓名	犬主身份证号	社区	犬主地址	犬名	犬品种	犬龄（月）	免疫证号	疫苗名称	疫苗生产单位	免疫医生	免疫日期	宠物医院id
        String phone = rowMap.get(callCount++);//犬主电话
        if (StringUtils.isBlank(phone)) {
            throw new RspException("犬主电话不存在");
        }
        phone = StringUtils.isNoneBlank(phone) ? phone.trim() : phone;
        String ownerName = rowMap.get(callCount++);//犬主姓名
        ownerName = StringUtils.isNoneBlank(ownerName) ? ownerName.trim() : ownerName;
        String identityCard = rowMap.get(callCount++);//犬主身份证号
        identityCard = StringUtils.isNoneBlank(identityCard) ? identityCard.trim() : identityCard;
        String community = rowMap.get(callCount++);//社区
        community = StringUtils.isNoneBlank(community) ? community.trim() : community;
        String address = rowMap.get(callCount++);//犬主地址
        address = StringUtils.isNoneBlank(address) ? address.trim() : address;
        String dogName = rowMap.get(callCount++);//犬名
        dogName = StringUtils.isNoneBlank(dogName) ? dogName.trim() : dogName;
        String dogTypeName = rowMap.get(callCount++);//犬品种
        dogTypeName = StringUtils.isNoneBlank(dogTypeName) ? dogTypeName.trim() : dogTypeName;

        Integer dogBreedType = null;
        if (StringUtils.isNoneBlank(dogTypeName)) {
            String likeName = dogTypeName;
            List<TDictionary> likeNames = breedTypeDics.stream()
                    .filter(Objects::nonNull)
                    .filter(x -> StringUtils.isNoneBlank(x.getDictionaryDescribe()))
                    .filter(x -> x.getDictionaryDescribe().indexOf(likeName) != -1)
                    .collect(Collectors.toList());
            if (likeNames != null && likeNames.size() == 1) {
                dogBreedType = likeNames.get(0).getDictionaryValue();
            }
        }
        if (dogBreedType == null) {
            throw new RspException("检索犬品种不存在、或者存在多个");
        }

        String __dogAge = StringUtils.isNoneBlank(rowMap.get(callCount)) ? rowMap.get(callCount++).trim() : rowMap.get(callCount++);
        Integer dogAge = null;//犬龄（月）
        LocalDateTime birthDate = null;//犬出生日期
        if (StringUtils.isNoneBlank(__dogAge)) {
            LocalDateTime parseTime = parseStr2Date(__dogAge);
            if (null != parseTime) {
                birthDate = parseTime;
                Period period = Period.between(birthDate.toLocalDate(), NOW.toLocalDate());
                dogAge = period.getMonths();
            } else {
                dogAge = Integer.parseInt(__dogAge);
                birthDate = NOW.minusMonths(dogAge);
            }
        }

        String immuneCardNum = rowMap.get(callCount++);//免疫证号
        immuneCardNum = StringUtils.isNoneBlank(immuneCardNum) ? immuneCardNum.trim() : immuneCardNum;

        String vaccineName = rowMap.get(callCount++);//疫苗名称
        vaccineName = StringUtils.isNoneBlank(vaccineName) ? vaccineName.trim() : vaccineName;
        String vaccineProducer = rowMap.get(callCount++);//疫苗生产单位
        vaccineProducer = StringUtils.isNoneBlank(vaccineProducer) ? vaccineProducer.trim() : vaccineProducer;
        String doctor = rowMap.get(callCount++);//免疫医生
        doctor = StringUtils.isNoneBlank(doctor) ? doctor.trim() : doctor;
        String __vaccineTime = StringUtils.isNoneBlank(rowMap.get(callCount)) ? rowMap.get(callCount++).trim() : rowMap.get(callCount++);
        LocalDateTime vaccineTime = null;//免疫日期
        if (StringUtils.isNoneBlank(__vaccineTime)) {
            LocalDateTime parseTime = parseStr2Date(__vaccineTime);
            if (parseTime == null) {
                throw new RspException("时间解析失败");
            }
            vaccineTime = parseTime;

        }
        Integer hospitalId = null;//宠物医院id
        hospitalId = StringUtils.isNoneBlank(rowMap.get(callCount)) ? Integer.valueOf(rowMap.get(callCount++).trim()) : null;


        //扩张字段 （毛色	犬性别）
        String colorStr = rowMap.get(callCount++);//犬毛色
        colorStr = StringUtils.isNoneBlank(colorStr) ? colorStr.trim() : colorStr;
        Integer dogColorType = null;
        if (StringUtils.isNoneBlank(colorStr)) {
            String _colorStr = colorStr;
            List<TDictionary> likeColors = colorDics.stream()
                    .filter(Objects::nonNull)
                    .filter(x -> StringUtils.isNoneBlank(x.getDictionaryDescribe()))
                    .filter(x -> x.getDictionaryDescribe().contains(_colorStr))
                    .collect(Collectors.toList());
            if (likeColors != null && likeColors.size() == 1) {
                dogColorType = likeColors.get(0).getDictionaryValue();
            }
        }

        String dogGenderStr = rowMap.get(callCount++);//犬性别   0雄，1雌
        dogGenderStr = StringUtils.isNoneBlank(dogGenderStr) ? dogGenderStr.trim() : dogGenderStr;
        Integer dogGender = null;
        if (StringUtils.isNoneBlank(dogGenderStr)) {
            switch (dogGenderStr) {
                case "公":
                    dogGender = 0;
                    break;
                case "雄":
                    dogGender = 0;
                    break;
                case "母":
                    dogGender = 1;
                    break;
                case "雌":
                    dogGender = 1;
                    break;
            }
        }


        List<TDogOwner> dogOwners = tDogOwnerMapper.findOwnerByPhone(phone);
        TDogOwner owner = null;
        if (dogOwners != null) {
            int ownerSize = dogOwners.size();
            if (isIgnoreRepetOwner && ownerSize > 0) {
                throw new RspException("犬主已存在->忽略添加！");
            }
            if (ownerSize == 1) {
                owner = dogOwners.get(0);
            }
            if (dogOwners.size() > 1) {
                throw new RspException("犬主信息无法确定");
            }
        }
        if (owner == null) {
            //犬主信息
            owner = new TDogOwner();
            owner.setCreationTime(NOW);
            owner.setStatus(0);
            owner.setDogOwnerName(ownerName);
            owner.setCommunity(community);
            owner.setAddress(address);
            owner.setDogOwnerPhone(phone);
            owner.setDogOwnerType(0);
            owner.setOrgId(hospitalId);
            owner.setDogOwnerId(tDogOwnerMapper.saveOwner(owner));
            if (owner == null || owner.getDogOwnerId() == null) {
                throw new RspException("犬主信息插入失败");
            }

            //犬主证件信息
            TDogOwnerCard ownerCard = new TDogOwnerCard();
            ownerCard.setCardNum(identityCard);
            ownerCard.setCardType(0);
            ownerCard.setCreationTime(NOW);
            ownerCard.setDogOwnerId(owner.getDogOwnerId());
            ownerCard.setDogOwnerCardId(tDogOwnerMapper.saveOwnerCard(ownerCard));
            if (ownerCard == null || ownerCard.getDogOwnerCardId() == null) {
                throw new RspException("犬主证件信息插入失败");
            }
        }

        //犬信息
        TDogInfo dogInfo = new TDogInfo();
        dogInfo.setCreationTime(NOW);
        if (dogAge != null)
            dogInfo.setDogAge(Double.valueOf(dogAge));
        dogInfo.setDogName(dogName);
        dogInfo.setBirthTime(birthDate);
        dogInfo.setDogStatus(0);
        dogInfo.setDogColorType(dogColorType);
        dogInfo.setDogGender(dogGender);

        dogInfo.setDogBreedType(dogBreedType);
        dogInfo.setDogOwnerId(owner.getDogOwnerId());
        dogInfo.setDogId(tDogOwnerMapper.saveDogInfo(dogInfo));
        if (dogInfo == null || dogInfo.getDogId() == null) {
            throw new RspException("犬主证件信息插入失败");
        }

        if (StringUtils.isNoneBlank(immuneCardNum)) {
            TImmuneCard immuneCard = new TImmuneCard();
            immuneCard.setCreationTime(NOW);
            immuneCard.setDogId(dogInfo.getDogId());
            immuneCard.setDogOwnerId(owner.getDogOwnerId());
            immuneCard.setImmuneCardNum(immuneCardNum);
            immuneCard.setStatus(1);
            immuneCard.setLssueOrgId(hospitalId);
            immuneCard.setSeqId(Long.valueOf(tDogOwnerMapper.saveImmuneCard(immuneCard)));
            if (immuneCard == null || immuneCard.getSeqId() == null) {
                throw new RspException("犬主证件信息插入失败");
            }
        }

        TVaccineInjection injection = new TVaccineInjection();
        injection.setInjectionDate(vaccineTime);
        injection.setCreateDate(NOW);
        injection.setDogId(dogInfo.getDogId());
        injection.setInjectionStatus(0l);
        injection.setVaccineName(vaccineName);
        injection.setOperatorId(1l);
        injection.setVaccineProducer(vaccineProducer);
        injection.setVacOrg(hospitalId);
        injection.setVeterinarian(doctor);
        injection.setVaccineInjectionId(tDogOwnerMapper.saveVaccineInjection(injection));
        if (injection == null || injection.getVaccineInjectionId() == null) {
            throw new RspException("犬主证件信息插入失败");
        }
    }

    private static LocalDateTime parseStr2Date(String in) {
        String[] tokens = new String[]{".", "/"};
        if (StringUtils.isNoneBlank(in)) {
            Optional<String> parseTostr = Arrays.stream(tokens).filter(in::contains)
                    .map(x -> {
                        try {
                            String[] strs = StringUtils.split(in, x);
                            if (strs[0].length() == 2) {
                                strs[0] = "20" + strs[0];
                            }
                            if (strs[1].length() != 2) {
                                for (int i = 0; i < 2 - strs[1].length(); i++) {
                                    strs[1] = "0" + strs[1];
                                }
                            }
                            if (strs.length == 2) {
                                strs = new String[]{strs[0], strs[1], "00"};
                            }
                            if (strs[2].length() != 2) {
                                for (int i = 0; i < 2 - strs[2].length(); i++) {
                                    strs[2] = "0" + strs[2];
                                }
                            }
                            String reduce = Arrays.stream(strs).reduce("", (x1, y1) -> x1 + "/" + y1);
                            return StringUtils.startsWith(reduce, "/") ? StringUtils.substring(reduce, 1) : reduce;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }).filter(Objects::nonNull).findFirst();

            if (!parseTostr.isPresent()) {
                if (in.length() == 8) {
                    parseTostr = Optional.of(in);
                } else if (in.length() == 6) {
                    parseTostr = Optional.of("20" + in);
                } else if (in.length() == 4) {
                    parseTostr = Optional.of("2019" + in);
                }
            }
            if (parseTostr.isPresent()) {
                String parseStr = parseTostr.get();
                List<DateTimeFormatter> cur_dtf = dtfs.stream()
                        .filter(z -> {
                            try {
                                LocalDate.parse(parseStr, z);
                                return true;
                            } catch (Exception e) {
                            }
                            return false;
                        }).collect(Collectors.toList());
                return cur_dtf != null && cur_dtf.size() == 1 ? LocalDate.parse(parseStr, cur_dtf.get(0)).atStartOfDay(ZoneId.systemDefault()).toLocalDateTime() : null;

            }
        }
        return null;
    }

}
