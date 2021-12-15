package sun.dfs.pj.mapper;

;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import sun.dfs.pj.model.Addres;

import java.util.List;

@Component
@Mapper
//public interface AddresMapper extends BaseMapper<Addres> {
public interface AddresMapper {
//    select("url");
    public String getUrl(int id);
    public Addres selectById(int id);
    int deleteById(int id);
    int insert(Addres addres);
    List<Addres> getPublicPage();
    List<Addres> getUserPage(String name);
}
