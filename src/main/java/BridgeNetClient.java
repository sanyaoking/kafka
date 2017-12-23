import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;
import io.vertx.ext.eventbus.bridge.tcp.impl.protocol.FrameHelper;

public class BridgeNetClient {

    public static void main(String[] arg){
        Vertx vertx = Vertx.vertx();
        // Send a request and get a response
        NetClient client = vertx.createNetClient();
        EventBus eb = vertx.eventBus();
        eb.consumer("client").handler(mes->{
            System.out.println(mes.body().toString());
        });
        client.connect(7000, "localhost", conn -> {
            NetSocket socket = conn.result();
            socket.handler(event -> {
                System.out.println(event.toString());
            });
            FrameHelper.sendFrame("send", "test","client", new JsonObject().put("value", "vert.x"), socket);
        });
    }
}
