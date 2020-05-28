package hotciv.framework.StrategyInterfaces;

import hotciv.framework.Position;
import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;
import hotciv.standard.TileImpl;

import java.util.HashMap;
import java.util.List;

public interface CityWorkForceFocus {
  void changeWorkForceFocusInCityAt(Position p,
                                                           String balance,
                                                           GameImpl game);

  int getProductionRate(int citySize, String workFocus);

  int getFoodRate(int citySize, String workFocus);

}
