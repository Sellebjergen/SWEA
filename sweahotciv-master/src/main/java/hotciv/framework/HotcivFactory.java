package hotciv.framework;

import hotciv.framework.StrategyInterfaces.*;

public interface HotcivFactory {
  AttackingStrategy createAttackingStrategy();
  CityWorkForceFocus createCityWorkForceFocusStrategy();
  IncrementAgeStrategy createIncrementAgeStrategy();
  IncrementCitySizeStrategy createIncrementCitySizeStrategy();
  UnitActionsStrategy createUnitActionsStrategy();
  WinningStrategy createWinningStrategy();
  WorldLayoutStrategy createWorldLayoutStrategy();
  DieStrategy createDieStrategy();
}
