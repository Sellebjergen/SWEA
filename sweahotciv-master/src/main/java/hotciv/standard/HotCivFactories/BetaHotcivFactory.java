package hotciv.standard.HotCivFactories;

import hotciv.framework.HotcivFactory;
import hotciv.framework.StrategyInterfaces.*;
import hotciv.standard.AttackingStrategies.AlphaAttackingStrategyImpl;
import hotciv.standard.IncrementAgeStrategies.BetaIncrementAgeImpl;
import hotciv.standard.WinningStrategies.BetaWinningStrategyImpl;
import hotciv.standard.CityWorkForceFocusStrategies.AlphaCityWorkForceFocusImpl;
import hotciv.standard.DieStrategyImpl;
import hotciv.standard.IncrementCitySizeStrategies.AlphaIncrementCitySizeImpl;
import hotciv.standard.UnitActionsStrategies.AlphaUnitActionsImpl;
import hotciv.standard.WorldLayoutStrategies.AlphaWorldLayoutImpl;

public class BetaHotcivFactory implements HotcivFactory {

  @Override
  public AttackingStrategy createAttackingStrategy() {
    return new AlphaAttackingStrategyImpl();
  }

  @Override
  public CityWorkForceFocus createCityWorkForceFocusStrategy() {
    return new AlphaCityWorkForceFocusImpl();
  }

  @Override
  public IncrementAgeStrategy createIncrementAgeStrategy() {
    return new BetaIncrementAgeImpl();
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
    return new BetaWinningStrategyImpl();
  }

  @Override
  public WorldLayoutStrategy createWorldLayoutStrategy() {
    return new AlphaWorldLayoutImpl();
  }

  @Override
  public DieStrategy createDieStrategy() {
    return new DieStrategyImpl();
  }
}
