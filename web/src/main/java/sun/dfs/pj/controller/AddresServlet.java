package sun.dfs.pj.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.dfs.pj.model.Addres;
import sun.dfs.pj.model.EsDao;
import sun.dfs.pj.model.RespBean;
import sun.dfs.pj.model.User;
import sun.dfs.pj.service.AddresService;
import sun.dfs.pj.utils.UsersUtils;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class AddresServlet {
    @Autowired
    AddresService addresService;

    @PostMapping("/addres")
    public RespBean upload(@RequestParam(value = "file") MultipartFile[] multipartFile,
                           @RequestParam(value = "radio") boolean radio,
                           @RequestParam(value = "fileList") String fileList
    ) {
        JSONArray objects = JSON.parseArray(fileList);
        for (int i = 0; i < multipartFile.length; i++) {
            String string = objects.getString(i);
            JSONObject jsonObject = JSON.parseObject(string);
            Map<String, Object> innerMap = jsonObject.getInnerMap();
            string = ((String) innerMap.get("value")).trim();
            if ("".equals(string) || string == "") {
                string = ((String) innerMap.get("name")).trim();
                string = string.substring(0, string.lastIndexOf("."));
            }
            addresService.UploadFile(multipartFile[i], radio, string, null);
        }
        RespBean suc = RespBean.OK("上传成功");
        return suc;
    }

    @GetMapping("/addres/search")
    public RespBean getaddres(@RequestParam(value = "current") Integer current,
                              @RequestParam(value = "size") Integer size,
                              @RequestParam(value = "value") String value,
                              @RequestParam(value = "open") boolean open,
                              @RequestParam(value = "type") int type){
        PageInfo<EsDao> esDaoPageInfo = addresService.esPage(current, size, value, type,open);
        RespBean ok = RespBean.OK("查询成功!!", esDaoPageInfo);
        return ok;
    }

    /**
     * 获取地址分页
     *
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/addres")
    public RespBean getaddres(@RequestParam(value = "current") Integer current, @RequestParam(value = "size") Integer size) {
        log.info("用户: " + UsersUtils.getUser().getUsername() + "获取私有文件");
        User us = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PageInfo<Addres> pageAddres = addresService.getPageAddres(us, current, size);
        RespBean suc = RespBean.OK("获取文件成功", pageAddres);
        return suc;
    }

    /**
     * 获取公共地址分页
     *
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/addres/public")
    public RespBean getaddresPublic(@RequestParam(value = "current") Integer current, @RequestParam(value = "size") Integer size) {
        log.info("用户: " + UsersUtils.getUser().getUsername() + "获取公共文件");
        User us = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PageInfo<Addres> allPage = addresService.getAllPage(us, current, size);
        RespBean suc = RespBean.OK("获取文件成功", allPage);
        return suc;
    }
}
