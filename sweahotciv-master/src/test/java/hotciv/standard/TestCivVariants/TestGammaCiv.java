package hotciv.standard.TestCivVariants;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;

import hotciv.standard.GameObserverTest.GameObserverStub;
import hotciv.standard.HotCivFactories.GammaHotCivFactory;
import hotciv.standard.GameImpl;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestGammaCiv {
  private Game game;
  private GameObserverStub go = new GameObserverStub();
  private Position redArcherPos;
  private Position blueLegionPos;
  private Position redSettlerPos;
  
  private int archerDefense = GameConstants.UNIT_DEFEND_MAP.get(GameConstants.ARCHER);

  /** Fixture for Gammaciv testing. */
  @Before
  public void setUp() {
    game = new GameImpl(new GammaHotCivFactory());
    game.addObserver(go);
    redArcherPos = new Position(2,0);
    blueLegionPos = new Position(3,2);
    redSettlerPos = new Position(4,3);
  }

  // testing the settler action.
  @Test
  public void settlersUnitActionsMakeACityWithSameOwner() {
    assertThat(game.getUnitAt(redSettlerPos).getTypeString(), is(GameConstants.SETTLER));
    game.performUnitActionAt(redSettlerPos);
    assertThat(game.getCityAt(redSettlerPos).getOwner(), is(Player.RED));
  }

  // the size of the city created by the settler is 1
  @Test
  public void citySizeAfterSettlerActionIs1() {
    game.performUnitActionAt(redSettlerPos);
    assertThat(game.getCityAt(redSettlerPos).getSize(), is(1));
  }

  // a legion can't activate unit action
  @Test
  public void legionCantActivateUnitAction() {
    game.performUnitActionAt(blueLegionPos);
    assertThat(game.getUnitAt(blueLegionPos).getDefensiveStrength(), is(GameConstants.UNIT_DEFEND_MAP.get(GameConstants.LEGION)));
  }

  // an archer doubles it's defense when going into fortify
  @Test
  public void archerDoublesDefence() {
    game.performUnitActionAt(redArcherPos);
    assertThat(game.getUnitAt(redArcherPos).getDefensiveStrength(), is(GameConstants.UNIT_DEFEND_MAP.get(GameConstants.ARCHER) * 2));
  }

  // archers moveCount goes to 0, when it activate it's effect.
  @Test
  public void archersMoveCountGoesToZeroWhenEffectActivates() {
    game.performUnitActionAt(redArcherPos);
    assertThat(game.getUnitAt(redArcherPos).getMoveCount(), is(0));
  }

  // archers can't move and then use their effect
  @Test
  public void archersCannotMoveBeforeEffect() {
    Position moveTo = new Position (2,1);

    game.moveUnit(redArcherPos, moveTo);
    game.performUnitActionAt(moveTo);
    assertThat(game.getUnitAt(moveTo).getDefensiveStrength(), is(GameConstants.UNIT_DEFEND_MAP.get(GameConstants.ARCHER)));
  }

  // if archer effect is active, used again to remove extra defence and reset moveCount.
  @Test
  public void activateArcherEffectAgainToReturnToNormalStats() {
    game.performUnitActionAt(redArcherPos);
    game.performUnitActionAt(redArcherPos);
    assertThat(game.getUnitAt(redArcherPos).getDefensiveStrength(), is(GameConstants.UNIT_DEFEND_MAP.get(GameConstants.ARCHER)));
    assertThat(game.getUnitAt(redArcherPos).getMoveCount(), is(GameConstants.landMoveCount));
  }
}