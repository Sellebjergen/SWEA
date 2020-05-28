package hotciv.standard.IncrementAgeStrategies;

import hotciv.framework.StrategyInterfaces.IncrementAgeStrategy;

public class AlphaIncrementAgeImpl implements IncrementAgeStrategy {
  @Override
  public int incrementAge(int age) {
    return age + 100;
  }
}
