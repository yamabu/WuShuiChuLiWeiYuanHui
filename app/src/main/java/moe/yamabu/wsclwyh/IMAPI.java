package moe.yamabu.wsclwyh;

import com.wilddog.client.Query;
import com.wilddog.client.Wilddog;

public class IMAPI {

    public static Query getRoomQuery() {
        Wilddog ref = new Wilddog("https://yamabu-demo1.wilddogio.com/room/name");
        return ref.orderByKey();
    }

    public static Query getMessageQuery() {
        Wilddog ref = new Wilddog("https://yamabu-demo1.wilddogio.com/room/messages");
        return ref.orderByChild("time");
    }

    public static Wilddog pushMessageQuery() {
        return new Wilddog("https://yamabu-demo1.wilddogio.com/room/messages");
    }
}
