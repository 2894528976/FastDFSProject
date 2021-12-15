package sun.dfs.pj.exception;

public enum  ResultCode {
    //token令牌过期
    TOKEN_EXPIRES(333,"token过期了哦"),
    //    验证码错误
    CODE_ERROR(420,"验证码错误"),
    //    token其他错误
    TOKEN_NULL(334,"token其他错误"),
    //    token其他错误
    FILE_NOT_FOUNT(550,"文件未找到,或已删除"),
    ;
    private int code;
    private String message;
    ResultCode(int code,String message){
        this.code=code;
        this.message=message;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
