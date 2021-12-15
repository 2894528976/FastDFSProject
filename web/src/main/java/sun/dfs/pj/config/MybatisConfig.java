package sun.dfs.pj.config;

import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

//@Configuration
//@MapperScan("sun.dfs.pj.mapper")
//public class MybatisConfig implements MetaObjectHandler {
//        // 最新版
//        @Bean
//        public MybatisPlusInterceptor mybatisPlusInterceptor() {
//            MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
//            return interceptor;
//        }
//        @Override
//        public void insertFill(MetaObject metaObject) {
//            this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)
//        }
//
//        @Override
//        public void updateFill(MetaObject metaObject) {
//            this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐)
//        }
//}
