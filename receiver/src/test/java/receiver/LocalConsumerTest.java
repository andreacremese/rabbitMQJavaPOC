package receiver;

import com.rabbitmq.client.*;
import messages.UpdateChacheMessage;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class LocalConsumerTest {

    Logger mockLogger = mock(Logger.class);
    Channel mockChannel = mock(Channel.class);
    String mockConsumerTag = "mockConsumerTag";
    Storage mockStorage = mock(Storage.class);

    LocalConsumer _sut = new LocalConsumer(mockChannel, mockLogger, mockStorage);

    @Test
    public void shouldPrintOutTheMessage () throws java.io.IOException {
        // arrange
        String message = "{\"key\":\"some\",\"value\":\"number\"}";
        // act
        _sut.handleDelivery(mockConsumerTag, null, new AMQP.BasicProperties(), message.getBytes() );
        // assert
        String expected = " [x] Received and processed '" + message + "'";
        verify(mockLogger).print(eq(expected));
    }

    @Test
    public void addKeyValuePairToStorage () throws java.io.IOException {
        // arrange
        String message = "{\"key\":\"some\",\"value\":\"number\"}";
        // act
        _sut.handleDelivery(mockConsumerTag, null, new AMQP.BasicProperties(), message.getBytes() );
        // assert

        ArgumentCaptor<UpdateChacheMessage> argument = ArgumentCaptor.forClass(UpdateChacheMessage.class);
        verify(mockStorage).add(argument.capture());
        assertEquals("some", argument.getValue().key);
        assertEquals("number", argument.getValue().value);
    }
}