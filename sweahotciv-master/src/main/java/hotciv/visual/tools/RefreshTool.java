package hotciv.visual.tools;

import hotciv.framework.Game;
import minidraw.standard.NullTool;
import minidraw.standard.SelectionTool;

import java.awt.event.MouseEvent;

public class RefreshTool extends NullTool {

  private Game game;
  private SelectionTool tool;

  public RefreshTool(Game game, SelectionTool tool) {
    this.game = game;
    this.tool = tool;
  }

  @Override
  public void mouseUp(MouseEvent e, int x, int y) {
    tool.editor().drawing().requestUpdate();
    tool.mouseUp(e, x, y);
  }
}