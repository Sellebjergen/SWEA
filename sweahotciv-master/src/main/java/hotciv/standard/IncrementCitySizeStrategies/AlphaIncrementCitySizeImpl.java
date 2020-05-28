package hotciv.standard.IncrementCitySizeStrategies;

import hotciv.framework.Position;
import hotciv.framework.StrategyInterfaces.IncrementCitySizeStrategy;
import hotciv.standard.CityImpl;

import java.util.HashMap;

public class AlphaIncrementCitySizeImpl implements IncrementCitySizeStrategy {
  @Override
  public HashMap<Position, CityImpl> incrementCitySize(Position p, HashMap<Position, CityImpl> cities) {
    return cities;
  }
}
