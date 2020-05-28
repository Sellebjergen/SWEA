package hotciv.standard.TestCivVariants;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.standard.Epsilon.FixedEpsilonHotCivFactory;
import hotciv.standard.GameImpl;
import hotciv.standard.GameObserverTest.GameObserverStub;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class TestEpsilonCiv {
  private Game game;
  private GameObserverStub go = new GameObserverStub();
  private Position redCityPos = new Position(1, 1);
  private Position blueCityPos = new Position(4,1);
  private Position redArcherPos;
  private Position blueLegionPos;
  private Position redSettlerPos;

  @Before
  public void setUp() {
    game = new GameImpl(new FixedEpsilonHotCivFactory());
    game.addObserver(go);
    redArcherPos = new Position(2,0);
    blueLegionPos = new Position(3,2);
    redSettlerPos = new Position(4,3);
  }

  @Test
  public void redArcherAttacksBlueLegionOnHill() {
    // moving the units together.
    game.moveUnit(redArcherPos, new Position(1, 1));
    game.moveUnit(redSettlerPos, new Position(3, 3));
    game.endOfTurn();
    game.moveUnit(blueLegionPos, new Position(2, 1));
    game.endOfTurn();
    game.moveUnit(new Position(3,3), new Position(2, 3)); // redSettler 2, 3
    game.moveUnit(new Position(1,1), new Position(0, 0)); // redAarcher 0, 0
    game.endOfTurn();
    game.moveUnit(new Position(2,1), new Position(1, 2)); // blueLegion 1, 2
    game.endOfTurn();
    game.moveUnit(new Position(2,3), new Position(1, 3)); // redSettler 1, 3
    game.endOfTurn();
    game.moveUnit(new Position(1,2), new Position(0, 1)); // blueLegion 0, 1
    game.endOfTurn();
    game.moveUnit(new Position(1,3), new Position(0, 2)); // redSettler 0, 2
    game.moveUnit(redCityPos, new Position(0, 1));

    assertThat(game.getUnitAt(new Position(0, 1)).getOwner(), is(Player.RED));
    assertThat(game.getUnitAt(new Position(0, 1)).getTypeString(), is(GameConstants.ARCHER));
  }

  @Test
  public void redArcherAttacksBlueLegion() {
    Position pointOfConflict = new Position(3, 1);
    game.moveUnit(blueLegionPos, pointOfConflict);
    game.moveUnit(redArcherPos, pointOfConflict);
    assertThat(game.getUnitAt(pointOfConflict).getTypeString(), is(GameConstants.ARCHER));
    assertThat(game.getUnitAt(pointOfConflict).getOwner(), is(Player.RED));
  }

  @Test
  public void redArcherAttackedByBlueLegion() {
    Position pointOfConflict = new Position(3, 1);

    game.moveUnit(redArcherPos, pointOfConflict);
    game.endOfTurn();
    game.moveUnit(blueLegionPos, pointOfConflict);
    assertThat(game.getUnitAt(pointOfConflict).getTypeString(), is(GameConstants.LEGION));
    assertThat(game.getUnitAt(pointOfConflict).getOwner(), is(Player.BLUE));
  }

  @Test
  public void redSettlerCantKillBlueLegion() {
    game.moveUnit(redSettlerPos, blueLegionPos);
    assertThat(game.getUnitAt(blueLegionPos).getTypeString(), is(GameConstants.LEGION));
    assertNull(game.getUnitAt(redSettlerPos));
  }

  @Test
  public void legionAttackArcherOnHill() {
    Position hillPos = new Position(0, 1);

    // moves the redArcher to the hill position.
    assertThat(game.getTileAt(hillPos).getTypeString(), is(GameConstants.HILLS));
    game.moveUnit(redArcherPos, new Position(1, 1));
    game.endOfTurn();
    game.endOfTurn();
    game.moveUnit(new Position(1, 1), hillPos);
    assertThat(game.getUnitAt(hillPos).getTypeString(), is(GameConstants.ARCHER));
    game.endOfTurn();
    // moves the legion on top of archer.
    game.moveUnit(blueLegionPos, new Position(3, 1));
    game.endOfTurn();
    game.endOfTurn();
    game.moveUnit(new Position(3, 1), new Position(2, 1));
    game.endOfTurn();
    game.endOfTurn();
    game.moveUnit(new Position(2, 1), new Position(2, 0));
    game.endOfTurn();
    game.endOfTurn();
    game.moveUnit(new Position(2, 0), hillPos);

    // takes the last move and thereby attackes.
    assertThat(game.getUnitAt(hillPos).getTypeString(), is(GameConstants.ARCHER));
  }

  @Test
  public void itTakes3WinsToWinGame() {
    game.moveUnit(redArcherPos, new Position(3, 1));
    game.endOfTurn(); // now reds turn
    game.moveUnit(blueLegionPos, new Position(3, 1)); // blueLegion kills redArcher
    game.endOfTurn();
    game.endOfTurn(); // now reds turn
    game.endOfTurn(); // create new redArcher
    game.moveUnit(redCityPos, redArcherPos); // new redArcher
    game.endOfTurn(); // now reds turn
    game.moveUnit(new Position(3,1), redArcherPos); // blueLegion kills new redArcher

    assertNull(game.getWinner());
  }

  @Test
  public void blueCanWin() {
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
    game.moveUnit(redCityPos, new Position(2, 1));

    game.endOfTurn(); // now blues turn
    game.moveUnit(redArcherPos, new Position(2, 1)); // blueLegion kills new redArcher

    assertThat(game.getWinner(), is(Player.BLUE));
  }

  @Test
  public void redCanWin() {
    game.endOfTurn(); // now blues turn
    game.changeProductionInCityAt(blueCityPos, GameConstants.LEGION);
    game.moveUnit(blueLegionPos, new Position(3, 1));

    game.endOfTurn(); // now reds turn
    game.moveUnit(redArcherPos, new Position(3, 1)); // redArcher kills blueLegion

    game.endOfTurn(); // now blues turn
    game.endOfTurn(); // now reds turn
    game.moveUnit(new Position(3, 1), redArcherPos);

    game.endOfTurn(); // now blues turn
    game.endOfTurn(); // now reds turn
    game.endOfTurn(); // now blues turn
    game.moveUnit(blueCityPos, new Position(3, 1)); // new blueLegion

    game.endOfTurn(); // now reds turn
    game.moveUnit(redArcherPos, new Position(3, 1)); // redArcher kills new blueLegion

    game.endOfTurn(); // now blues turn
    game.endOfTurn(); // now reds turn
    game.moveUnit(new Position(3, 1), redArcherPos);

    game.endOfTurn(); // now blues turn
    game.moveUnit(blueCityPos, new Position(3, 1)); // new blueLegion

    game.endOfTurn(); // now reds turn
    game.moveUnit(redArcherPos, new Position(3, 1)); // redArcher kills new blueLegion

    assertThat(game.getWinner(), is(Player.RED));
  }
}
