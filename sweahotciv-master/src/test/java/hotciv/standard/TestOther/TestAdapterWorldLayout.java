package hotciv.standard.TestOther;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.framework.Tile;
import hotciv.standard.GameImpl;
import hotciv.standard.HotCivFactories.AdapterWorldLayoutHotCivFactory;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class TestAdapterWorldLayout {
  @Test
  public void dontMake25similarLayouts() {
    ArrayList<String> tilesAt0_0 = new ArrayList<>();
    Game game = new GameImpl(new AdapterWorldLayoutHotCivFactory());

    for (int i = 0; i < 25; i++) {
      Tile tile = game.getTileAt(new Position(0, 0));
      tilesAt0_0.add(tile.getTypeString());
      game = new GameImpl(new AdapterWorldLayoutHotCivFactory());
    }

    // asserts that all items are the same.
    assertFalse(tilesAt0_0.stream().allMatch(tilesAt0_0.get(0)::equals));
  }
}
