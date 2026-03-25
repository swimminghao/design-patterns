package _03behavioral._11chain.ext;

import lombok.Data;

@Data
public class Response {
    String content;
    public Response(String content){
        this.content = content;
    }
}
