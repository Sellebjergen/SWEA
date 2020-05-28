package hotciv.broker.main.Broker.Proxies;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.framework.*;
import hotciv.broker.main.Broker.BrokerConstants;

public class GameProxy implements Game, ClientProxy {
  private final Requestor requestor;
  private String id;
  private GameObserver go;

  public GameProxy(String id, Requestor requestor) {
    this.requestor = requestor;
    this.id = id;
    System.out.println("GameProxy Loaded!");
  }

  @Override
  public Tile getTileAt(Position p) {
    String tileRef = requestor.sendRequestAndAwaitReply(id, BrokerConstants.gameID + BrokerConstants.getTile_operation, String.class, p);
    return new TileProxy(tileRef, requestor);
  }

  @Override
  public Unit getUnitAt(Position p) {
    String unitRef = requestor.sendRequestAndAwaitReply(id, BrokerConstants.gameID + BrokerConstants.getUnit_operation, String.class, p);
    if (unitRef.equals("")) return null;
    return new UnitProxy(unitRef, requestor);
  }

  @Override
  public City getCityAt(Position p) {
    String cityRef = requestor.sendRequestAndAwaitReply(id, BrokerConstants.gameID + BrokerConstants.getCity_operation, String.class, p);
    if (cityRef.equals("")) return null;
    return new CityProxy(cityRef, requestor);
  }

  @Override
  public Player getPlayerInTurn() {
    return requestor.sendRequestAndAwaitReply(id, BrokerConstants.gameID + BrokerConstants.getPlayerInTurn_operation, Player.class);
  }

  @Override
  public Player getWinner() {
    return requestor.sendRequestAndAwaitReply(id, BrokerConstants.gameID + BrokerConstants.getWinner_operation, Player.class);
  }

  @Override
  public int getAge() {
    return requestor.sendRequestAndAwaitReply(id, BrokerConstants.gameID + BrokerConstants.getAge_operation, Integer.class);
  }

  @Override
  public boolean moveUnit(Position from, Position to) {
    boolean move = requestor.sendRequestAndAwaitReply(id, BrokerConstants.gameID + BrokerConstants.moveUnit_operation, Boolean.class, from, to);
    go.worldChangedAt(from);
    go.worldChangedAt(to);
    return move;
  }

  @Override
  public void endOfTurn() {
    Player player = getPlayerInTurn();
    requestor.sendRequestAndAwaitReply(id, BrokerConstants.gameID + BrokerConstants.endOfTurn_operation, void.class);
    go.turnEnds(player, getAge());
  }

  @Override
  public void changeWorkForceFocusInCityAt(Position p, String balance) {
    requestor.sendRequestAndAwaitReply(id, BrokerConstants.gameID + BrokerConstants.changeWorkFocus_operation, void.class, p, balance);
  }

  @Override
  public void changeProductionInCityAt(Position p, String unitType) {
    requestor.sendRequestAndAwaitReply(id, BrokerConstants.gameID + BrokerConstants.changeProduction_operation, void.class, p, unitType);
  }

  @Override
  public void performUnitActionAt(Position p) {
    requestor.sendRequestAndAwaitReply(id, BrokerConstants.gameID + BrokerConstants.performUnitAction_operation, void.class, p);
    go.worldChangedAt(p);
  }

  @Override
  public void addObserver(GameObserver observer) {
    go = observer;
  }

  @Override
  public void setTileFocus(Position position) {
    go.tileFocusChangedAt(position);
  }

  @Override
  public String getId() {
    return id;
  }
}
