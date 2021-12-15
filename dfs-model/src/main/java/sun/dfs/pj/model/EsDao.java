package sun.dfs.pj.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.crypto.Data;
@lombok.Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EsDao {
    String description;//描述
    String url; //地址
    String annotation;  //文件类型
    String fileType;  //文件后缀
    String belong; //用户名
//    Integer uid;
    boolean isOpen;  //是否公开
    String thumbnail;  //缩略图
    Data createTime; //创建时间
    int uid ; //用户id


}
