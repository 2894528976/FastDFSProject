package sun.dfs.pj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.dfs.pj.mapper.UserMapper;
import sun.dfs.pj.model.User;
import sun.dfs.pj.service.UserService;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    public User getUserByUserName(String username){
        User user = userMapper.getPasswordByUserName(username);
        return user;
    }

}
