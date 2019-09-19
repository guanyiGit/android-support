package com.soholy.controller;


import com.soholy.service.TVersionInfoServer;
import com.soholy.utils.R;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/platform")
public class TVersionInfoController {

    @Autowired
    private TVersionInfoServer tVersionInfoServer;

    /**
     * 校验并查询平台版本信息
     *
     * @param version   版本信息
     * @param checkType 1根据平台id检查 2 根据平台名称检查  默认查询id
     * @param platform  查询的条件（平台的名称或平台的id）
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", value = "校验版本", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "checkType", value = "1根据平台id检查 2 根据平台名称检查", paramType = "query", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "platform", value = "需校验的平台信息（平台的名称或平台的id）", required = true, paramType = "query", dataType = "int"),
    })
    @ApiOperation(value = "校验平台版本信息", notes = "校验并查询平台版本信息")
    @GetMapping("/check")
    public R check(@RequestParam Integer version,
                   @RequestParam(required = false, defaultValue = "1") Integer checkType,
                   @RequestParam String platform) {
        try {
            if (version == null || StringUtils.isBlank(platform)) {
                throw new RuntimeException("参数错误");
            }
            return R.ok(tVersionInfoServer.checkVersion(version, checkType, platform));
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }


    /**
     * 查询所有平台信息
     *
     * @return
     */
    @ApiOperation(value = "查询所有平台信息", notes = "查询所有平台信息")
    @GetMapping("/list")
    public R list() {
        try {
            return R.ok(tVersionInfoServer.findPlatforms());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }

}

