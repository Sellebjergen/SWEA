package hotciv.standard;

import hotciv.framework.*;
import hotciv.framework.StrategyInterfaces.*;
import hotciv.standard.MovingStrategies.FlyingMovingStrategyImpl;
import hotciv.standard.MovingStrategies.groundMovingStrategyImpl;

import java.util.HashMap;

public class GameImpl implements Game {
  private Player currentPlayer = Player.RED;
  private int age = -4000;
  private int amountOfRounds = 0;

  private WinningStrategy ws;
  private IncrementAgeStrategy ias;
  private UnitActionsStrategy uas;
  private AttackingStrategy as;
  private WorldLayoutStrategy wls;
  private CityWorkForceFocus cwff;
  private DieStrategy ds;
  private IncrementCitySizeStrategy icss;

  private HashMap<Position, TileImpl> tiles;
  private HashMap<Position, CityImpl> cities;
  private HashMap<Position, UnitImpl> units;

  private int redCombatWins = 0;
  private int blueCombatWins = 0;

  private GameObserver gameObserver;

  public GameImpl(HotcivFactory factory) {
    this.ws = factory.createWinningStrategy();
    this.ias = factory.createIncrementAgeStrategy();
    this.uas = factory.createUnitActionsStrategy();
    this.wls = factory.createWorldLayoutStrategy();
    this.as = factory.createAttackingStrategy();
    this.cwff = factory.createCityWorkForceFocusStrategy();
    this.ds = factory.createDieStrategy();
    this.icss = factory.createIncrementCitySizeStrategy();

    // make the tiles in a HashMap.
    tiles = new HashMap<Position, TileImpl>();
    wls.putTiles(this);

    // make the Cities in a HashMap.
    cities = new HashMap<Position, CityImpl>();
    wls.putCities(this);

    // make the units in a HashMap
    units = new HashMap<Position, UnitImpl>();
    wls.putUnits(this);
  }

  public TileImpl getTileAt( Position p ) {
    return tiles.get(p);
  }

  public UnitImpl getUnitAt( Position p ) {
    return units.get(p);
  }

  public CityImpl getCityAt( Position p ) {
    return cities.get(p);
  }

  public Player getPlayerInTurn() { return currentPlayer; }

  public Player getWinner() {
    return ws.getWinner(this);
  }

  public int getAge() { return age; }

  private boolean isAValidMovement(Position from, Position to) {
    MovingStrategy moveStrat = new groundMovingStrategyImpl();
    if (!getUnitAt(from).getIsGroundUnit()) moveStrat = new FlyingMovingStrategyImpl();
    return moveStrat.isAValidMovement(from, to, this);
  }

  private void addUnit(Position p, UnitImpl unit) {
    units.put(p, unit);
    gameObserver.worldChangedAt(p);
  }

  public boolean moveUnit( Position from, Position to ) {
    if (! isAValidMovement(from, to)) return false;
    if (! as.handleAttackingProcedure(from, to, this)) {
      gameObserver.worldChangedAt(from);
      return false;
    }
    gameObserver.worldChangedAt(from);
    gameObserver.worldChangedAt(to);
    return true;
  }

  public void endOfTurn() {
    if (currentPlayer.equals(Player.RED)) {
      gameObserver.turnEnds(getPlayerInTurn(), getAge());
      currentPlayer = Player.BLUE;
    }
    else if (currentPlayer.equals(Player.BLUE)) {
      updateGameboard();
      gameObserver.turnEnds(getPlayerInTurn(), getAge());
      currentPlayer = Player.RED;
    }
  }

  private void updateGameboard() {
    // make sure all cities have the correct food and production before incrementing
    cities.forEach((k, v) -> changeWorkForceFocusInCityAt(k, v.getWorkforceFocus()));

    // give cities more 'production' and makes units
    cities.forEach((k, v) -> {v.incrementTreasury();
                              v.incrementFoodStorage();
                              makeUnit(k);
                              incrementCitySize(k);
                              gameObserver.worldChangedAt(k);});

    // reset the moveCount of all units.
    units.forEach((k, v) -> {
      v.resetMoveCount();
      gameObserver.worldChangedAt(k);
    });

    // increments the age with the given strategy ias.
    this.age = ias.incrementAge(getAge());

    amountOfRounds += 1;
  }

  private void makeUnit(Position cityPos) {
    CityImpl city = getCityAt(cityPos);

    if (!city.canAffordUnit()) return;
    city.buyProductionUnit();

    placeUnitAroundCity(cityPos, city);
  }

  private void placeUnitAroundCity(Position cityPos, CityImpl city) {
    // if no unit in city, place one.
    if(getUnitAt(cityPos) == null) {
      addUnit(cityPos, new UnitImpl(city.getProduction(), city.getOwner()));
      gameObserver.worldChangedAt(cityPos);
      return;
    }

    // otherwise place it on the first avaible field around the city.
    for (Position p : hotciv.utility.Utility.get8neighborhoodOf(cityPos)) {
      if(getUnitAt(p) == null) {
        boolean toTileIsOcean = getTileAt(p).getTypeString().equals(GameConstants.OCEANS);
        boolean toTileIsMountain = getTileAt(p).getTypeString().equals(GameConstants.MOUNTAINS);
        if (!toTileIsMountain && !toTileIsOcean) {
          addUnit(p, new UnitImpl(city.getProduction(), city.getOwner()));
          gameObserver.worldChangedAt(p);
          return;
        }
      }
    }
  }

  public void changeProductionInCityAt( Position p, String unitType ) {
    getCityAt(p).changeProduction(unitType);
  }

  public void performUnitActionAt( Position p ) {
    uas.unitActions(p, this);
    gameObserver.worldChangedAt(p);
  }

  @Override
  public void addObserver(GameObserver observer) {
    this.gameObserver = observer;
  }

  @Override
  public void setTileFocus(Position position) {
    gameObserver.tileFocusChangedAt(position);
  }

  public void changeWorkForceFocusInCityAt( Position p, String balance ) {
    cwff.changeWorkForceFocusInCityAt(p, balance, this);
  }

  public void incrementCombatWin() {
    if(getPlayerInTurn().equals(Player.RED)) redCombatWins++;
    else if(getPlayerInTurn().equals(Player.BLUE)) blueCombatWins++;
  }

  // ==================================== //
  public HashMap<Position, CityImpl> getCities() {
    return cities;
  }

  public HashMap<Position, UnitImpl> getUnits() {
    return units;
  }

  public HashMap<Position, TileImpl> getTiles() {
    return tiles;
  }

  public void setUnits(HashMap<Position, UnitImpl> units) {
    this.units = units;
  }

  public void setTiles(HashMap<Position, TileImpl> tiles) {
    this.tiles = tiles;
  }

  public void setCities(HashMap<Position, CityImpl> cities) {
    this.cities = cities;
  }

  private void incrementCitySize(Position p) {
    this.cities = icss.incrementCitySize(p, cities);
  }

  public int getRedCombatWins() {
    return redCombatWins;
  }

  public int getBlueCombatWins() {
    return blueCombatWins;
  }

  public int getAmountOfRounds() {
    return amountOfRounds;
  }

  @Override
  public String getId() {
    return "null";
  }
}