package receiver;

import messages.UpdateChacheMessage;
import net.spy.memcached.MemcachedClient;

import java.net.InetSocketAddress;
import java.util.LinkedList;

public class Storage {

    private MemcachedClient _mcc;

    public Storage() {

        try{
            // Connecting to Memcached server on localhost
            _mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
            System.out.println("Connection to server sucessful.");


        }catch(Exception ex){
            System.out.println( ex.getMessage() );
        }
    }

    public void add(UpdateChacheMessage msg) {
        // now set data into memcached server
        _mcc.set(msg.key, 900, msg.value);
    }

    public String get(String key) {
        Object result = _mcc.get(key);
        return result == null ? "" : result.toString();
    }
}
