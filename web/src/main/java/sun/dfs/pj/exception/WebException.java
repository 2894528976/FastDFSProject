package sun.dfs.pj.exception;

public class WebException extends RuntimeException {
    ResultCode resultCode;
    public WebException(ResultCode resultCode){
        this.resultCode=resultCode;
    }
    public String getMessage(){
        return resultCode.getMessage();
    }
    public int getCode(){
        return resultCode.getCode();
    }

}
