package com.soholy.service.File.impl;


import com.soholy.service.File.FileManagerService;
import com.soholy.utils.fdfs.FastDFSClient;
import com.soholy.utils.fdfs.UploadResult;
import org.apache.commons.codec.digest.DigestUtils;
import org.csource.common.NameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.security.provider.MD5;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 项目名称：dogmanager-fileManager
 * 类名称：FIleManagerService
 * 类描述：
 * 创建人：YL
 * 创建时间：2018年8月30日 下午8:44:07
 * 修改人：Administrator
 * 修改时间：2018年8月30日 下午8:44:07
 * 修改备注：
 */
@Service
public class FileManagerServiceImpl implements FileManagerService {

    @Value("${fastDfs.tracker.url}")
    private String trackerUrl;

    @Value("${fastDfs.prefix.url}")
    private String prefix;

    public List<UploadResult> upPictureBatch(HttpServletRequest request) throws Exception {
        MultipartHttpServletRequest Murequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> files = Murequest.getFileMap();//得到文件map对象


        List<UploadResult> ets = new ArrayList<>();
        for (String key : files.keySet()) {
            MultipartFile attach = files.get(key);
            String ext = attach.getOriginalFilename().substring(attach.getOriginalFilename().lastIndexOf(".") + 1);
            byte[] file = attach.getBytes();

            NameValuePair[] meta_list = new NameValuePair[4];
            meta_list[0] = new NameValuePair("fileName", attach.getOriginalFilename());
            meta_list[1] = new NameValuePair("fileLength", String.valueOf(attach.getSize()));
            meta_list[2] = new NameValuePair("fileExt", ext);
            meta_list[3] = new NameValuePair("fileAuthor", "");
            System.out.println(DigestUtils.md5Hex(file));
            try {
                FastDFSClient fastDFSClient = new FastDFSClient(Arrays.asList(trackerUrl));
                UploadResult uploadResult = new UploadResult();
                uploadResult.setUrl(prefix + "/" + fastDFSClient.uploadFile(file, ext, meta_list));
                ets.add(uploadResult);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ets;
    }

}

