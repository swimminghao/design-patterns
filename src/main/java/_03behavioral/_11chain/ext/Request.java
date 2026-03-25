package _03behavioral._11chain.ext;


import lombok.Data;

@Data
public class Request {

    String msg; //请求内容
    public Request(String msg){
        this.msg = msg;
    }


}
