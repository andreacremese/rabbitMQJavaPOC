package receiver.controllers;

import CacheDTOs.CacheItem;
import messages.UpdateCacheMessage;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import receiver.controllers.UpdateMessageController;
import receiver.services.Storage;

import java.io.IOException;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UpdateMessageControllerTest {

    public Storage mockStorage = mock(Storage.class);

    @Test
    public void storesNewValue() throws IOException {
        // arrange
        UpdateMessageController sut = new UpdateMessageController(mockStorage);
        UpdateCacheMessage msg = new UpdateCacheMessage();
        String key = "newKey";
        String value = "newValue";
        msg.key = key;
        msg.value = value;
        when(mockStorage.get(anyString())).thenReturn(null);
        // act
        sut.handleMessage(msg);
        // assert
        ArgumentCaptor<CacheItem> argument = ArgumentCaptor.forClass(CacheItem.class);
        verify(mockStorage).add(argument.capture());
        assertEquals(key, argument.getValue().key);
        assertEquals(value, argument.getValue().value);
    }

    @Test
    public void addsValuesForAKeyAlreadyPresent() throws IOException {
        // arrange
        String key = "key";
        String newValue = "newValue";
        String oldValue = "oldValue";

        UpdateMessageController sut = new UpdateMessageController(mockStorage);

        UpdateCacheMessage msg = new UpdateCacheMessage();
        msg.key = key;
        msg.value = newValue;

        CacheItem oldCi = new CacheItem();
        oldCi.key = key;
        oldCi.value = oldValue;

        when(mockStorage.get(anyString())).thenReturn(oldCi);

        // act
        sut.handleMessage(msg);
        // assert
        ArgumentCaptor<CacheItem> argument = ArgumentCaptor.forClass(CacheItem.class);
        verify(mockStorage).add(argument.capture());
        assertEquals(key, argument.getValue().key);
        assertEquals(newValue, argument.getValue().value);
        assertEquals(1, argument.getValue().oldValues.size());
        assertEquals(oldValue, argument.getValue().oldValues.get(0));
    }

    @Test
    public void addsValuesForAKeyAlreadyPresentWithOldEntries() throws IOException {
        // arrange
        String key = "key";
        String newValue = "newValue";
        String oldValue = "oldValue";
        String veryOldValue = "veryOldValue";

        UpdateMessageController sut = new UpdateMessageController(mockStorage);

        UpdateCacheMessage msg = new UpdateCacheMessage();
        msg.key = key;
        msg.value = newValue;

        CacheItem oldCi = new CacheItem();
        oldCi.key = key;
        oldCi.value = oldValue;
        oldCi.oldValues = new LinkedList<String>();
        oldCi.oldValues.add(veryOldValue);

        when(mockStorage.get(anyString())).thenReturn(oldCi);

        // act
        sut.handleMessage(msg);
        // assert
        ArgumentCaptor<CacheItem> argument = ArgumentCaptor.forClass(CacheItem.class);
        verify(mockStorage).add(argument.capture());
        assertEquals(key, argument.getValue().key);
        assertEquals(newValue, argument.getValue().value);
        assertEquals(2, argument.getValue().oldValues.size());
        assertEquals(veryOldValue, argument.getValue().oldValues.get(0));
        assertEquals(oldValue, argument.getValue().oldValues.get(1));
    }
}
