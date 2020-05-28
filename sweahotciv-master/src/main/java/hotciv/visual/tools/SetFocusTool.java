package hotciv.visual.tools;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.view.CivDrawing;
import hotciv.view.GfxConstants;
import minidraw.standard.NullTool;
import minidraw.standard.SelectionTool;

import java.awt.event.MouseEvent;
import java.nio.channels.SelectableChannel;

public class SetFocusTool extends NullTool {
  private Game game;
  private SelectionTool tool;

  public SetFocusTool(Game game, SelectionTool tool) {
    this.game = game;
    this.tool = tool;
  }

  @Override
  public void mouseDown(MouseEvent e, int x, int y) {
    Position pos = GfxConstants.getPositionFromXY(x, y);
    game.setTileFocus(pos);
    tool.mouseDown(e, x, y);
  }

}