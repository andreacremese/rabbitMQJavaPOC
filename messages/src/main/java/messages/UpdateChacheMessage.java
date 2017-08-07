package messages;

public class UpdateChacheMessage {
    public String key;
    public String value;

    public boolean equals(Object object2) {
        return object2 instanceof UpdateChacheMessage &&
                this.key.equals(((UpdateChacheMessage)object2).key) &&
        this.value.equals(((UpdateChacheMessage)object2).value);
    }
}
