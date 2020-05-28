package hotciv.standard.HotCivFactories;

import hotciv.framework.HotcivFactory;
import hotciv.framework.StrategyInterfaces.*;
import hotciv.standard.AttackingStrategies.EpsilonAttackingStrategyImpl;
import hotciv.standard.CityWorkForceFocusStrategies.EtaCityWorkForceFocusImpl;
import hotciv.standard.DieStrategyImpl;
import hotciv.standard.IncrementAgeStrategies.BetaIncrementAgeImpl;
import hotciv.standard.IncrementCitySizeStrategies.EtaIncrementCitySizeImpl;
import hotciv.standard.UnitActionsStrategies.GammaUnitActionsImpl;
import hotciv.standard.UnitActionsStrategies.SemiUnitActionsImpl;
import hotciv.standard.WinningStrategies.EpsilonWinningStrategyImpl;
import hotciv.standard.WorldLayoutStrategies.DeltaWorldLayoutImpl;

public class SemiHotCivFactory implements HotcivFactory {
  @Override
  public AttackingStrategy createAttackingStrategy() {
    return new EpsilonAttackingStrategyImpl(createDieStrategy());
  }

  @Override
  public CityWorkForceFocus createCityWorkForceFocusStrategy() {
    return new EtaCityWorkForceFocusImpl();
  }

  @Override
  public IncrementAgeStrategy createIncrementAgeStrategy() {
    return new BetaIncrementAgeImpl();
  }

  @Override
  public IncrementCitySizeStrategy createIncrementCitySizeStrategy() {
    return new EtaIncrementCitySizeImpl();
  }

  @Override
  public UnitActionsStrategy createUnitActionsStrategy() {
    return new SemiUnitActionsImpl();
  }

  @Override
  public WinningStrategy createWinningStrategy() {
    return new EpsilonWinningStrategyImpl();
  }

  @Override
  public WorldLayoutStrategy createWorldLayoutStrategy() {
    return new DeltaWorldLayoutImpl();
  }

  @Override
  public DieStrategy createDieStrategy() {
    return new DieStrategyImpl();
  }
}
