package hotciv.standard.BrokerTest;

import frds.broker.ClientRequestHandler;
import frds.broker.Requestor;
import frds.broker.ipc.socket.SocketClientRequestHandler;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.broker.main.Broker.Stubs.NullObserver;
import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.broker.main.Broker.BrokerConstants;
import hotciv.broker.main.Broker.Proxies.GameProxy;
import java.io.IOException;

public class HotCivStoryTest {

  public static void main(String[] args) throws IOException {
    new HotCivStoryTest(args);
  }

  private HotCivStoryTest(String[] args) {
    ClientRequestHandler clientRequestHandler = new SocketClientRequestHandler("localhost", BrokerConstants.serverPort);
    Requestor requestor = new StandardJSONRequestor(clientRequestHandler);
    Game game = new GameProxy("Stub", requestor);
    game.addObserver(new NullObserver());

    System.out.println("=== HotCiv Story Testing - Testing simple methods ===");
    System.out.println(" -> Game age                  : " + game.getAge());
    System.out.println(" -> Game winner               : " + game.getWinner());
    System.out.println(" -> Game playerInTurn         : " + game.getPlayerInTurn());

    Position from = new Position(3, 8);
    Position to = new Position(4, 7);
    System.out.println(" -> Game move " + from.toString() + "->" + to.toString() + "    : " + game.moveUnit(from, to));

    game.endOfTurn();
    System.out.println(" -> Game nextPlayerInTurn     : " + game.getPlayerInTurn());
  }
}