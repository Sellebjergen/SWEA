package hotciv.visual.tools;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.framework.Unit;
import hotciv.view.GfxConstants;
import minidraw.framework.DrawingEditor;
import minidraw.standard.NullTool;
import minidraw.standard.SelectionTool;

import java.awt.event.MouseEvent;

public class ActionTool extends NullTool {
  private SelectionTool tool;
  private Game game;

  public ActionTool(Game game, SelectionTool tool) {
    this.game = game;
    this.tool = tool;
  }

  @Override
  public void mouseDown(MouseEvent e, int x, int y) {
    Position pos = GfxConstants.getPositionFromXY(x, y);

    boolean itsYourUnit = game.getUnitAt(pos).getOwner().equals(game.getPlayerInTurn());

    if (e.isShiftDown() && itsYourUnit) {
      game.performUnitActionAt(pos);
      tool.mouseDown(e, x, y);
    }
  }
}
