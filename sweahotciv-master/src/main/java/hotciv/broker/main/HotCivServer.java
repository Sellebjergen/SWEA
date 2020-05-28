package hotciv.broker.main;

import frds.broker.Invoker;
import frds.broker.ipc.socket.SocketServerRequestHandler;
import hotciv.broker.main.Broker.BrokerConstants;
import hotciv.broker.main.Broker.Invokers.HotCivGameInvoker;
import hotciv.broker.main.Broker.Invokers.HotCivRootInvoker;
import hotciv.broker.main.Broker.NameServiceImpl;
import hotciv.broker.main.Broker.Stubs.NullObserver;
import hotciv.framework.Game;
import hotciv.standard.GameImpl;
import hotciv.standard.HotCivFactories.AlphaHotcivFactory;
import hotciv.standard.HotCivFactories.SemiHotCivFactory;

public class HotCivServer {

  public static void main(String[] args) throws Exception {
    new HotCivServer(new GameImpl(new SemiHotCivFactory()));
  }

  private HotCivServer(Game game) {
    int port = BrokerConstants.serverPort;

    game.addObserver(new NullObserver());

    // Define the server side delegates
    Invoker invoker = new HotCivRootInvoker(game, new NameServiceImpl());

    // Configure a socket based server request handler
    SocketServerRequestHandler ssrh = new SocketServerRequestHandler(port, invoker);

    // Welcome
    System.out.println("=== HotCiv Socket based Server Request Handler (port:" + port + ") ===");
    System.out.println(" Use ctrl-c to terminate!");
    ssrh.start();
  }
}
