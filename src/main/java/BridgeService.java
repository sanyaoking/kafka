import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetServerOptions;
import io.vertx.ext.bridge.BridgeOptions;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.eventbus.bridge.tcp.BridgeEvent;
import io.vertx.ext.eventbus.bridge.tcp.TcpEventBusBridge;

public class BridgeService {
    private static volatile Handler<BridgeEvent> eventHandler = event -> event.complete(true);
    public static void main(String[] arg){
        Vertx vertx = Vertx.vertx();

        vertx.setPeriodic(1000, __ -> vertx.eventBus().send("ping", new JsonObject().put("value", "hi")));
        vertx.eventBus().consumer("test", (Message<JsonObject> msg) -> {
            System.out.println("this is test client");
            msg.reply(new JsonObject().put("back","this is back!"));
        });
        TcpEventBusBridge bridge = TcpEventBusBridge.create(
                vertx,
                new BridgeOptions()
                        .addInboundPermitted(new PermittedOptions().setAddress("hello"))
                        .addInboundPermitted(new PermittedOptions().setAddress("echo"))
                        .addInboundPermitted(new PermittedOptions().setAddress("test"))
                        .addOutboundPermitted(new PermittedOptions().setAddress("echo"))
                        .addOutboundPermitted(new PermittedOptions().setAddress("ping")), new NetServerOptions(), event -> eventHandler.handle(event));

        bridge.listen(7000, res -> {
            if(res.succeeded()){
                System.out.println("this is success!");
            }else{
                System.out.println("this is fail!");
            }
        });

    }
}

