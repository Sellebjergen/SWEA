package hotciv.standard.TestCivVariants;

import hotciv.framework.*;
import hotciv.standard.GameObserverTest.GameObserverStub;
import hotciv.standard.HotCivFactories.ZetaHotCivFactory;
import hotciv.standard.GameImpl;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestZetaCiv {
  private Game game;
  private GameObserverStub go = new GameObserverStub();
  private Position redCityPos = new Position(1,1);
  private Position blueCityPos = new Position(4,1);
  private Position redArcherPos;
  private Position blueLegionPos;

  @Before
  public void setUp() {
    game = new GameImpl(new ZetaHotCivFactory());
    game.addObserver(go);
    redArcherPos  = new Position(2, 0);
    blueLegionPos = new Position(3, 2);
  }

  @Test
  public void redWinsWhenOwningAllCities() {
    game.moveUnit(redArcherPos, new Position(3, 1));
    roundEnd();
    game.moveUnit(new Position(3, 1), blueCityPos);
    assertThat(game.getWinner(), is(Player.RED));
  }

  @Test
  public void redDoNotWinWhenOwningAllCitiesAfter20Rounds() {
    for (int i = 0; i < 22; i ++) {
      roundEnd();
    }

    game.moveUnit(redArcherPos, new Position(3, 1));
    roundEnd();
    game.moveUnit(new Position(3, 1), blueCityPos);
    assertThat(game.getCityAt(blueCityPos).getOwner(), is(Player.RED));
    assertNull(game.getWinner());
  }

  @Test
  public void blueCannotWinBefore20RoundsByKilling3Units() {
    game.moveUnit(redArcherPos, new Position(3, 1));

    game.endOfTurn(); // now blues turn
    game.moveUnit(blueLegionPos, new Position(3, 1)); // blueLegion kills redArcher

    game.endOfTurn(); // now reds turn
    game.endOfTurn(); // now blues turn
    game.endOfTurn(); // now reds turn
    game.moveUnit(redCityPos, redArcherPos); // new redArcher

    game.endOfTurn(); // now blues turn
    game.moveUnit(new Position(3,1), redArcherPos); // blueLegion kills new redArcher

    game.endOfTurn(); // now reds turn
    game.endOfTurn(); // now blues turn
    game.endOfTurn(); // now reds turn
    game.moveUnit(redCityPos, new Position(3, 1));

    game.endOfTurn(); // now blues turn
    game.moveUnit(redArcherPos, new Position(3, 1)); // blueLegion kills new redArcher

    assertNull(game.getWinner());
  }

/*  @Test
  public void blueCanWinAfter20RoundsByKilling3Units() {
    game.changeProductionInCityAt(redCityPos, GameConstants.ARCHER);
    game.endOfTurn(); // now blues turn
    game.moveUnit(blueLegionPos, new Position(3, 1));

    for (int i = 0; i < 30; i ++) {
      roundEnd();
    }

    game.endOfTurn(); // now reds turn
    game.endOfTurn(); // now blues turn
    game.moveUnit(new Position(3, 1), new Position(2, 1)); // blueLegion kills redArcher
    game.endOfTurn(); // now reds turn
    game.endOfTurn(); // now blues turn
    game.moveUnit(new Position(2,1), redArcherPos); // blueLegion kills new redArcher
    game.endOfTurn(); // now reds turn
    game.endOfTurn(); // now blues turn
    game.endOfTurn(); // now reds turn
    game.endOfTurn(); // now blues turn
    roundEnd();
    roundEnd();
    roundEnd();
    game.moveUnit(redArcherPos, new Position(2, 1)); // blueLegion kills new redArcher

    assertThat(game.getWinner(), is(Player.BLUE));
  }*/

  // =====   Helping methods   ===== //

  // quick fix to ensure we get the round end fixed.
  private void roundEnd() {
    game.endOfTurn();
    game.endOfTurn();
  }
}
