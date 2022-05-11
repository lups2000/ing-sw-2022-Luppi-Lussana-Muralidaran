package it.polimi.ingsw.Messages;

public class RequestLogin extends Message{

    private static final long serialVersionUID = -6343239452500134346L;

    public RequestLogin(String nickName){
        super(nickName,MessageType.REQUEST_LOGIN);
    }

    @Override
    public String toString() {
        return "RequestLogin{" +
                "nickName=" +getNickName()+'}';
    }
}
