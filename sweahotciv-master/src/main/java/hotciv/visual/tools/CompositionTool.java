package hotciv.visual.tools;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.view.GfxConstants;
import minidraw.framework.Tool;
import minidraw.standard.NullTool;
import minidraw.standard.SelectionTool;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.security.PolicySpi;

public class CompositionTool extends NullTool {
  private Game game;
  private SelectionTool tool;
  private Tool alternatingTool;

  public CompositionTool(Game game, SelectionTool tool) {
    this.game = game;
    this.tool = tool;
  }

  @Override
  public void mouseDown(MouseEvent e, int x, int y) {
    alternatingTool = new SetFocusTool(game, tool);
    Position pos = GfxConstants.getPositionFromXY(x, y);

    boolean unitIsPresent = game.getUnitAt(pos) != null;
    if (unitIsPresent) { alternatingTool = new MoveTool(game, tool); }
    if (unitIsPresent && e.isShiftDown()) { alternatingTool = new ActionTool(game, tool); }

    boolean insideOfTurn = x < 590 && x > 555 && y < 105 && y > 60;
    if (insideOfTurn) { alternatingTool = new EndOfTurnTool(game, tool); }

    boolean insideOfRefresh = x < GfxConstants.REFRESH_BUTTON_X + 50 && x > GfxConstants.REFRESH_BUTTON_X &&
                              y < GfxConstants.REFRESH_BUTTON_Y + 50 && y > GfxConstants.REFRESH_BUTTON_Y;
    if (insideOfRefresh) { alternatingTool = new RefreshTool(game, tool); }

    alternatingTool.mouseDown(e, x, y);
  }

  @Override
  public void mouseDrag(MouseEvent e, int x, int y) {
    alternatingTool.mouseDrag(e, x, y);
  }

  @Override
  public void mouseUp(MouseEvent e, int x, int y) {
    alternatingTool.mouseUp(e, x, y);
  }
}
