package hotciv.broker.main.Broker;

import hotciv.framework.City;
import hotciv.framework.NameService;
import hotciv.framework.Unit;
import hotciv.framework.Tile;

import java.util.HashMap;

public class NameServiceImpl implements NameService {
  private HashMap<String, City> cities = new HashMap<>();
  private HashMap<String, Unit> units = new HashMap<>();
  private HashMap<String, Tile> tiles = new HashMap<>();

  @Override
  public void putCity(String objectID, City city) {
    cities.put(objectID, city);
  }

  @Override
  public City getCity(String objectID) {
    return cities.get(objectID);
  }

  @Override
  public void putUnit(String objectID, Unit unit) {
    units.put(objectID, unit);
  }

  @Override
  public Unit getUnit(String objectID) {
    return units.get(objectID);
  }

  @Override
  public void putTile(String objectID, Tile tile) {
    tiles.put(objectID, tile);
  }

  @Override
  public Tile getTile(String objectID) {
    return tiles.get(objectID);
  }
}