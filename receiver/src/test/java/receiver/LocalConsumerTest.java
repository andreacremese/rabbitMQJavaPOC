package receiver;

import com.rabbitmq.client.*;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class LocalConsumerTest {

    Logger mockLogger = mock(Logger.class);
    Channel mockChannel = mock(Channel.class);
    String mockConsumerTag = "mockConsumerTag";

    LocalConsumer _sut = new LocalConsumer(mockChannel, mockLogger);

    @Test
    public void shouldPrintOutTheMessage () throws java.io.IOException {
        // arrange
        String message = "Test";
        // act
        _sut.handleDelivery(mockConsumerTag, null, new AMQP.BasicProperties(), message.getBytes() );
        // assert
        String expected = " [x] Received and processed '" + message + "'";
        verify(mockLogger).print(eq(expected));
    }
}