package hotciv.standard.WorldLayoutStrategies;

import hotciv.framework.*;
import hotciv.framework.StrategyInterfaces.WorldLayoutStrategy;
import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;
import hotciv.standard.TileImpl;
import hotciv.standard.UnitImpl;

import java.util.HashMap;

public class DeltaWorldLayoutImpl implements WorldLayoutStrategy {

  @Override
  public void putTiles(GameImpl game) {
    HashMap<Position, TileImpl> worldTiles = new HashMap<>();
      String[] layout =
              new String[] {
                      "...ooMooooo.....",
                      "..ohhoooofffoo..",
                      ".oooooMooo...oo.",
                      ".ooMMMoooo..oooo",
                      "...ofooohhoooo..",
                      ".ofoofooooohhoo.",
                      "...ooo..........",
                      ".ooooo.ooohooM..",
                      ".ooooo.oohooof..",
                      "offfoooo.offoooo",
                      "oooooooo...ooooo",
                      ".ooMMMoooo......",
                      "..ooooooffoooo..",
                      "....ooooooooo...",
                      "..ooohhoo.......",
                      ".....ooooooooo..",
              };
      // Conversion...
    String line;
      for ( int r = 0; r < GameConstants.WORLDSIZE; r++ ) {
        line = layout[r];
        for ( int c = 0; c < GameConstants.WORLDSIZE; c++ ) {
          char tileChar = line.charAt(c);
          String type = "error";
          if ( tileChar == '.' ) { type = GameConstants.OCEANS; }
          if ( tileChar == 'o' ) { type = GameConstants.PLAINS; }
          if ( tileChar == 'M' ) { type = GameConstants.MOUNTAINS; }
          if ( tileChar == 'f' ) { type = GameConstants.FOREST; }
          if ( tileChar == 'h' ) { type = GameConstants.HILLS; }
          Position p = new Position(r,c);
          worldTiles.put( p, new TileImpl(type));
        }
      }
      game.setTiles(worldTiles);
  }

  @Override
  public void putCities(GameImpl game) {
    HashMap<Position, CityImpl> worldCities = new HashMap<>();
    worldCities.put(new Position(8, 12), new CityImpl(Player.RED, GameConstants.productionFocus, GameConstants.ARCHER));
    worldCities.put(new Position(4, 5), new CityImpl(Player.BLUE, GameConstants.foodFocus, GameConstants.LEGION));

    game.setCities(worldCities);
  }

  @Override
  public void putUnits(GameImpl game) {
    HashMap<Position, UnitImpl> worldUnits = new HashMap<>();
    worldUnits.put(new Position(3, 8), new UnitImpl(GameConstants.ARCHER, Player.RED));
    worldUnits.put(new Position(4, 4), new UnitImpl(GameConstants.LEGION, Player.BLUE));
    worldUnits.put(new Position(2, 2), new UnitImpl(GameConstants.SETTLER, Player.RED));

    game.setUnits(worldUnits);
  }
}
