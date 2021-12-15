package sun.dfs.pj.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import sun.dfs.pj.model.User;

public class UsersUtils {
    public static User getUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
