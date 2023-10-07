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

		ConnectionFactory factory = new ConnectionFactory();

		factory.setHost("localhost");

		try {
			Connection connection = factory.newConnection();

			Channel channel = connection.createChannel();

			channel.queueDeclare(QUEUE, false, false, false, null);

			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

			while (true) {
				
				System.out.print("Inserisci un messaggio: ");

				// Reading data using readLine
				String message = reader.readLine();

				if (message.equals("exit")) {
					break;
				}

				channel.basicPublish("", QUEUE, null, message.getBytes());
				System.out.println(" [x] Messaggio: '" + message + "' inviato. \n");
			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

}