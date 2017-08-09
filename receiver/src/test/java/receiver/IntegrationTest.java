package receiver;

import CacheDTOs.CacheItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import net.spy.memcached.MemcachedClient;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;

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
//
//
//        MemcachedClient memClient = new MemcachedClient(new InetSocketAddress("localhost", 11211));
//        String message = "{\"key\":\"some\",\"value\":\"number\"}";
//        Subscription.UPDATE_CACHE.startListener(mockChannel, new Storage("127.0.0.1", 11211),mockLogger);
//        // act
//        Subscription.UPDATE_CACHE.getConsumer().handleDelivery(mockConsumerTag, null, new AMQP.BasicProperties(), message.getBytes() );
//        // assert
//        CacheItem result = new ObjectMapper().readValue(memClient.get("some").toString(), CacheItem.class);
//        assert(result.value).equals("number");
//    }
}
