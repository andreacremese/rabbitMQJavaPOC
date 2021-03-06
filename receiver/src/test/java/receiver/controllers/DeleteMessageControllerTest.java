package receiver.controllers;

import messages.DeleteCacheMessage;
import org.junit.Test;
import receiver.services.Storage;

import java.io.IOException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DeleteMessageControllerTest {
    public Storage mockStorage = mock(Storage.class);

    @Test
    public void deletesKeyFromCache() throws IOException {
            // arrange
            DeleteMessageController sut = new DeleteMessageController(mockStorage);
            DeleteCacheMessage msg = new DeleteCacheMessage();
            String key = "newKey";
            msg.key = key;
            when(mockStorage.get(anyString())).thenReturn(null);
            // act
            sut.handleMessage(msg);
            // assert
            verify(mockStorage).delete(key);
    }
}
