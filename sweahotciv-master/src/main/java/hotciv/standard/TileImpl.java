package hotciv.standard;

import hotciv.framework.GameConstants;

import java.util.UUID;

public class TileImpl implements hotciv.framework.Tile {
  private String type;
  private String id;

  public TileImpl(String type) {
    this.type = type;
    this.id = UUID.randomUUID().toString();
  }

  /**
   * return the tile type as a string. The set of
   * valid strings are defined by the graphics
   * engine, as they correspond to named image files.
   *
   * @return the type type as string
   */
  @Override
  public String getTypeString() {
    return type;
  }

  @Override
  public String getId() {
    return id;
  }
}
