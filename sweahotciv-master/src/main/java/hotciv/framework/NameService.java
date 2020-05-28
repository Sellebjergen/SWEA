package hotciv.framework;

import hotciv.framework.City;
import hotciv.framework.Unit;
import hotciv.framework.Tile;

public interface NameService {
  void putCity(String objectID, City city);
  City getCity(String objectID);

  void putUnit(String objectID, Unit unit);
  Unit getUnit(String objectID);

  void putTile(String objectID, Tile tile);
  Tile getTile(String objectID);
}