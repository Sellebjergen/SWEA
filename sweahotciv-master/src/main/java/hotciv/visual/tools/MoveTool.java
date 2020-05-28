package hotciv.visual.tools;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.Unit;
import hotciv.standard.UnitImpl;
import hotciv.view.GfxConstants;
import minidraw.standard.NullTool;
import minidraw.standard.SelectionTool;

import java.awt.event.MouseEvent;

public class MoveTool extends NullTool {
  private Game game;
  private SelectionTool tool;
  private Position from;
  private Position to;

  public MoveTool(Game game, SelectionTool tool) {
    this.game = game;
    this.tool = tool;
  }

  @Override
  public void mouseDown(MouseEvent e, int x, int y) {
    from = GfxConstants.getPositionFromXY(x, y);
    to = GfxConstants.getPositionFromXY(x, y);
    Unit selectUnit = game.getUnitAt(from);
    if (selectUnit != null &&
            selectUnit.getMoveCount() > 0 &&
            selectUnit.getOwner().equals(game.getPlayerInTurn())) {
      tool.mouseDown(e, x, y);
    }
  }

  @Override
  public void mouseDrag(MouseEvent e, int x, int y) {
    Position pos = GfxConstants.getPositionFromXY(x, y);
    int endOfMap = GameConstants.WORLDSIZE - 1;
    boolean NotOnBoardX = pos.getColumn() > endOfMap || pos.getColumn() < 0;
    boolean NotOnBoardY = pos.getRow() > endOfMap || pos.getRow() < 0;

    if (NotOnBoardX) { pos = new Position(pos.getRow(), endOfMap); }
    if (NotOnBoardY) { pos = new Position(endOfMap, pos.getColumn()); }
    if(game.moveUnit(from, to)) {
      tool.mouseDrag(e, GfxConstants.getXFromColumn(pos.getColumn()),
                        GfxConstants.getYFromRow(pos.getRow()));
    }
  }

  @Override
  public void mouseUp(MouseEvent e, int x, int y) {
    to = GfxConstants.getPositionFromXY(x, y);
    game.moveUnit(from, to);
    game.setTileFocus(to);
    tool.mouseUp(e, x, y);
  }
}
