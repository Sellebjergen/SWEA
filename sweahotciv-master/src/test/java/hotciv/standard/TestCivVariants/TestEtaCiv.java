package hotciv.standard.TestCivVariants;

import hotciv.framework.*;
import hotciv.standard.GameObserverTest.GameObserverStub;
import hotciv.standard.HotCivFactories.EtaHotCivFactory;
import hotciv.standard.GameImpl;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestEtaCiv {
  private Game game;
  private GameObserverStub go = new GameObserverStub();
  private Position redCityPos = new Position(1,1);
  private Position blueCityPos = new Position(4,1);

  // ends the round
  private void roundEnd() {
    game.endOfTurn();
    game.endOfTurn();
  }

  // get the city size to 4 with Production
  private void citySizeTo4WithProduction(Position cityPos) {
    citySizeTo4WithFood(cityPos);
    game.changeWorkForceFocusInCityAt(cityPos, GameConstants.productionFocus);
  }

  // get the city size to 6 with Production
  private void citySizeTo6WithProduction(Position cityPos) {
    citySizeTo6WithFood(cityPos);
    game.changeWorkForceFocusInCityAt(cityPos, GameConstants.productionFocus);
  }

  // get the city size to 4 with Food
  private void citySizeTo4WithFood(Position cityPos) {
    game.changeWorkForceFocusInCityAt(cityPos, GameConstants.foodFocus);

    for(int i = 0; i < 15; i++) {
      game.endOfTurn();
      game.endOfTurn();
    }
  }

  // get the city size to 6 with Food
  private void citySizeTo6WithFood(Position cityPos) {
    game.changeWorkForceFocusInCityAt(cityPos, GameConstants.foodFocus);

    for(int i = 0; i < 19; i++) {
      game.endOfTurn();
      game.endOfTurn();
    }
  }

  /** Fixture for Etaciv testing. */
  @Before
  public void setUp() {
    game = new GameImpl(new EtaHotCivFactory());
    game.addObserver(go);
  }

  @Test
  public void redCityWithSize1Gets1FoodAnd1Production() {
    assertThat(game.getCityAt(redCityPos).getSize(), is(1));
    assertThat(game.getCityAt(redCityPos).getTreasury(), is(0));
    assertThat(game.getCityAt(redCityPos).getFoodStorage(), is(0));

    roundEnd();

    assertThat(game.getCityAt(redCityPos).getSize(), is(1));
    assertThat(game.getCityAt(redCityPos).getTreasury(), is(1));
    assertThat(game.getCityAt(redCityPos).getFoodStorage(), is(1));
  }

  @Test
  public void redCityWithSize4AndProduction() {
    game.changeProductionInCityAt(redCityPos, GameConstants.SETTLER);

    citySizeTo4WithProduction(redCityPos);

    // the city now have size 4, 15 production and 0 food
    roundEnd();

    assertThat(game.getCityAt(redCityPos).getSize(), is(4));
    assertThat(game.getCityAt(redCityPos).getTreasury(), is(19));
    assertThat(game.getCityAt(redCityPos).getFoodStorage(), is(1));
  }

  @Test
  public void blueCityWithSize6AndProduction() {
    game.changeProductionInCityAt(blueCityPos, GameConstants.SETTLER); // so we see the right amount later
    game.changeWorkForceFocusInCityAt(blueCityPos, GameConstants.productionFocus);

    citySizeTo6WithProduction(blueCityPos);

    // the city now have size 6, 19 production and 0 food
    roundEnd();

    assertThat(game.getCityAt(blueCityPos).getSize(), is(6));
    assertThat(game.getCityAt(blueCityPos).getTreasury(), is(20)); // only one up as blueCity have no hills or mountains
    assertThat(game.getCityAt(blueCityPos).getFoodStorage(), is(1));
  }

  @Test
  public void redCityCanChangeWorkFocusToFood() {
    assertThat(game.getCityAt(redCityPos).getWorkforceFocus(), is(GameConstants.productionFocus));
    game.changeWorkForceFocusInCityAt(redCityPos, GameConstants.foodFocus);
    assertThat(game.getCityAt(redCityPos).getWorkforceFocus(), is(GameConstants.foodFocus));
  }

  @Test
  public void blueCityCanChangeWorkFocusToProduction() {
    assertThat(game.getCityAt(blueCityPos).getWorkforceFocus(), is(GameConstants.foodFocus));
    game.changeWorkForceFocusInCityAt(blueCityPos, GameConstants.productionFocus);
    assertThat(game.getCityAt(blueCityPos).getWorkforceFocus(), is(GameConstants.productionFocus));
  }

  @Test
  public void redCityWithSize1Gets1FoodAnd1Food() {
    game.changeWorkForceFocusInCityAt(blueCityPos, GameConstants.foodFocus);

    assertThat(game.getCityAt(blueCityPos).getSize(), is(1));
    assertThat(game.getCityAt(blueCityPos).getTreasury(), is(0));
    assertThat(game.getCityAt(blueCityPos).getFoodStorage(), is(0));

    roundEnd();

    assertThat(game.getCityAt(blueCityPos).getSize(), is(1));
    assertThat(game.getCityAt(blueCityPos).getTreasury(), is(1));
    assertThat(game.getCityAt(blueCityPos).getFoodStorage(), is(1));
  }

  @Test
  public void blueCityWithSize4AndFood() {
    game.changeWorkForceFocusInCityAt(blueCityPos, GameConstants.foodFocus);
    game.changeProductionInCityAt(blueCityPos, GameConstants.ARCHER);

    citySizeTo4WithFood(blueCityPos);

    // the city now have size 4, 5 production and 0 food
    roundEnd();

    assertThat(game.getCityAt(blueCityPos).getSize(), is(4));
    assertThat(game.getCityAt(blueCityPos).getTreasury(), is(6));
    assertThat(game.getCityAt(blueCityPos).getFoodStorage(), is(10));
  }

  @Test
  public void redCityWithSize6AndFood() {
    game.changeWorkForceFocusInCityAt(redCityPos, GameConstants.foodFocus);
    game.changeProductionInCityAt(redCityPos, GameConstants.SETTLER);

    citySizeTo6WithFood(redCityPos);

    // the city now have size 6, 19 production and 0 food
    roundEnd();

    assertThat(game.getCityAt(redCityPos).getSize(), is(6));
    assertThat(game.getCityAt(redCityPos).getTreasury(), is(20));
    assertThat(game.getCityAt(redCityPos).getFoodStorage(), is(16));
  }
}
