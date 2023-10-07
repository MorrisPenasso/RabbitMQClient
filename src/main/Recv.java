package main;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Recv {

	private  final  static  String  QUEUE  =  "MyMessages" ;

	  public  static  void  main (String[] argv) throws IOException, TimeoutException {
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();
	    channel.queueDeclare(QUEUE, false, false, false, null);
	    
	    System.out.println(" [*] In attesa di messaggi. Per uscire premere CTRL+C");
	    
	    DeliverCallback deliverCallback = (consumerTag, delivery) -> {
	    	
	        String  message = new String(delivery.getBody(), "UTF-8" );
	        
	        System.out.println( " [x] Ricevuto '" + message + "'" );
	   };
	   
	   channel.basicConsume(QUEUE, true , deliverCallback, consumerTag -> { });

	  }

}
