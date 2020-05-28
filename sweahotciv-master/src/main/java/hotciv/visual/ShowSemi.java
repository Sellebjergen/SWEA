package hotciv.visual;

import hotciv.framework.Game;
import hotciv.standard.GameImpl;
import hotciv.standard.HotCivFactories.SemiHotCivFactory;
import hotciv.stub.StubGame2;
import hotciv.visual.tools.CompositionTool;
import hotciv.visual.tools.MoveTool;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;
import minidraw.standard.SelectionTool;

public class ShowSemi {
  public static void main(String[] args) {
    Game game = new GameImpl(new SemiHotCivFactory());

    DrawingEditor editor =
            new MiniDrawApplication( "Move any unit using the mouse",
                    new HotCivFactory4(game));

    editor.open();
    editor.showStatus("Playable SemiCiv.");
    editor.setTool( new CompositionTool(game, new SelectionTool(editor)) );
  }
}
