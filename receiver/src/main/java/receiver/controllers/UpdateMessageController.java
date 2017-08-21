package receiver.controllers;

import CacheDTOs.CacheItem;
import messages.GenericMessage;
import messages.UpdateCacheMessage;
import receiver.services.Storage;

import java.io.IOException;
import java.util.LinkedList;

public class UpdateMessageController extends GenericController<UpdateCacheMessage> {


    public UpdateMessageController(Storage storage) {
        super(storage, UpdateCacheMessage.class);
    }

    public void handleMessage(UpdateCacheMessage m) throws IOException {

        // get from cache
        CacheItem result = getStorage().get(m.key);
        CacheItem cm = new CacheItem();
        cm.key = m.key;
        cm.value = m.value;

        if (result != null) {
            if (result.oldValues == null) {
                cm.oldValues = new LinkedList<String>();
            } else {
                cm.oldValues = result.oldValues;
            }
            cm.oldValues.add(result.value);
        }
        getStorage().add(cm);
    }
}
