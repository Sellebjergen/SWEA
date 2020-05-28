package hotciv.standard.IncrementCitySizeStrategies;

import hotciv.framework.Position;
import hotciv.framework.StrategyInterfaces.IncrementCitySizeStrategy;
import hotciv.standard.CityImpl;

import java.util.HashMap;

public class EtaIncrementCitySizeImpl implements IncrementCitySizeStrategy {

  @Override
  public HashMap<Position, CityImpl> incrementCitySize(Position p, HashMap<Position, CityImpl> cities) {
    CityImpl city = cities.get(p);

    if(city.getSize() < 9) {
      if (cityGotEnoughFood(city)) cities.get(p).incrementCitySize();
    }

    return cities;
  }

  private Boolean cityGotEnoughFood(CityImpl city) {
    int citySize = city.getSize();
    return city.getFoodStorage() > (5 + citySize * 3);
  }
}