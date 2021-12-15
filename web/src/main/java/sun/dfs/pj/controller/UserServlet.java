package sun.dfs.pj.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.dfs.pj.mapper.UserMapper;
import sun.dfs.pj.model.User;
import sun.dfs.pj.service.UserService;

@RestController()
@RequestMapping("/userSetting")
@CrossOrigin
public class UserServlet {
    @Autowired
    UserService userService;
    @GetMapping
    @ApiOperation("获取用户")
    public User getuser(@ApiParam(value = "接受一个用户名")@RequestParam("username") String username){
        User userByUserName = userService.getUserByUserName(username);
        return userByUserName;
    }
    @DeleteMapping
    public User deleteUser(){
        return null;
    }
    @PutMapping
    public User putUser(){
        return null;
    }
    @PostMapping
    public User PostUser(){
        return null;
    }
}
