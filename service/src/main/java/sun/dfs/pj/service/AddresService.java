package sun.dfs.pj.service;

import com.github.pagehelper.PageInfo;
import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import org.springframework.web.multipart.MultipartFile;
import sun.dfs.pj.model.Addres;
import sun.dfs.pj.model.EsDao;
import sun.dfs.pj.model.User;
import java.util.List;
import java.util.Set;

public interface AddresService {
    public PageInfo<Addres> getPageAddres(User user, int current, int size);
    public PageInfo<Addres> getAllPage(User user,int current,int size);

    //    根据id获取URL
    String getUrl(int id);

    //    根据id获取所有信息
    Addres getAll(int id);

    //删除地址(逻辑删除)
    Integer delete(int id);

    //获取缩略图地址
    String getThumbnail(int id);

    //存入数据库
    int addAddres(Addres addres);

    //上传文件
    Addres UploadFile(MultipartFile multipartFile, boolean isOpen, String title,Set<MetaData> metaData);


    //获取公共文件
    PageInfo<Addres> publicPage(int current, int size);

    //es分页
    PageInfo<EsDao> esPage(int current, int size, String value, int Type,boolean open);
}
