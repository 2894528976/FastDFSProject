package sun.dfs.pj.service;

import org.springframework.stereotype.Service;
import sun.dfs.pj.model.User;


public interface UserService {
    public User getUserByUserName(String username);

}
