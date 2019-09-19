package com.soholy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.soholy.pojo.VersionRefPl;
import com.soholy.service.PLViewService;
import com.soholy.utils.R;
import com.soholy.utils.ReqPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/down")
public class DownloadController {

    @Autowired
    private PLViewService plViewService;


    @GetMapping("/sz")
    public String downSZ(Model model) {
        Page<VersionRefPl> versionList = plViewService.findVersionList(new ReqPage.QReqPage(), 1, null);
        if(versionList!= null && versionList.getRecords()!= null ){
            model.addAttribute("download",versionList.getRecords().stream().filter(x->1== x.getMake()).collect(Collectors.toList()));
        }
        return "download";
    }

}
