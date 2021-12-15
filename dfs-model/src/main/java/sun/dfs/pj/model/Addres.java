package sun.dfs.pj.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Addres {
//    @TableId(type = IdType.AUTO)
    int id;
    String url;
    String annotation;
//    @TableLogic
    int deleted;
    Integer outher;
    String fileType;
    String belong;
    boolean isOpen;
//    @TableField(fill = FieldFill.INSERT)
    Date createTime;

    String description;
    String thumbnail;
    int uid;

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
