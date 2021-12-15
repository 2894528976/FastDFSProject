package sun.dfs.pj.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Outher {
//    @TableId(type = IdType.AUTO)
    int id;
//    @TableField(fill = FieldFill.INSERT)
    Date createTime;
    Date deleteTime;
//    @TableField(fill = FieldFill.UPDATE)
    Date updateTime;

}
