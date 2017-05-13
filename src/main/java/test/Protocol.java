package test;

import org.junit.Test;

/**
 * Created by MarioJ on 25/03/16.
 */
public class Protocol {

    @Test
    public void toMessage() {
        assert br.com.protocol.Protocol.toMessage("[/127.0.0.1:52266] [all] [e ae]") != null;
    }
}
