package hotciv.standard.TestCivVariants;

import hotciv.framework.*;
import hotciv.standard.GameObserverTest.GameObserverStub;
import hotciv.standard.HotCivFactories.BetaHotcivFactory;
import hotciv.standard.IncrementAgeStrategies.BetaIncrementAgeImpl;
import hotciv.standard.GameImpl;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestBetaCiv {
  private Game game;
  private GameObserverStub go = new GameObserverStub();
  private Position redCityPos = new Position(1,1);
  private Position blueCityPos = new Position(4,1);
  private Position redArcherPos;
  private Position blueLegionPos;

  // quick fix to ensure we get the round end fixed.
  private void roundEnd() {
    game.endOfTurn();
    game.endOfTurn();
  }

  // get age to -100
  private void getAgeTo100BC() {
    for(int i = 0; i < 39; i++) {
      roundEnd();
    }
  }

  // get age to 50
  private void getAgeTo50AD() {
    getAgeTo100BC();
    for(int i = 0; i < 3; i++) {
      roundEnd();
    }
  }

  // get age to 1750
  private void getAgeTo1750AD() {
    getAgeTo50AD();
    for(int i = 0; i < 34; i++) {
      roundEnd();
    }
  }

  // get age to 1900
  private void getAgeTo1900AD() {
    getAgeTo1750AD();
    for(int i = 0; i < 6; i++) {
      roundEnd();
    }
  }

  // get age to 1970
  private void getAgeTo1970AD() {
    getAgeTo1900AD();
    for(int i = 0; i < 14; i++) {
      roundEnd();
    }
  }

  /** Fixture for Betaciv testing. */
  @Before
  public void setUp() {
    game = new GameImpl(new BetaHotcivFactory());
    game.addObserver(go);
    redArcherPos = new Position(2,0);
    blueLegionPos = new Position(3,2);
  }

  // red can win the game
  @Test
  public void redWinsWhenOwningAllCities() {
    game.moveUnit(redArcherPos, new Position(3, 1));
    roundEnd();
    game.moveUnit(new Position(3, 1), blueCityPos);
    assertThat(game.getWinner(), is(Player.RED));
  }

  // blue can win the game
  @Test
  public void blueWinsWhenOwningAllCities() {
    game.endOfTurn();
    game.moveUnit(blueLegionPos, new Position(2, 1));
    roundEnd();
    game.moveUnit(new Position(2, 1), redCityPos);
    assertThat(game.getWinner(), is(Player.BLUE));
  }

  // world age 100 between 4000BC (-4000) and 100BC (-100)
  @Test
  public void age100Between4000And100() {
    getAgeTo100BC();
    assertThat(game.getAge(), is(-100));
  }

  // The sequence should then be -100, -1, 1, 50
  @Test
  public void ageAroundJesusBirth() {
    getAgeTo100BC();

    assertThat(game.getAge(), is(-100));
    roundEnd();
    assertThat(game.getAge(), is(-1));
    roundEnd();
    assertThat(game.getAge(), is(1));
    roundEnd();
    assertThat(game.getAge(), is(50));
  }

  // Between 50AD and 1750AD the world age 50
  @Test
  public void worldAge50Between50And1750() {
    getAgeTo50AD();
    for(int i = 0; i < 34; i++) {
      roundEnd();
    }
    assertThat(game.getAge(), is(1750));
  }

  // Between 1750AD and 1900AD the world age 25
  @Test
  public void worldAge25Between1750And1900() {
    getAgeTo1750AD();
    for(int i = 0; i < 6; i++) {
      roundEnd();
    }
    assertThat(game.getAge(), is(1900));
  }

  // Between 1900AD and 1970AD the world age 5
  @Test
  public void worldAge25Between1900And1970() {
    getAgeTo1900AD();
    for(int i = 0; i < 14; i++) {
      roundEnd();
    }
    assertThat(game.getAge(), is(1970));
  }

  // After 1970AD the world age 1
  @Test
  public void worldAge1After1970() {
    getAgeTo1970AD();
    for(int i = 0; i < 20; i++) {
      roundEnd();
    }
    assertThat(game.getAge(), is(1990));
  }

  // unit test of beta incrementing strategy at age -1.
  @Test
  public void unitTestBetaIncrementAgeAtAge_neg1() {
    assertThat(new BetaIncrementAgeImpl().incrementAge(-1), is(1));
  }

  // unit test of beta incrementing strategy at age 1751
  @Test
  public void unitTestBetaIncrementAgeAt1751() {
    assertThat(new BetaIncrementAgeImpl().incrementAge(1750), is(1775));
  }

  // unit test of beta incrementing strategy at age 2100
  @Test
  public void unitTestBetaIncrementAgeAt2100() {
    assertThat(new BetaIncrementAgeImpl().incrementAge(2100), is(2101));
  }
}
