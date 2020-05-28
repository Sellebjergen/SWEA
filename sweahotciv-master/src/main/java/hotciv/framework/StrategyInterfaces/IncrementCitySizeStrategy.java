package hotciv.framework.StrategyInterfaces;

import hotciv.framework.Position;
import hotciv.standard.CityImpl;

import java.util.HashMap;

public interface IncrementCitySizeStrategy {
  HashMap<Position, CityImpl> incrementCitySize(Position p, HashMap<Position, CityImpl> cities);
}
