package hotciv.standard.Epsilon;

import hotciv.framework.HotcivFactory;
import hotciv.framework.StrategyInterfaces.*;
import hotciv.standard.AttackingStrategies.EpsilonAttackingStrategyImpl;
import hotciv.standard.CityWorkForceFocusStrategies.AlphaCityWorkForceFocusImpl;
import hotciv.standard.IncrementAgeStrategies.AlphaIncrementAgeImpl;
import hotciv.standard.IncrementCitySizeStrategies.AlphaIncrementCitySizeImpl;
import hotciv.standard.UnitActionsStrategies.AlphaUnitActionsImpl;
import hotciv.standard.WinningStrategies.EpsilonWinningStrategyImpl;
import hotciv.standard.WorldLayoutStrategies.AlphaWorldLayoutImpl;

public class FixedEpsilonHotCivFactory implements HotcivFactory {

  @Override
  public AttackingStrategy createAttackingStrategy() {
    return new EpsilonAttackingStrategyImpl(createDieStrategy());
  }

  @Override
  public CityWorkForceFocus createCityWorkForceFocusStrategy() {
    return new AlphaCityWorkForceFocusImpl();
  }

  @Override
  public IncrementAgeStrategy createIncrementAgeStrategy() {
    return new AlphaIncrementAgeImpl();
  }

  @Override
  public IncrementCitySizeStrategy createIncrementCitySizeStrategy() {
    return new AlphaIncrementCitySizeImpl();
  }

  @Override
  public UnitActionsStrategy createUnitActionsStrategy() {
    return new AlphaUnitActionsImpl();
  }

  @Override
  public WinningStrategy createWinningStrategy() {
    return new EpsilonWinningStrategyImpl();
  }

  @Override
  public WorldLayoutStrategy createWorldLayoutStrategy() {
    return new AlphaWorldLayoutImpl();
  }

  @Override
  public DieStrategy createDieStrategy() {
    return new DieStrategyFixedImpl();
  }
}
