package main;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;

public class Send {

	private final static String QUEUE = "MyMessages";

	public static void main(String[] args) throws IOException, TimeoutException {

		// Creating a Connection Factory
		ConnectionFactory factory = new ConnectionFactory();

		// Setting the host into the Connection Factory
		factory.setHost("localhost");

		try {
			
			// Creating a Connection with localhost host
			Connection connection = factory.newConnection();

			// Creating a Channel for insert messages
			Channel channel = connection.createChannel();

			// Declaring a queue for the channel
			channel.queueDeclare(QUEUE, false, false, false, null);

			//Create a reader
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

			while (true) {
				
				System.out.print("Inserisci un messaggio: ");

				// Reading data using readLine
				String message = reader.readLine();

				if (message.equals("exit")) {
					break;
				}

				// Sending the message into the queue of the channel
				channel.basicPublish("", QUEUE, null, message.getBytes());
				
				System.out.println(" [x] Messaggio: '" + message + "' inviato. \n");
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}