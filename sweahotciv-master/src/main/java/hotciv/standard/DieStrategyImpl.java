package hotciv.standard;

import hotciv.framework.StrategyInterfaces.DieStrategy;
import java.util.Random;

public class DieStrategyImpl implements DieStrategy {

  private Random random;

  public DieStrategyImpl() {
    random = new Random();
  }

  @Override
  public int rollDie() {
    return random.nextInt(6) + 1;
  }

  @Override
  public int rollDie2() {
    return random.nextInt(6) + 1;
  }
}
