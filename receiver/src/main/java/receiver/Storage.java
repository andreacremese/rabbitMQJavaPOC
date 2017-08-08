package receiver;

import CacheDTOs.CacheItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.spy.memcached.MemcachedClient;

import java.io.IOException;
import java.net.InetSocketAddress;

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

    public void add(CacheItem msg) throws JsonProcessingException {
        // now set data into memcached server
        ObjectMapper mapper = new ObjectMapper();
        _mcc.set(msg.key, 900, mapper.writeValueAsString(msg));
    }

    public CacheItem get(String key) throws IOException {
        Object result = _mcc.get(key);
        if (result == null) {
            return null;
        }

        return new ObjectMapper().readValue(result.toString(), CacheItem.class);
    }
}
