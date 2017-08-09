package receiver.controllers;

import CacheDTOs.CacheItem;
import messages.GenericMessage;
import messages.UpdateCacheMessage;
import receiver.services.Storage;

import java.io.IOException;
import java.util.LinkedList;

public class UpdateMessageController extends GenericController {

    public UpdateMessageController(Storage storage) {
        super(storage);
    }

    public void handleMessage(GenericMessage msg) throws IOException {
        UpdateCacheMessage m = (UpdateCacheMessage) msg;

        // get from cache
        CacheItem result = _storage.get(m.key);

        if (result == null) {
            CacheItem cm = new CacheItem();
            cm.key = m.key;
            cm.value = m.value;
            this._storage.add(cm);
        } else {
            CacheItem cm = new CacheItem();
            cm.key = m.key;
            cm.value = m.value;
            if (result.oldValues == null) {
                cm.oldValues = new LinkedList<String>();
            } else {
                cm.oldValues = result.oldValues;
            }
            cm.oldValues.add(result.value);
            this._storage.add(cm);
        }
    }
}
