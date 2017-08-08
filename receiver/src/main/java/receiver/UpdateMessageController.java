package receiver;

import CacheDTOs.CacheItem;
import messages.GenericMessage;
import messages.UpdateChacheMessage;

import java.io.IOException;
import java.util.LinkedList;

public class UpdateMessageController extends GenericController {

    public UpdateMessageController(Storage storage) {
        super(storage);
    }

    public void handleMessage(GenericMessage msg) throws IOException {
        UpdateChacheMessage m = (UpdateChacheMessage) msg;

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
            if (cm.oldValues == null) {
                cm.oldValues = new LinkedList<String>();
            }
            cm.oldValues.add(result.value);
            this._storage.add(cm);
        }
    }
}
