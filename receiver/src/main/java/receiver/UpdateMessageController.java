package receiver;

import messages.GenericMessage;
import messages.UpdateChacheMessage;

public class UpdateMessageController extends GenericController {

    public UpdateMessageController(Storage storage) {
        super(storage);
    }

    public void handleMessage(GenericMessage msg) {
        UpdateChacheMessage m = (UpdateChacheMessage) msg;

        // get from cache
        String result = _storage.get(m.key);

        if (result == "") {
            this._storage.add(m);
        } else {
            //
        }
        // if exists, add to the list
        // if not, make new
    }
}
