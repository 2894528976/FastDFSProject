package sun.dfs.pj.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import sun.dfs.pj.model.User;
@Component
@Mapper
public interface UserMapper {
//    @Select("select * from user where username = #{username}")
    User getPasswordByUserName(String username);
}
