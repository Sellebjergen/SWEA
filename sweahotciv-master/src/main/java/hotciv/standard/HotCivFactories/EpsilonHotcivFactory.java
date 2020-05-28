package hotciv.standard.HotCivFactories;

import hotciv.framework.HotcivFactory;
import hotciv.framework.StrategyInterfaces.*;
import hotciv.standard.AttackingStrategies.EpsilonAttackingStrategyImpl;
import hotciv.standard.WinningStrategies.EpsilonWinningStrategyImpl;

public class EpsilonHotcivFactory extends AlphaHotcivFactory implements HotcivFactory {
  @Override
  public AttackingStrategy createAttackingStrategy() {
    return new EpsilonAttackingStrategyImpl(createDieStrategy());
  }
  @Override
  public WinningStrategy createWinningStrategy() {
    return new EpsilonWinningStrategyImpl();
  }
}
