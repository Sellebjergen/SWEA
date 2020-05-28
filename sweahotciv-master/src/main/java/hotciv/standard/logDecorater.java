package hotciv.standard;

import hotciv.framework.*;
import hotciv.standard.HotCivFactories.AlphaHotcivFactory;

public class logDecorater implements Game {
  private GameImpl game;
  private boolean log = true;
  private GameObserver gameObserver;

  public logDecorater(GameImpl game) {
    this.game = game;
    getPlayerInTurn();
  }

  // Why can't i call this?
  public void toggleLog() {
    this.log = !log;
  }

  @Override
  public Player getPlayerInTurn() {
    Player pit = game.getPlayerInTurn();
    if (log) System.out.println("Round " + (game.getAmountOfRounds() + 1) + " - Current player: " + pit.toString());
    return pit;
  }

  @Override
  public Player getWinner() {
    Player winner = game.getWinner();
    if (log) {
      System.out.println("");
      System.out.println("----- The game Ended -----");
      System.out.println(winner.toString() + " Won the game!");
    }
    return winner;
  }

  @Override
  public int getAge() {
    return game.getAge();
  }

  @Override
  public boolean moveUnit(Position from, Position to) {
    if (log) System.out.println("  Player moves " + getUnitAt(from).getTypeString() + " from " + from.toString() + " to " + to.toString());
    return game.moveUnit(from, to);
  }

  @Override
  public void endOfTurn() {
    game.endOfTurn();
    if (log) getPlayerInTurn();
  }
  
  @Override
  public void changeWorkForceFocusInCityAt(Position p, String balance) {
    if (log) System.out.println("  Changed workForce in city at " + p.toString() + " to" + balance);
    game.changeWorkForceFocusInCityAt(p, balance);
  }

  @Override
  public void changeProductionInCityAt(Position p, String unitType) {
    if (log) System.out.println("  Changed production in city at " + p.toString() + " to " + unitType);
    game.changeProductionInCityAt(p, unitType);
  }

  @Override
  public void performUnitActionAt(Position p) {
    if(log) System.out.println("  Unit at " + p + " performed unit action");
    game.performUnitActionAt(p);
  }

  @Override
  public void addObserver(GameObserver observer) {
    game.addObserver(observer);
  }

  @Override
  public void setTileFocus(Position position) {

  }

  @Override
  public Tile getTileAt(Position p) {
    return game.getTileAt(p);
  }

  @Override
  public Unit getUnitAt(Position p) {
    return game.getUnitAt(p);
  }

  @Override
  public City getCityAt(Position p) {
    return game.getCityAt(p);
  }

  @Override
  public String getId() {
    return "null";
  }
}
