package cn.yanf.webSocket;


import cn.yanf.Repository.CustomerRepository;
import cn.yanf.StringUtils.SpringUtils;
import cn.yanf.entity.Customer;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;


/** This example demonstrates how to create a websocket connection to a server. Only the most important callbacks are overloaded. */
//@RestController
//@RestController
//@EnableAutoConfiguration
	//@Component
public class ExampleClient extends WebSocketClient {
	private static String message;

	private CustomerRepository repository;

	public CustomerRepository getRepository() {
		return repository;
	}

	public void setRepository(CustomerRepository repository) {
		this.repository = repository;
	}

	public static String getMessage() {
		return message;
	}

	public static void setMessage(String message) {
		ExampleClient.message = message;
	}
	//public ExampleClient(){}
	public ExampleClient(URI serverUri , Draft draft ) {
		super( serverUri, draft );
		//this.repository=repository;
	}

	public ExampleClient( URI serverURI ) {
		super( serverURI );
	}

	@Override
	public void onOpen( ServerHandshake handshakedata ) {
		System.out.println( "opened connection" );
		// if you plan to refuse connection based on ip or httpfields overload: onWebsocketHandshakeReceivedAsClient
		this.send("{\"veId\":\"102160613134951192168402401009\",\"serviceId\":\"105151104105203192168402401002\",\"key\":\"5d4e1edf-cf1e-457f-b473-f11fa90b4e34\",\"is_atom\":\"1\",\"param\":{\"control\":\"on\",\"time\":10}}");
		this.send("{\"veId\":\"102160613134951192168402401009\",\"serviceId\":\"105151104105203192168402401005\",\"key\":\"47d53e8e-31a0-4e9a-849b-aaa6ed12b79e\",\"is_atom\":\"1\",\"param\":{\"subscribe\":\"on\"}}");

	}
	//@Autowired

	@Override
	public void onMessage( String message ) {
		//VeService veService= ContextLoaderListener.getCurrentWebApplicationContext().getBean (VeService.class);
		//System.out.println( "received: " + message );
		repository.save(new Customer("102160613134951192168402401009",message));
		//SpringUtil.doSomething(message);
		//veService.textc(message);
	}

	//@Override
	public void onFragment( Framedata fragment ) {
		System.out.println( "received fragment: " + new String( fragment.getPayloadData().array() ) );
	}

	@Override
	public void onClose( int code, String reason, boolean remote ) {
		// The codecodes are documented in class org.java_websocket.framing.CloseFrame
		System.out.println( "Connection closed by " + ( remote ? "remote peer" : "us" ) );
	}

	@Override
	public void onError( Exception ex ) {
		ex.printStackTrace();
		// if the error is fatal then onClose will be called additionally
	}

//	public static void main( String[] args ) throws URISyntaxException {
//		ExampleClient c = new ExampleClient( new URI( "ws://192.168.40.240:8880/IoT_Harbor/websocketInterface" ), new Draft_17() ); // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts
//		c.connect();
//	}

}