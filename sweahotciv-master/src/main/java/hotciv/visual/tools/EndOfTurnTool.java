package hotciv.visual.tools;

import hotciv.framework.Game;
import hotciv.view.GfxConstants;
import minidraw.standard.NullTool;
import minidraw.standard.SelectionTool;

import java.awt.event.MouseEvent;

public class EndOfTurnTool extends NullTool {

  private Game game;
  private SelectionTool tool;

  public EndOfTurnTool(Game game, SelectionTool tool) {
    this.game = game;
    this.tool = tool;
  }

  @Override
  public void mouseDown(MouseEvent e, int x, int y) {
    game.endOfTurn();
    tool.mouseDown(e, x, y);
  }
}
