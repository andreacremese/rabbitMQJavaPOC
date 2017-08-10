package receiver.controllers;

import messages.GenericMessage;
import receiver.services.Storage;

import java.io.IOException;

public abstract class GenericController<M extends GenericMessage> {

    private Storage _storage;
    private Class<M> _msgClass;

    public GenericController(Storage storage, Class<M> mClass) {
        _msgClass = mClass;
        _storage = storage;
    }

    public Class<M> getMsg() {
        return this._msgClass;
    }

    public Storage getStorage(){
        return _storage;
    }

    public abstract void handleMessage(GenericMessage msg) throws IOException;
}
