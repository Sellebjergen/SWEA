package hotciv.standard.IncrementAgeStrategies;

import hotciv.framework.StrategyInterfaces.IncrementAgeStrategy;

public class BetaIncrementAgeImpl implements IncrementAgeStrategy {
  @Override
  public int incrementAge(int age) {
    if(age < -100) {
      return age + 100;
    }
    else if(age < -1) {
      return age + 99;
    }
    else if(age < 1) {
      return age + 2;
    }
    else if(age < 50) {
      return age + 49;
    }
    else if(age < 1750) {
      return age + 50;
    }
    else if(age < 1900) {
      return age + 25;
    }
    else if(age < 1970) {
      return  age + 5;
    }
    return age + 1;
  }
}