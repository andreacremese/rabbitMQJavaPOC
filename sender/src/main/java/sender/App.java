package sender;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.fasterxml.jackson.databind.*;

public class App  {
    private final static String UPDATE_EXCHANGE_NAME = "updated_cache";
    private final static String DELETE_EXCHANGE_NAME = "delete_cache";

    public static void main(String[] args) throws java.io.IOException {
        boolean durable = true;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


        channel.exchangeDeclare(UPDATE_EXCHANGE_NAME, "fanout");
        channel.exchangeDeclare(DELETE_EXCHANGE_NAME, "fanout");

        if (args.length == 2) {
            messages.UpdateCacheMessage msg = new messages.UpdateCacheMessage();
            msg.key = args[0];
            msg.value = args[1];

            //String message = "";
            ObjectMapper mapper = new ObjectMapper();
            String message = mapper.writeValueAsString(msg);

            channel.basicPublish(UPDATE_EXCHANGE_NAME, "", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "' to exchange" + UPDATE_EXCHANGE_NAME);
        } else {
            messages.DeleteCacheMessage msg = new messages.DeleteCacheMessage();
            msg.key = args[0];

            //String message = "";
            ObjectMapper mapper = new ObjectMapper();
            String message = mapper.writeValueAsString(msg);

            channel.basicPublish(DELETE_EXCHANGE_NAME, "", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + " to exchage '" + DELETE_EXCHANGE_NAME);

        }

        channel.close();
        connection.close();
    }

}
