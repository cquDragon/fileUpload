package org.fife.fileUpload.rest;

import com.alibaba.fastjson.JSONObject;
import org.fife.fileUpload.util.FileDealUtil;
import org.fife.fileUpload.util.FileUtil;
import org.fife.fileUpload.util.ResultUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {

    @PostMapping("/baseQuery")
    public String upFileByBase64(@RequestBody String base) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(base);
        String name = jsonObject.getString("name");
        String pathStr = "" ;
        if (jsonObject.get("filePath") != null && !jsonObject.getString("filePath").trim().equals("")) {
            pathStr = jsonObject.getString("filePath");
            if (!pathStr.endsWith("/")) {
                pathStr += "/";
            }
        }

        FileUtil.decoderBase64File(jsonObject.getString("baseStr"), pathStr + name);
        return "success";
    }

    @PostMapping("/merge")
    public String onMerge(@RequestBody String base){
        JSONObject jsonObject = JSONObject.parseObject(base);
        FileDealUtil.mergeFile(jsonObject.getString("filePath"),jsonObject.getString("filePath")+"/result/");
        return "success";
    }
}
