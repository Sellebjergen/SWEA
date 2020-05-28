package hotciv.standard.GameObserverTest;

import hotciv.framework.GameObserver;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.Unit;

import java.util.ArrayList;

public class GameObserverStub implements GameObserver {
  private ArrayList<Position> worldChangeMap = new ArrayList<>();
  private ArrayList<Player> players = new ArrayList<>();
  private ArrayList<Integer> endAges = new ArrayList<>();

  @Override
  public void worldChangedAt(Position pos) {
    worldChangeMap.add(pos);
  }

  @Override
  public void turnEnds(Player nextPlayer, int age) {
    players.add(nextPlayer);
    endAges.add(age);
  }

  @Override
  public void tileFocusChangedAt(Position position) {
    // nothing yet
  }

  // ===  Get methods  === //
  public ArrayList<Position> getWorldChangeMap() {
    return worldChangeMap;
  }
  public ArrayList<Player> getPlayers() {
    return players;
  }
  public ArrayList<Integer> getEndAges() {
    return endAges;
  }
}
