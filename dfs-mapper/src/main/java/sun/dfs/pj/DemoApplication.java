package sun.dfs.pj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import sun.dfs.pj.mapper.UserMapper;
import sun.dfs.pj.model.User;
//import sun.dfs.pj.mapper.UserMapperImp;

@SpringBootApplication
@MapperScan(basePackages = {"sun.dfs.pj.mapper"})
public class DemoApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(DemoApplication.class, args);
        UserMapper userMapper = run.getBean(UserMapper.class);
        User aa = userMapper.getPasswordByUserName("root");
        System.out.println(aa);
//        System.out.println(userMapper);
    }

}
