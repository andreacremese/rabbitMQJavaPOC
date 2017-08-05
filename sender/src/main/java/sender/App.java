package sender;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.fasterxml.jackson.databind.*;


import messages.UpdateChacheMessage;

public class App  {
    private final static String EXCHANGE_NAME = "updated_cache";

    public static void main(String[] args) throws java.io.IOException {
        boolean durable = true;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        messages.UpdateChacheMessage msg = new messages.UpdateChacheMessage();
        msg.key = args[0];
        msg.value = args[1];

        //String message = "";
        ObjectMapper mapper = new ObjectMapper();
        String message = mapper.writeValueAsString(msg);

        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }

}
