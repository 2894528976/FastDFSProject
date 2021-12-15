package sun.dfs.pj.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RespBean {
    int status;
    String message;
    Object obj;

    RespBean(int status,String message){
        this.status = status;
        this.message = message;
    }



    public void setObj(Object obj) {
        this.obj = obj;
    }
    public static RespBean NOT_LOGIN(String message){
        return new RespBean(401,message);
    }
    public static RespBean OK(String message){
        return new RespBean(200,message);
    }
    public static RespBean OK(String message,Object obj){
        return new RespBean(200,message,obj);
    }
    public static RespBean ERROR(String message,Object obj){
        return new RespBean(500,message,obj);
    }
    public static RespBean ERROR(String message){
        return new RespBean(500,message);
    }
}
