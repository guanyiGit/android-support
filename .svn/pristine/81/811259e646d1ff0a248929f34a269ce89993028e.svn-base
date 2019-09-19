package com.soholy.controller;


import com.soholy.service.PLViewService;
import com.soholy.utils.R;
import com.soholy.utils.ReqPage;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author GuanY
 * @since 2019-07-11
 */
@Controller
@Log
public class PLViewController {

    @Autowired
    private PLViewService plViewService;

    @Autowired
    private HttpServletRequest request;

    @Value("${admin.ips}")
    private String ipInfos;

    private boolean ipIntercept() {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        log.warning(ip);
        final String fromIp = ip;
        return Arrays.stream(ipInfos.replaceAll("-", "").split(" ")).anyMatch(x -> x.equals(fromIp));
    }


    @GetMapping("/view")
    String view() {
        if (!ipIntercept()) {
            return "redirect:/";
        }
        return "view";
    }

    @GetMapping("/view/upload")
    String viewUpload() {
        if (!ipIntercept()) {
            return "redirect:/";
        }
        return "upload";
    }


    /**
     * 查询平台列表
     *
     * @param reqPage
     * @param plSeq   平台id
     * @param make    启用标示 0不可用 1启用 2禁用
     * @return
     */
    @GetMapping("/versions")
    @ResponseBody
    R versions(ReqPage.QReqPage reqPage, @RequestParam(required = false) Integer plSeq,
               @RequestParam(required = false) Integer make) {
        try {
            return R.ok(plViewService.findVersionList(reqPage, plSeq, make));
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }


    /**
     * 查询平台列表
     *
     * @return
     */
    @GetMapping("/pletforms")
    @ResponseBody
    R pletforms() {
        try {
            return R.ok(plViewService.findPletforms());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }

    /**
     * 新增包
     *
     * @param pkUrl      包的url
     * @param platformId 平台id
     */
    @PostMapping("/pk/save")
    @ResponseBody
    public R save(@RequestParam String pkUrl, @RequestParam Integer platformId) {
        try {
            return R.ok(plViewService.save(pkUrl, platformId));
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }


    /**
     * 修改版本信息的状态
     *
     * @param vSeq 版本id
     * @param stat 状态 1启用 2禁用
     * @return
     */
    @PutMapping("/pk/{vSeq}")
    @ResponseBody
    public R modify(@PathVariable Integer vSeq, @RequestParam Integer stat) {
        try {
            if (plViewService.modifyMakeByVSeq(vSeq, stat)) return R.ok();
            else return R.error();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }


    /**
     * 根据id删除版本信息
     *
     * @param vSeq 版本id
     * @return
     */
    @DeleteMapping("/pk/{vSeq}")
    @ResponseBody
    public R del(@PathVariable Integer vSeq) {
        try {
            if (plViewService.removeByVSeq(vSeq)) return R.ok();
            else return R.error();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }

}

