package hotciv.standard.GameObserverTest;

import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.standard.GameImpl;
import hotciv.standard.HotCivFactories.AlphaHotcivFactory;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestGameObserver {
  private GameObserverStub go = new GameObserverStub();
  private Game game;

  private Position redCityPos;
  private Position blueCityPos;

  private Position redArcherPos = new Position(2,0);
  private Position blueLegionPos = new Position(3,2);
  private Position redSettlerPos = new Position(4,3);

  @Before
  public void setup() {
    game = new GameImpl(new AlphaHotcivFactory());
    game.addObserver(go);
    redCityPos = new Position(1,1);
    blueCityPos = new Position(4,1);
  }

  @Test
  public void playerInTurn() {
    game.endOfTurn();
    assertThat(go.getPlayers().get(0), is(Player.RED));
    game.endOfTurn();
    assertThat(go.getPlayers().get(1), is(Player.BLUE));
  }

  @Test
  public void observerGetsMovingCoordinates() {
    game.moveUnit(redArcherPos, new Position(2, 1));
    assertThat(go.getWorldChangeMap().get(0), is(redArcherPos));
    assertThat(go.getWorldChangeMap().get(1), is(new Position(2, 1)));
  }

  @Test
  public void observerGetAgesCorrect() {
    game.endOfTurn();
    assertThat(go.getEndAges().get(0), is(-4000));  // first time red goes on endAge with -4000
    assertThat(go.getPlayers().get(0), is(Player.RED));
    game.endOfTurn();
    assertThat(go.getEndAges().get(1), is(-3900));
    assertThat(go.getPlayers().get(1), is(Player.BLUE));
    game.endOfTurn();
    assertThat(go.getEndAges().get(2), is(-3900));   // now it's blue
    assertThat(go.getPlayers().get(2), is(Player.RED));
    game.endOfTurn();
    assertThat(go.getEndAges().get(3), is(-3800));  // now it's red
    assertThat(go.getPlayers().get(3), is(Player.BLUE));
  }

  @Test
  public void nothingInterferes() {
    // red turn
    game.moveUnit(redArcherPos, new Position(2, 1));
    game.endOfTurn();
    assertThat(go.getEndAges().get(0), is(-4000));
    assertThat(go.getPlayers().get(0), is(Player.RED));
    assertThat(go.getWorldChangeMap().get(0), is(redArcherPos));
    assertThat(go.getWorldChangeMap().get(1), is(new Position(2, 1)));

    // blue turn
    game.moveUnit(blueLegionPos, new Position(3, 1));
    game.endOfTurn();
    assertThat(go.getEndAges().get(1), is(-3900));
    assertThat(go.getPlayers().get(1), is(Player.BLUE));
    assertThat(go.getWorldChangeMap().get(2), is(blueLegionPos));
    assertThat(go.getWorldChangeMap().get(3), is(new Position(3, 1)));
  }

  @Test
  public void whenUnitActionIsPerformed() {
    game.performUnitActionAt(redSettlerPos);
    assertThat(go.getWorldChangeMap().get(0), is(redSettlerPos));
  }

  @Test
  public void creatingUnitFromCityShouldNotifyObserver() {
    // red should create an archer after 3'rd round.
    game.endOfTurn();
    game.endOfTurn(); // round 2
    game.endOfTurn();
    game.endOfTurn(); // round 3
    assertThat(go.getWorldChangeMap().get(0), is(new Position(1, 1)));
  }
}
