package receiver;

import com.rabbitmq.client.Channel;
import receiver.services.Logger;

import static org.mockito.Mockito.*;

public class IntegrationTest {

    // will still mock the logger
    Logger mockLogger = mock(Logger.class);
    Channel mockChannel = mock(Channel.class);
    String mockConsumerTag = "mockConsumerTag";


//    @Test
//    public void canSaveToMemcache() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
//        // arrange
//
//        //when(mockChannel.queueDeclare()).thenReturn;
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("127.0.0.1");
//        Connection connection = factory.newConnection();
//        final Channel channel = connection.createChannel();
//
//        MemcachedClient memClient = new MemcachedClient(new InetSocketAddress("localhost", 11211));
//        String message = "{\"key\":\"some\",\"value\":\"number\"}";
//        Subscription.UPDATE_CACHE.startListener(channel, new Storage("127.0.0.1", 11211),mockLogger);
//        // act
//        Subscription.UPDATE_CACHE.getConsumer().handleDelivery(mockConsumerTag, null, new AMQP.BasicProperties(), message.getBytes() );
//        // assert
//        CacheItem result = new ObjectMapper().readValue(memClient.get("some").toString(), CacheItem.class);
//        assert(result.value).equals("number");
//    }
}
