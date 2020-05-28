package hotciv.standard.HotCivFactories;

import hotciv.framework.HotcivFactory;
import hotciv.framework.StrategyInterfaces.*;
import hotciv.standard.AttackingStrategies.AlphaAttackingStrategyImpl;
import hotciv.standard.CityWorkForceFocusStrategies.AlphaCityWorkForceFocusImpl;
import hotciv.standard.DieStrategyImpl;
import hotciv.standard.UnitActionsStrategies.GammaUnitActionsImpl;
import hotciv.standard.IncrementAgeStrategies.AlphaIncrementAgeImpl;
import hotciv.standard.IncrementCitySizeStrategies.AlphaIncrementCitySizeImpl;
import hotciv.standard.UnitActionsStrategies.ThetaUnitActionsImpl;
import hotciv.standard.WinningStrategies.AlphaWinningStrategyImpl;
import hotciv.standard.WorldLayoutStrategies.AlphaWorldLayoutImpl;

public class ThetaHotCivFactory implements HotcivFactory {

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
    return new AlphaIncrementAgeImpl();
  }

  @Override
  public IncrementCitySizeStrategy createIncrementCitySizeStrategy() {
    return new AlphaIncrementCitySizeImpl();
  }

  @Override
  public UnitActionsStrategy createUnitActionsStrategy() {
    return new ThetaUnitActionsImpl();
  }

  @Override
  public WinningStrategy createWinningStrategy() {
    return new AlphaWinningStrategyImpl();
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
