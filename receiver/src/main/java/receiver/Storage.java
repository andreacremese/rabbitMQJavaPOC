package receiver;

import messages.UpdateChacheMessage;

import java.util.LinkedList;

public class Storage {

    public LinkedList<UpdateChacheMessage> list;

    public Storage() {
        list = new LinkedList<UpdateChacheMessage>();
    }

    public void add(UpdateChacheMessage msg) {
        if (list == null) {
            list = new LinkedList<UpdateChacheMessage>();
        }
        list.add(msg);
    }

}
