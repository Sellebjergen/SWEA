package hotciv.standard.AttackingStrategies;

import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.StrategyInterfaces.AttackingStrategy;
import hotciv.framework.StrategyInterfaces.DieStrategy;
import hotciv.standard.GameImpl;
import hotciv.standard.UnitImpl;

public class EpsilonAttackingStrategyImpl implements AttackingStrategy {
  private DieStrategy ds;

  public EpsilonAttackingStrategyImpl(DieStrategy ds) {
    this.ds = ds;
  }

  @Override
  public boolean handleAttackingProcedure(Position from,
                                          Position to,
                                          GameImpl game) {

    if(game.getUnitAt(to) == null) {
      moveUnit(from, to, game);
      return false;
    }
    return handleBattle(from, to, game);
  }

  private boolean handleBattle(Position from, Position to, GameImpl game) {
    int combinedAttackStr = getCombinedStrength(from, game, game.getUnitAt(from).getAttackingStrength());
    int combinedDefenceStr = getCombinedStrength(to, game, game.getUnitAt(to).getDefensiveStrength());

    if (combinedAttackStr * ds.rollDie() >= combinedDefenceStr * ds.rollDie2()) {
      removeUnit(to, game);
      moveUnit(from, to, game);
      game.incrementCombatWin();
      return true;
    }
    removeUnit(from, game);
    return false;
  }

  private void moveUnit(Position from, Position to, GameImpl game) {
    UnitImpl attacker = game.getUnitAt(from);
    removeUnit(from, game);

    game.getUnits().put(to, attacker);
    game.getUnitAt(to).decreaseMoveCount();
    if (isInCity(to, game)) {
      game.getCityAt(to).changeOwnership(attacker.getOwner());
    }
  }

  private void removeUnit(Position unitPos, GameImpl game) {
    game.getUnits().remove(unitPos);
  }

  private int getCombinedStrength(Position unitPos, GameImpl game, int strength) {
    return (strength + numberOfAdjacentUnits(unitPos, game))
            * terrainFactor(unitPos, game) * cityFactor(unitPos, game);
  }

  private int numberOfAdjacentUnits(Position posOfUnit, GameImpl game) {
    int adjUnits = 0;
    for (Position p : hotciv.utility.Utility.get8neighborhoodOf(posOfUnit)) {
      if(game.getUnitAt(p) != null && game.getUnitAt(p).getOwner().equals(game.getUnitAt(posOfUnit).getOwner())) {
        adjUnits += 1;
      }
    }
    return adjUnits;
  }

  private boolean isInCity(Position p, GameImpl game) {
    return game.getCityAt(p) != null;
  }

  private int cityFactor(Position p, GameImpl game) {
    if (isInCity(p, game)) {
      return 3;
    }
    return 1;
  }

  private int terrainFactor(Position pos, GameImpl game) {
    String type = game.getTileAt(pos).getTypeString();
    switch (type) {
      case GameConstants.FOREST: return 2;
      case GameConstants.HILLS: return 2;
      case GameConstants.MOUNTAINS: return 1;
      case GameConstants.OCEANS: return 1;
      case GameConstants.PLAINS: return 1;
    }
    // in case it's an invalid tile type.
    return 0;
  }
}
