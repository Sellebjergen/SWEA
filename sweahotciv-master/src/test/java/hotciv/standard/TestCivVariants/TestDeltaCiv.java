package hotciv.standard.TestCivVariants;

import hotciv.framework.*;
import hotciv.standard.GameObserverTest.GameObserverStub;
import hotciv.standard.HotCivFactories.DeltaHotcivFactory;
import hotciv.standard.GameImpl;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestDeltaCiv {
  private Game game;
  private GameObserverStub go = new GameObserverStub();
  private Position redCityPos = new Position(8,12);
  private Position blueCityPos = new Position(4,5);
  private Position redArcherPos;
  private Position blueLegionPos;

  /** Fixture for Deltaciv testing. */
  @Before
  public void setUp() {
    game = new GameImpl(new DeltaHotcivFactory());
    game.addObserver(go);
    redArcherPos = new Position(3,8);
    blueLegionPos = new Position(4,4);
  }

  // DeltaCiv should have a forrest at tile (9,1)
  @Test
  public void forrestAt9_1() {
    assertThat(game.getTileAt(new Position(9, 1)).getTypeString(), is(GameConstants.FOREST));
  }

  // DeltaCiv should have a ocean at tile (15,15)
  @Test
  public void oceanAt15_15() {
    assertThat(game.getTileAt(new Position(15, 15)).getTypeString(), is(GameConstants.OCEANS));
  }

  // DeltaCiv should have a hill at tile (3,1)
  @Test
  public void hillAt3_1() {
    assertThat(game.getTileAt(new Position(6, 6)).getTypeString(), is(GameConstants.OCEANS));
  }

  // DeltaCiv should have a forrest at tile (9,1)
  @Test
  public void hillAt9_1() {
    assertThat(game.getTileAt(new Position(1, 3)).getTypeString(), is(GameConstants.HILLS));
  }

  // red should have archer at (3,8)
  @Test
  public void redArcherAt3_8() {
    assertThat(game.getUnitAt(redArcherPos).getOwner(), is(Player.RED));
  }

  // blue should have legion at (4,4)
  @Test
  public void blueLegionAt4_4() {
    assertThat(game.getUnitAt(blueLegionPos).getOwner(), is(Player.BLUE));
  }

  // red should have city at (8,12)
  @Test
  public void redCityAt8_12() {
    assertThat(game.getCityAt(redCityPos).getOwner(), is(Player.RED));
  }

  // blue should have city at (4,5)
  @Test
  public void blueCityAt4_5() {
    assertThat(game.getCityAt(blueCityPos).getOwner(), is(Player.BLUE));
  }
}