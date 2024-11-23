package org.example.airport.utils;

public class Result<T> {
    //code
    private String code;
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    //msg
    private String msg;
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    //token
    private String token;
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    //data
    private T data;
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    //url
    private String url;
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }


    //构造成功与失败结果
    public Result() {
    }
    //构造函数，参数为data
    public Result(T data) {
        this.data = data;
    }


    //success
    //构造函数，参数为data、msg、url
    public static <T> Result<T> success(T data,String msg,String url) {
        Result<T> result = new Result<>(data);
        result.setCode("0");
        result.setMsg(msg);
        result.setUrl(url);
        return result;
    }
    //构造函数，参数为msg、url
    public static Result success(String msg,String url){
        Result result = new Result<>();
        result.setCode("0");
        result.setMsg(msg);
        result.setUrl(url);
        return result;
    }


    //error
    //构造函数，参数为msg、url
    public static Result error(String msg,String url){
        Result result = new Result<>();
        result.setCode("1");
        result.setMsg(msg);
        result.setUrl(url);
        return result;
    }
    //构造函数，参数为和data、msg、url
    public static <T> Result<T>  error(T data,String msg, String url) {
        Result result = new Result<>();
        result.setCode("1");
        result.setMsg(msg);
        result.setUrl(url);
        return result;
    }
}

