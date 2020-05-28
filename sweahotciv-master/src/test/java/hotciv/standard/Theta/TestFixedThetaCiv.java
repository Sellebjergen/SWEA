package hotciv.standard.Theta;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.standard.GameImpl;
import hotciv.standard.GameObserverTest.GameObserverStub;
import hotciv.standard.TileImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TestFixedThetaCiv {
  private Game game;
  private GameObserverStub go = new GameObserverStub();
  private Position redCityPos = new Position(1,1);
  private Position blueCityPos = new Position(4,1);
  // private Position redArcherPos;
  // private Position blueLegionPos;

  @Before
  public void setUp() {
    game = new GameImpl(new FixedThetaHotCivFactory());
    game.addObserver(go);
    // redArcherPos  = new Position(2, 0);
    // blueLegionPos = new Position(3, 2);
    redCityPos = new Position(1, 1);
  }

  @Test
  public void aB52ShouldBeAbleToDecreaseTheCityPopBy1() {
    produceB52(redCityPos);
    int initialCitySize = game.getCityAt(blueCityPos).getSize();
    game.moveUnit(redCityPos, new Position(2,2));
    game.moveUnit(new Position(2,2), new Position(3,2));
    roundEnd();
    game.moveUnit(new Position(3,2), new Position(4,2));
    game.moveUnit(new Position(4,2), blueCityPos);
    assertThat(game.getCityAt(blueCityPos).getSize(), is(initialCitySize));
    game.performUnitActionAt(blueCityPos);
    assertThat(game.getCityAt(blueCityPos).getSize(), is(initialCitySize - 1));
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
    for (int i = 0; i < 50; i++) {
      roundEnd();
    }
  }
}
