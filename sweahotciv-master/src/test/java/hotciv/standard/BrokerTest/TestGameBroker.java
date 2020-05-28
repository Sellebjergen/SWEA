package hotciv.standard.BrokerTest;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import hotciv.broker.main.Broker.BrokerConstants;
import hotciv.broker.main.Broker.Invokers.HotCivRootInvoker;
import hotciv.broker.main.Broker.LocalMethodClientRequestHandler;
import hotciv.broker.main.Broker.NameServiceImpl;
import hotciv.broker.main.Broker.Proxies.CityProxy;
import hotciv.broker.main.Broker.Proxies.GameProxy;
import hotciv.broker.main.Broker.Proxies.TileProxy;
import hotciv.broker.main.Broker.Proxies.UnitProxy;
import hotciv.broker.main.Broker.SpyStandardJSONRequester;
import hotciv.broker.main.Broker.Stubs.NullObserver;
import hotciv.broker.main.Broker.Stubs.StubGame3;
import hotciv.framework.*;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;


public class TestGameBroker {
  private Game game;
  private Requestor requestor;
  private Game servant;

  @Before
  public void setup() {
    NameService nm = new NameServiceImpl();

    servant = new StubGame3();
    GameObserver nullObserver = new NullObserver();
    servant.addObserver(nullObserver);

    Invoker invoker = new HotCivRootInvoker(servant, nm);
    ClientRequestHandler crh = new LocalMethodClientRequestHandler(invoker);

    requestor = new SpyStandardJSONRequester(crh);
    game = new GameProxy("Stub", requestor);
    game.addObserver(nullObserver);
  }

  @Test
  public void canGetTile() {
    Tile tile = game.getTileAt(new Position(1, 1));
    assertNotNull(tile);
    assert(tile instanceof TileProxy);
    assertThat(tile.getTypeString(), is(GameConstants.HILLS));
  }

  @Test
  public void canGetUnit() {
    Unit unit = game.getUnitAt(new Position(1, 1));
    assertNotNull(unit);
    assert(unit instanceof UnitProxy);
    assertThat(unit.getOwner(), is(Player.RED));
  }

  @Test
  public void canGetCity() {
    City city = game.getCityAt(new Position(1, 2));
    assertNotNull(city);
    assert(city instanceof CityProxy);
    assertThat(city.getOwner(), is(Player.RED));
  }

  @Test
  public void shouldHaveWinner() {
    Player winner = game.getWinner();
    assertThat(winner, is(Player.RED));
  }

  @Test
  public void shouldBeAbleToGetPlayerInTurn() {
    assertThat(game.getPlayerInTurn(), is(Player.RED));
  }

  @Test
  public void shouldGetAge() {
    game.getAge();
    assertThat(game.getAge(), is(200));
  }

  @Test
  public void shouldMoveUnit() {
    Position from = new Position(1,1);
    Position to = new Position(2, 2);

    assertThat(game.moveUnit(from, to), is(true));
  }

  @Test
  public void weCanSwapTurn() {
    assertThat(game.getPlayerInTurn(), is(Player.RED));
    game.endOfTurn();
    assertThat(game.getPlayerInTurn(), is(Player.BLUE));
  }

  @Test
  public void weCanChangeWorkForceFocus() {
    game.changeWorkForceFocusInCityAt(new Position(1,2), GameConstants.productionFocus);

    Position cityPos = ((Position)((SpyStandardJSONRequester)requestor).getArguments()[0]);
    String newWorkFocus = ((String)((SpyStandardJSONRequester)requestor).getArguments()[1]);
    String methodName = ((SpyStandardJSONRequester)requestor).lastOperationName();

    assertThat(cityPos.toString(), is(new Position(1,2 ).toString()));
    assertThat(newWorkFocus, is(GameConstants.productionFocus));
    assertThat(methodName, is(BrokerConstants.gameID + BrokerConstants.changeWorkFocus_operation));
    assertThat(((StubGame3)servant).getNewWorkFocus(), is(GameConstants.productionFocus));
  }

  @Test
  public void weCanChangeProduction() {
    game.changeProductionInCityAt(new Position(1, 2), GameConstants.LEGION);

    Position cityPos = ((Position)((SpyStandardJSONRequester)requestor).getArguments()[0]);
    String newProduction = ((String)((SpyStandardJSONRequester)requestor).getArguments()[1]);
    String methodName = ((SpyStandardJSONRequester)requestor).lastOperationName();

    assertThat(cityPos.toString(), is(new Position(1,2 ).toString()));
    assertThat(newProduction, is(GameConstants.LEGION));
    assertThat(methodName, is(BrokerConstants.gameID + BrokerConstants.changeProduction_operation));
    assertThat(((StubGame3)servant).getNewProduction(), is(GameConstants.LEGION));
  }

  @Test
  public void weCanUseUnitAction() {
    game.performUnitActionAt(new Position(1, 2));

    Position unitPos = ((Position)((SpyStandardJSONRequester)requestor).getArguments()[0]);
    String methodName = ((SpyStandardJSONRequester)requestor).lastOperationName();

    assertThat(unitPos.toString(), is(new Position(1,2 ).toString()));
    assertThat(methodName, is(BrokerConstants.gameID + BrokerConstants.performUnitAction_operation));
    assertThat(((StubGame3)servant).getUnitActionPerformed(), is(true));
  }
}