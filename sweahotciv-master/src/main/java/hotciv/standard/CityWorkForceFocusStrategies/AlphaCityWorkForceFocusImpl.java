package hotciv.standard.CityWorkForceFocusStrategies;

import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.StrategyInterfaces.CityWorkForceFocus;
import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;
import hotciv.standard.TileImpl;

import java.util.*;

public class AlphaCityWorkForceFocusImpl implements CityWorkForceFocus {
  @Override
  public void changeWorkForceFocusInCityAt(Position p,
                                           String balance,
                                           GameImpl game) {

    game.getCityAt(p).newProductionRate(getProductionRate(game.getCityAt(p).getSize(), game.getCityAt(p).getWorkforceFocus()));
    game.getCityAt(p).newFoodRate(getFoodRate(game.getCityAt(p).getSize(), game.getCityAt(p).getWorkforceFocus()));
  }

  @Override
  public int getProductionRate(int citySize, String workFocus) {
    return 6;
  }

  @Override
  public int getFoodRate(int citySize, String workFocus) {
    return 0;
  }
}
