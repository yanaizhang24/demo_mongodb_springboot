package cn.yanf.webSocket;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * Created by Administrator on 2016/8/11 0011.
 */
public class WebSocketC extends WebSocketClient {
    public WebSocketC(URI serverURI){
        super(serverURI);
    }

    @Override
    public void onClose(int arg0,String arg1,boolean remote){
        System.out.println("Connection closed by "+(remote?"remote peer":"us"));
    }

    @Override
    public void onError(Exception e){
        e.printStackTrace();
    }
    @Override
    public void onMessage(String message){
        System.out.println("received:"+message);
    }
    @Override
    public void onOpen(ServerHandshake arg0){
        System.out.println("opened connection");
        //{"veId":"102160613134951192168402401009","serviceId":"105151104105203192168402401002","key":"5d4e1edf-cf1e-457f-b473-f11fa90b4e34","is_atom":"1","param":{"control":"on","time":10}}
        //{"veId":"102160613134951192168402401009","serviceId":"105151104105203192168402401005","key":"47d53e8e-31a0-4e9a-849b-aaa6ed12b79e","is_atom":"1","param":{"subscribe":"on"}}
        this.send("{\"veId\":\"102160613134951192168402401009\",\"serviceId\":\"105151104105203192168402401002\",\"key\":\"5d4e1edf-cf1e-457f-b473-f11fa90b4e34\",\"is_atom\":\"1\",\"param\":{\"control\":\"on\",\"time\":10}}");
        this.send("{\"veId\":\"102160613134951192168402401009\",\"serviceId\":\"105151104105203192168402401005\",\"key\":\"47d53e8e-31a0-4e9a-849b-aaa6ed12b79e\",\"is_atom\":\"1\",\"param\":{\"subscribe\":\"on\"}}");
    }
    //public void onFragment(Framedata fragment){}
//    public static void main(String[] args )throws URISyntaxException{
//        WebSocketC c=new WebSocketC(new URI("ws://192.168.40.240:8880/IoT_Harbor/websocketInterface"));
//        c.connect();
//    }
}
