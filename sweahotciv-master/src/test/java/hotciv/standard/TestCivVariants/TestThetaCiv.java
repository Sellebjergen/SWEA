package hotciv.standard.TestCivVariants;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.standard.GameImpl;
import hotciv.standard.GameObserverTest.GameObserverStub;
import hotciv.standard.HotCivFactories.ThetaHotCivFactory;
import hotciv.standard.Theta.FixedThetaHotCivFactory;
import hotciv.standard.TileImpl;
import hotciv.standard.UnitImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TestThetaCiv {
  private Game game;
  private GameObserverStub go = new GameObserverStub();
  private Position redCityPos = new Position(1,1);
  private Position blueCityPos = new Position(4,1);
  // private Position redArcherPos;
  // private Position blueLegionPos;

  @Before
  public void setUp() {
    game = new GameImpl(new ThetaHotCivFactory());
    game.addObserver(go);
    // redArcherPos  = new Position(2, 0);
    // blueLegionPos = new Position(3, 2);
    redCityPos = new Position(1, 1);
  }

  @Test
  public void aB52BomberCanBeProduced() {
    assertNull(game.getUnitAt(redCityPos));
    produceB52(redCityPos);
    assertThat(game.getUnitAt(redCityPos).getTypeString(), is(GameConstants.B52));
  }

  @Test
  public void aB52ShouldBeAbleToMove2Times() {
    produceB52(redCityPos);
    assertThat(game.getUnitAt(redCityPos).getMoveCount(), is(2));
    assertTrue(game.moveUnit(redCityPos, new Position(2,1)));
    assertTrue(game.moveUnit(new Position(2,1), new Position(3,1)));
  }

  @Test
  public void aB52EffectShouldChangeForestToPlains() {
    Position pointOfInterest = new Position(1,2);
    addTileOnPos(pointOfInterest, GameConstants.FOREST);

    // testing if the bomber makes forest to plains.
    produceB52(redCityPos);
    game.moveUnit(redCityPos, pointOfInterest);
    game.performUnitActionAt(pointOfInterest);
    assertThat(game.getTileAt(pointOfInterest).getTypeString(), is(GameConstants.PLAINS));
  }

  @Test
  public void aB52ShouldBeAbleToMoveOverMountains() {
    produceB52(redCityPos);
    assertTrue(game.moveUnit(redCityPos, new Position(2,2)));
  }

  @Test
  public void aB52ShouldBeAbleToMoveOverOceans() {
    produceB52(redCityPos);
    assertTrue(game.moveUnit(redCityPos, new Position(1, 0)));
  }

  @Test
  public void aB52BomberShouldRemoveCityWithEffectIfCitySizeIs1() {   // doesn't work as pos (2,2) is mountain.
    // test for the b52Effect on cities.
    produceB52(redCityPos);
    game.moveUnit(redCityPos, new Position(2,2));
    game.moveUnit(new Position(2,2), new Position(3,2));
    roundEnd();
    game.moveUnit(new Position(3,2), new Position(4,2));
    game.moveUnit(new Position(4,2), blueCityPos);
    assertThat(game.getUnitAt(blueCityPos).getTypeString(), is(GameConstants.B52));
    game.performUnitActionAt(blueCityPos);
    assertNull(game.getCityAt(blueCityPos));
  }

  // ============================================================ //
  private void roundEnd() {
    game.endOfTurn();
    game.endOfTurn();
  }

  private void addTileOnPos(Position pointOfInterest, String tileType) {
    HashMap<Position, TileImpl> tiles = ((GameImpl) game).getTiles();
    tiles.put(pointOfInterest, new TileImpl(tileType));
    ((GameImpl) game).setTiles(tiles);
    assertThat(game.getTileAt(pointOfInterest).getTypeString(), is(tileType));
  }

  private void produceB52(Position cityPos) {
    game.changeProductionInCityAt(cityPos, GameConstants.B52);
    for (int i = 0; i < 10; i++) {
      roundEnd();
    }
  }
}
