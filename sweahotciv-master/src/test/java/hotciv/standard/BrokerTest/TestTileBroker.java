package hotciv.standard.BrokerTest;

import frds.broker.ClientRequestHandler;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import frds.broker.Invoker;

import hotciv.framework.NameService;
import hotciv.broker.main.Broker.NameServiceImpl;
import hotciv.framework.*;
import hotciv.broker.main.Broker.Invokers.HotCivTileInvoker;
import hotciv.broker.main.Broker.LocalMethodClientRequestHandler;
import hotciv.stub.StubTile;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestTileBroker {
  private Tile tile;

  @Before
  public void setup() {
    NameService nm = new NameServiceImpl();
    Invoker invoker = new HotCivTileInvoker(nm);
    ClientRequestHandler crh = new LocalMethodClientRequestHandler(invoker);

    nm.putTile("StubTile", new StubTile(GameConstants.HILLS));

    Requestor requestor = new StandardJSONRequestor(crh);

    tile = nm.getTile("StubTile");
  }

  @Test
  public void hasStringType() {
    String type = tile.getTypeString();
    assertThat(type, is(GameConstants.HILLS));
  }
}
