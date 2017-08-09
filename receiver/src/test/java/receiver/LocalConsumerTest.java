package receiver;

import com.rabbitmq.client.*;
import messages.UpdateCacheMessage;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import receiver.consumers.LocalConsumer;
import receiver.controllers.UpdateMessageController;
import receiver.services.Logger;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class LocalConsumerTest {

    Logger mockLogger = mock(Logger.class);
    Channel mockChannel = mock(Channel.class);
    String mockConsumerTag = "mockConsumerTag";
    UpdateMessageController mockController = mock(UpdateMessageController.class);

    LocalConsumer _sut = new LocalConsumer(mockChannel, mockLogger, mockController, UpdateCacheMessage.class);

//    @Test
//    public void shouldPrintOutTheMessage () throws java.io.IOException {
//        // arrange
//        String message = "{\"key\":\"some\",\"value\":\"number\"}";
//        // act
//        _sut.handleDelivery(mockConsumerTag, null, new AMQP.BasicProperties(), message.getBytes() );
//        // assert
//        String expected = " [x] Received and processed '" + message + "'";
//        verify(mockLogger).print(eq(expected));
//    }

    @Test
    public void handlesMessageToController () throws java.io.IOException {
        // arrange
        String message = "{\"key\":\"some\",\"value\":\"number\"}";
        // act
        _sut.handleDelivery(mockConsumerTag, null, new AMQP.BasicProperties(), message.getBytes() );
        // assert

        ArgumentCaptor<UpdateCacheMessage> argument = ArgumentCaptor.forClass(UpdateCacheMessage.class);
        verify(mockController).handleMessage(argument.capture());
        assertEquals("some", argument.getValue().key);
        assertEquals("number", argument.getValue().value);
    }
}