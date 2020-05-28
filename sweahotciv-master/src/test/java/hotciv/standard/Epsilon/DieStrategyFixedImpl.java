package hotciv.standard.Epsilon;

import hotciv.framework.StrategyInterfaces.DieStrategy;

public class DieStrategyFixedImpl implements DieStrategy {
  @Override
  public int rollDie() {
    return 5;
  }

  @Override
  public int rollDie2() { return 2; }
}
