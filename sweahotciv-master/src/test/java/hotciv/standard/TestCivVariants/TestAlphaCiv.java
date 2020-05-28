package hotciv.standard.TestCivVariants;

import hotciv.framework.*;
import hotciv.standard.GameImpl;
import hotciv.standard.GameObserverTest.GameObserverStub;
import hotciv.standard.HotCivFactories.AlphaHotcivFactory;
import hotciv.standard.TileImpl;
import hotciv.standard.UnitImpl;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestAlphaCiv {
  private Game game;
  private GameObserverStub go = new GameObserverStub();
  private Position redCityPos = new Position(1,1);
  private Position blueCityPos = new Position(4,1);
  private Position redArcherPos;
  private Position blueLegionPos;
  private Position blueArcherPos;
  private Position redSettlerPos;
  private UnitImpl redArcher;
  private UnitImpl blueLegion;
  private UnitImpl redSettler;

  // quick fix to ensure we get the round end fixed.
  private void roundEnd() {
    game.endOfTurn();
    game.endOfTurn();
  }

  /** Fixture for alphaciv testing. */
  @Before
  public void setUp() {
    game = new GameImpl( new AlphaHotcivFactory() );
    game.addObserver(go);
    redSettler = new UnitImpl(GameConstants.SETTLER, Player.RED);
    blueLegion = new UnitImpl(GameConstants.LEGION, Player.BLUE);
    redArcher = new UnitImpl(GameConstants.ARCHER, Player.RED);
    redArcherPos = new Position(2,0);
    blueArcherPos = new Position(2,1);
    blueLegionPos = new Position(3,2);
    redSettlerPos = new Position(4,3);
  }

  // FRS p. 455 states that 'Red is the first player to take a turn'.
  @Test
  public void shouldBeRedAsStartingPlayer() {
    assertThat(game.getPlayerInTurn(), is(Player.RED));
  }

  // blue goes next
  @Test
  public void shouldBeBlueAsSecondPlayer() {
    game.endOfTurn();
    assertThat(game.getPlayerInTurn(), is(Player.BLUE));
  }

  // starting age 4000 BC
  @Test
  public void startingAgeShouldBe4000BC() {
    assertThat(game.getAge(), is(-4000));
  }

  // after end of turn age by 100 years
  @Test
  public void shouldAgeBy100yearsAfterEndOfRound() {
    roundEnd();
    assertThat(game.getAge(), is(-4000 + 100));
  }

  // red wins at age 3000 BC ( -3000 )
  @Test
  public void redWinsAtAge3000BC() {
    for(int i = 0; i < 10; i++) {
      roundEnd();
    }
    assertThat(game.getAge(), is(-3000));
    assertThat(game.getWinner(), is(Player.RED));
  }

  // tile of type plain, returns plain type.
  @Test
  public void plainTileReturnPlainType() {
    assertThat(new TileImpl(GameConstants.PLAINS).getTypeString(), is(GameConstants.PLAINS));
  }

  // tile of type mountain return mountains type.
  @Test
  public void mountainsTileReturnMountainsType() {
    assertThat(new TileImpl(GameConstants.MOUNTAINS).getTypeString(), is(GameConstants.MOUNTAINS));
  }

  // (0,0) should be of type plains.
  @Test
  public void coordinat0_0ShouldBeOfTypePlains() {
    assertEquals(game.getTileAt(new Position(0, 0)).getTypeString(), GameConstants.PLAINS);
  }

  // (0,1) is of type hills.
  @Test
  public void coordinat0_1ShouldBeOfTypeHill() {
    assertEquals(game.getTileAt(new Position(0,1)).getTypeString(), GameConstants.HILLS);
  }

  // (1,0) is of type OCEANS
  @Test
  public void coordinat1_0ShouldBeOfTypeOCEANS() {
    assertEquals(game.getTileAt(new Position(1,0)).getTypeString(), GameConstants.OCEANS);
  }

  // (2,2) is of type MOUNTAINS
  @Test
  public void coordinat2_2ShouldBeOfTypeMountains() {
    assertEquals(game.getTileAt(new Position(2,2)).getTypeString(), GameConstants.MOUNTAINS);
  }

  // (7, 12) is of type PLAINS
  @Test
  public void coordinat7_12ShouldBeOfTypePLAINS() {
    assertEquals(game.getTileAt(new Position(7,12)).getTypeString(), GameConstants.PLAINS);
  }

  // (15, 15) is of type PLAINS
  @Test
  public void coordinat15_15ShouldBeOfTypePlains() {
    assertEquals(game.getTileAt(new Position(GameConstants.WORLDSIZE - 1,GameConstants.WORLDSIZE - 1)).getTypeString(), GameConstants.PLAINS);
  }

  // owner of city at (4, 1) is player blue
  @Test
  public void ownerOfRedCityIsRed() {
    assertThat(game.getCityAt(redCityPos).getOwner(), is(Player.RED));
  }

  // owner of city at (4, 1) is player blue
  @Test
  public void ownerOfBlueCityIsBlue() {
    assertThat(game.getCityAt(blueCityPos).getOwner(), is(Player.BLUE));
  }

  // red city is at (1, 1)
  @Test
  public void ownerOfCityAt1_1IsRed() {
    assertThat(game.getCityAt(redCityPos).getOwner(), is(Player.RED));
  }

  // blue city is at (4, 1)
  @Test
  public void ownerOfCityAt4_1IsBlue() {
    assertThat(game.getCityAt(blueCityPos).getOwner(), is(Player.BLUE));
  }

  // each city gains 6 productions by end of turn.
  @Test
  public void treasuryShouldIncrementBy6ByRoundEnd() {
    // assure they start with 0 treasury
    assertThat(game.getCityAt(redCityPos).getTreasury(), is(GameConstants.startTreasury)); 
    assertThat(game.getCityAt(blueCityPos).getTreasury(), is(GameConstants.startTreasury));

    // end a round
    roundEnd();

    // assure they now have 6 productions
    assertThat(game.getCityAt(redCityPos).getTreasury(), is(GameConstants.productionRate));
    assertThat(game.getCityAt(blueCityPos).getTreasury(), is(GameConstants.productionRate));
  }

  // units move count before moving is 1
  @Test
  public void unitMoveCountBeforeMovingShouldBe1() {
    assertThat(redArcher.getMoveCount(), is(GameConstants.landMoveCount));
  }

  // archers should be able to know their own type.
  @Test
  public void archersShouldBeAbleToTellTheirOwnType() {
    assertThat(redArcher.getTypeString(), is(GameConstants.ARCHER));
  }

  // settlers should be able to know their own type.
  @Test
  public void settlersShouldBeAbleToTellTheirOwnType() {
    assertThat(redSettler.getTypeString(), is(GameConstants.SETTLER));
  }

  // red units should know their owner
  @Test
  public void redUnitsShouldKnowTheirOwner() {
    assertThat(redArcher.getOwner(), is(Player.RED));
  }

  // blue units should know their owner
  @Test
  public void blueUnitsShouldKnowTheirOwner() {
    assertThat(blueLegion.getOwner(), is(Player.BLUE));
  }

  //archers should know their own attack strength
  @Test
  public void archerShouldKnowItsOwnAttackStrength() {
    assertThat(redArcher.getAttackingStrength(), is(GameConstants.UNIT_ATTACK_MAP.get(GameConstants.ARCHER)));
  }

  // legions should know their own attack strength
  @Test
  public void legionsShouldKnowItsOwnAttackStrength() {
    assertThat(blueLegion.getAttackingStrength(), is(GameConstants.UNIT_ATTACK_MAP.get(GameConstants.LEGION)));
  }

  // settlers should know their own attack strength
  @Test
  public void settlersShouldKnowItsOwnAttackStrength() {
    assertThat(redSettler.getAttackingStrength(), is(GameConstants.UNIT_ATTACK_MAP.get(GameConstants.SETTLER)));
  }

  // archers should know their own defense strength
  @Test
  public void archerShouldKnowItsOwnDefenseStrength() {
    assertThat(redArcher.getDefensiveStrength(), is(GameConstants.UNIT_DEFEND_MAP.get(GameConstants.ARCHER)));
  }

  // legions should know their own defense strength
  @Test
  public void legionsShouldKnowItsOwnDefenseStrength() {
    assertThat(blueLegion.getDefensiveStrength(), is(GameConstants.UNIT_DEFEND_MAP.get(GameConstants.LEGION)));
  }

  // settlers should know their own defense strength
  @Test
  public void settlersShouldKnowItsOwnDefenseStrength() {
    assertThat(redSettler.getDefensiveStrength(), is(GameConstants.UNIT_DEFEND_MAP.get(GameConstants.SETTLER)));
  }

  // red city's work focus is production
  @Test
  public void redCityWorkFocusIsProduction() {
    assertThat(game.getCityAt(redCityPos).getWorkforceFocus(), is(GameConstants.productionFocus));
  }

  // blue city's work focus is food
  @Test
  public void BlueCityWorkFocusIsFood() {
    assertThat(game.getCityAt(blueCityPos).getWorkforceFocus(), is(GameConstants.foodFocus));
  }

  // city size starts at 1
  @Test
  public void redCitySizeIsOne() {
    assertThat(game.getCityAt(redCityPos).getSize(), is(1));
  }

  // city size stays at 1
  @Test
  public void redCitySizeStaysAtOne() {
    for(int i = 0; i < 150; i++) {
      roundEnd();
    }
    assertThat(game.getCityAt(redCityPos).getSize(), is(1));
  }

  // city size starts at 1
  @Test
  public void blueCitySizeIsOne() {
    assertThat(game.getCityAt(blueCityPos).getSize(), is(1));
  }

  // red should have an archer at (2,0)
  @Test
  public void redShouldHaveArcherAt2_0() {
    assertThat(game.getUnitAt(redArcherPos).getOwner(), is(Player.RED));
  }

  // blue should have a legion at (3,2)
  @Test
  public void blueShouldHaveLegionAt3_2() {
    assertThat(game.getUnitAt(blueLegionPos).getOwner(), is(Player.BLUE));
  }

  // red should have a settler at (4,3)
  @Test
  public void redShouldHaveSettlerAt4_3() {
    assertThat(game.getUnitAt(redSettlerPos).getOwner(), is(Player.RED));
  }

  // red city is producing an archer
  @Test
  public void redCityProducingArcher() {
    assertThat(game.getCityAt(redCityPos).getProduction(), is(GameConstants.ARCHER));
  }

  // blue city is producing a legion
  @Test
  public void blueCityProducingLegion() {
    assertThat(game.getCityAt(blueCityPos).getProduction(), is(GameConstants.LEGION));
  }

  // archers associated action has no effect
  @Test
  public void archersActionHasNoEffect() {
    assertThat(redArcher.getDefensiveStrength(), is(GameConstants.UNIT_DEFEND_MAP.get(GameConstants.ARCHER)));
    assertThat(redArcher.getMoveCount(), is(GameConstants.landMoveCount));
    game.performUnitActionAt(redArcherPos);
    assertThat(redArcher.getDefensiveStrength(), is(GameConstants.UNIT_DEFEND_MAP.get(GameConstants.ARCHER)));
    assertThat(redArcher.getMoveCount(), is(GameConstants.landMoveCount));
  }

  // settlers associated action has no effect
  @Test
  public void settlersActionHasNoEffect() {
    assertThat(redSettler.getMoveCount(), is(GameConstants.landMoveCount));
    game.performUnitActionAt(redSettlerPos);
    assertNull(game.getCityAt(redSettlerPos));
    assertThat(redSettler.getMoveCount(), is(GameConstants.landMoveCount));
  }

  // cities can change unit production
  @Test
  public void redCityCanChangeUnitProduction() {
    game.changeProductionInCityAt(redCityPos, GameConstants.SETTLER);
  }

  // cities can change unit production
  @Test
  public void blueCityCanChangeUnitProduction() {
    game.changeProductionInCityAt(blueCityPos, GameConstants.ARCHER);
  }

  // a city can only make an archer with >= 10 production
  @Test
  public void redCityNeeds10OrMoreToMakeArcher() {
    assertThat(game.getCityAt(redCityPos).getTreasury(), is(GameConstants.startTreasury));
    roundEnd();
    assertThat(game.getCityAt(redCityPos).getTreasury(), is(GameConstants.productionRate));
    roundEnd();
    assertThat(game.getCityAt(redCityPos).getTreasury(), is(2 * GameConstants.productionRate - GameConstants.archerCost));
    assertThat(game.getUnitAt(redCityPos).getTypeString(), is(GameConstants.ARCHER));
  }

  // a city can only make a legion with >= 15 production
  @Test
  public void cityNeeds15OrMoreToMakeLegion() {
    assertThat(game.getCityAt(blueCityPos).getTreasury(), is(GameConstants.startTreasury));
    roundEnd();
    assertThat(game.getCityAt(blueCityPos).getTreasury(), is(GameConstants.productionRate));
    roundEnd();
    assertThat(game.getCityAt(blueCityPos).getTreasury(), is(2 * GameConstants.productionRate));
    roundEnd();
    assertThat(game.getCityAt(blueCityPos).getTreasury(), is(3 * GameConstants.productionRate - GameConstants.legionCost));
    assertThat(game.getUnitAt(blueCityPos).getTypeString(), is(GameConstants.LEGION));
  }

  // a city can only make a settler with >= 30 production
  @Test
  public void cityNeeds30OrMoreToMakeSettler() {
    // ensure that redCity is set to produce settlers.
    game.changeProductionInCityAt(redCityPos, GameConstants.SETTLER);

    // for treasury from 0 to 24
    for(int i = 0; i < 4; i++) {
      roundEnd();
    }

    // there should be no unit at the redCityPos.
    assertNull(game.getUnitAt(redCityPos));

    // for treasury from 24 to 30, buy unit and treasury now at 0
    roundEnd();
    assertThat(game.getCityAt(redCityPos).getTreasury(), is(5 * GameConstants.productionRate - GameConstants.settlerCost));
    assertNotNull(game.getUnitAt(redCityPos));
    }

  // the second unit made in a city is spawned north of the city
  @Test
  public void unitPlacedNorthOfCityIfUnitInCity() {
    // making a unit in the city
    for(int i = 0; i < 3; i++) {
      roundEnd();
    }
    assertNotNull(game.getUnitAt(redCityPos));
    assertNull(game.getUnitAt(new Position(0, 1)));
    roundEnd();
    assertNotNull(game.getUnitAt(new Position(0, 1)));
  }

  // the forth unit made in a city is spawned east of the city
  @Test
  public void forthUnitMadeInCityIsMadeRightOfCity() {
    // making a unit in the city
    for(int i = 0; i < 6; i++) {
      roundEnd();
    }
    assertNotNull(game.getUnitAt(redCityPos));
    assertNull(game.getUnitAt(new Position(1, 2)));
    roundEnd();
    assertNotNull(game.getUnitAt(new Position(1, 2)));
  }

  // all units should only be able to move 1 tile.
  @Test
  public void unitsShouldOnlyHaveMoveCountOf1() {
    assertThat(new UnitImpl(GameConstants.ARCHER, Player.RED).getMoveCount(), is(GameConstants.landMoveCount));
    assertThat(new UnitImpl(GameConstants.LEGION, Player.BLUE).getMoveCount(), is(GameConstants.landMoveCount));
    assertThat(new UnitImpl(GameConstants.SETTLER, Player.RED).getMoveCount(), is(GameConstants.landMoveCount));
  }

  // a unit can move from (2,0) to (2,1)
  @Test
  public void theMove2_0to2_1IsALegalMove() {
    assertThat(game.moveUnit(redArcherPos, new Position(2, 1)), is(Boolean.TRUE));
  }

  // an archer is actually moving 1 Tile
  @Test
  public void anArcherIsAbleToMove1Tile() {
    // we're moving an archer
    game.moveUnit(new Position(2,0), new Position(2,1));
    // we're checking the archer has been moved
    assertThat(game.getUnitAt(new Position(2,1)).getTypeString(), is(GameConstants.ARCHER));
    assertNull(game.getUnitAt(redArcherPos));
  }

  // the attacking units always win
  @Test
  public void theAttackingUnitWins() {
    // tries to move the red unit onto the blue unit.
    assertThat(game.moveUnit(redArcherPos, blueArcherPos), is(true));

    // checks the redArcher is now at the blue units position
    assertThat(game.getUnitAt(blueArcherPos).getTypeString(), is(GameConstants.ARCHER));
    assertThat(game.getUnitAt(blueArcherPos).getOwner(), is(Player.RED));

    // checking that the redArchers old Position is now empty
    assertNull(game.getUnitAt(redArcherPos));
  }

  // a unit cannot move on an ocean tile
  @Test
  public void unitCannotMoveOntoOceanTile() {
    assertThat(game.moveUnit(redArcherPos, new Position(1,0)),is(false));
  }

  // a unit cannot move on an mountain tile
  @Test
  public void unitCannotMoveOntoMountainTile() {
    assertThat(game.moveUnit(redArcherPos, new Position(2,2)),is(false));
  }

  // a unit cannot tp.
  @Test
  public void unitsCantTP() {
    assertThat(game.moveUnit(redArcherPos, new Position(10, 10)), is(false));
  }

  // a unit cannot move more than their moveCount allows.
  @Test
  public void unitsCannotMoveMoreThanMoveCountAllows() {
    assertThat(redArcher.getMoveCount(), is(GameConstants.landMoveCount));
    game.moveUnit(redArcherPos, new Position(2, 1));
    assertThat(game.getUnitAt(new Position(2,1)).getMoveCount(), is(GameConstants.landMoveCount - 1));
    assertThat(game.moveUnit(new Position(2,1), redArcherPos), is(false));
  }

  // test that the moveCount resets every round end
  @Test
  public void moveCountResetsAfterARound() {
    assertThat(game.getUnitAt(redArcherPos).getMoveCount(), is(GameConstants.landMoveCount));
    game.moveUnit(redArcherPos, new Position(2, 1));
    assertThat(game.getUnitAt(new Position(2,1)).getMoveCount(), is(GameConstants.landMoveCount - 1));
    roundEnd();
    assertThat(game.getUnitAt(new Position(2,1)).getMoveCount(), is(GameConstants.landMoveCount));
  }

  // test for changing work force focus.
  @Test
  public void changeOfWorkForceFocusHasNoEffect() {
    assertThat(game.getCityAt(redCityPos).getWorkforceFocus(), is(GameConstants.productionFocus));
    game.changeWorkForceFocusInCityAt(redCityPos, GameConstants.foodFocus);
    assertThat(game.getCityAt(redCityPos).getWorkforceFocus(), is(GameConstants.productionFocus));
  }

  // red can take blues city
  @Test
  public void redCanTakeBlueCity() {
    game.moveUnit(redArcherPos, new Position(3, 1));
    roundEnd();
    game.moveUnit(new Position(3, 1), blueCityPos);
    assertThat(game.getCityAt(blueCityPos).getOwner(), is(Player.RED));
  }

  // blue can take red city
  @Test
  public void blueCanTakeRedCity() {
    game.endOfTurn();
    game.moveUnit(blueLegionPos, new Position(2, 1));
    roundEnd();
    game.moveUnit(new Position(2, 1), redCityPos);
    assertThat(game.getCityAt(redCityPos).getOwner(), is(Player.BLUE));
  }
}