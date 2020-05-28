package hotciv.standard.ManuelTest;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.standard.GameImpl;
import hotciv.standard.GameObserverTest.GameObserverStub;
import hotciv.standard.HotCivFactories.AlphaHotcivFactory;
import hotciv.standard.logDecorater;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TestTournamentCiv {
  private Game game;
  private GameObserverStub go = new GameObserverStub();

  @Before
  public void setUp() {
    game = new logDecorater(new GameImpl(new AlphaHotcivFactory()));
    game.addObserver(go);
  }

  @Test
  public void testLogging() {
    game.moveUnit(new Position(2, 0), new Position(2,1));
    game.endOfTurn();
    game.moveUnit(new Position(3,2 ), new Position(2, 1));
    game.endOfTurn();
    game.moveUnit(new Position(4, 3), new Position(4, 4));
    game.endOfTurn();
    game.moveUnit(new Position(2, 1), new Position(1,1));
    game.endOfTurn();
    game.moveUnit(new Position(4,4), new Position(3, 3));
    game.endOfTurn();
    game.moveUnit(new Position(1,1), new Position(2, 1));
    ((logDecorater) game).toggleLog();  //Why can't i use this?
    for (int i = 3; i < 11; i++) {
      game.endOfTurn();
      game.endOfTurn();
    }
    ((logDecorater) game).toggleLog();
    game.getWinner();
    // assertThat(true, is(true));
  }
}
