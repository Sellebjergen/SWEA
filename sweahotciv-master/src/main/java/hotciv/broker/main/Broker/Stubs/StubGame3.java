package hotciv.broker.main.Broker.Stubs;

import frds.broker.Servant;
import hotciv.broker.main.Broker.BrokerConstants;
import hotciv.framework.*;
import hotciv.stub.StubTile;

import java.util.HashMap;
import java.util.UUID;

public class StubGame3 implements Game, Servant {
  private HashMap<Position, StubUnit2> units = new HashMap<>();
  private HashMap<Position, StubCity5> cities = new HashMap<>();
  private HashMap<Position, StubTile> tiles = new HashMap<>();

  private Player currentPlayer;
  private String newWorkFocus = "";
  private String newProduction = "";
  private Boolean unitActionPerformed = false;
  private String id;

  public StubGame3() {
    id = UUID.randomUUID().toString();
    currentPlayer = Player.RED;
    tiles.put(new Position(1, 1), new StubTile(GameConstants.HILLS));
    units.put(new Position(1, 1), new StubUnit2(GameConstants.ARCHER, Player.RED));
    units.put(new Position(1, 2), new StubUnit2(GameConstants.ARCHER, Player.RED));
    cities.put(new Position(1, 2), new StubCity5(Player.RED, 2));
  }

  @Override
  public Tile getTileAt(Position p) {
    return tiles.get(p);
  }

  @Override
  public Unit getUnitAt(Position p) {
    return units.get(p);
  }

  @Override
  public City getCityAt(Position p) {
    return cities.get(p);
  }

  @Override
  public Player getPlayerInTurn() {
    return currentPlayer;
  }

  @Override
  public Player getWinner() {
    return Player.RED;
  }

  @Override
  public int getAge() {
    return 200;
  }

  @Override
  public boolean moveUnit(Position from, Position to) {
    if (units.get(from) != null) {
      units.remove(from);
      units.put(to, new StubUnit2(GameConstants.ARCHER, Player.RED));
      return true;
    }
    return false;
  }

  @Override
  public void endOfTurn() {
    if (currentPlayer.equals(Player.RED)) {
      currentPlayer = Player.BLUE;
    } else if (currentPlayer.equals(Player.BLUE)) {
      currentPlayer = Player.RED;
    }
  }

  @Override
  public void changeWorkForceFocusInCityAt(Position p, String balance) {
    newWorkFocus = balance;
  }

  @Override
  public void changeProductionInCityAt(Position p, String unitType) {
    newProduction = unitType;
  }

  @Override
  public void performUnitActionAt(Position p) {
    unitActionPerformed = true;
  }

  @Override
  public void addObserver(GameObserver observer) {
    // game observer method
  }

  @Override
  public void setTileFocus(Position position) {
    // game observer method
  }

  public String getNewWorkFocus() {
    return newWorkFocus;
  }

  public String getNewProduction() {
    return newProduction;
  }

  public Boolean getUnitActionPerformed() {
    return unitActionPerformed;
  }

  public String getId() {
    return id;
  }
}