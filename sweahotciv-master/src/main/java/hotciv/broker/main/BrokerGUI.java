package hotciv.broker.main;

import frds.broker.ClientRequestHandler;
import frds.broker.Requestor;
import frds.broker.ipc.socket.SocketClientRequestHandler;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.broker.main.Broker.BrokerConstants;
import hotciv.broker.main.Broker.Proxies.GameProxy;
import hotciv.framework.Game;
import hotciv.visual.HotCivFactory4;
import hotciv.visual.tools.CompositionTool;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;
import minidraw.standard.SelectionTool;

public class BrokerGUI {

  public static void main(String[] args) {
    new BrokerGUI();
  }

  private BrokerGUI() {
    ClientRequestHandler clientRequestHandler = new SocketClientRequestHandler("localhost", BrokerConstants.serverPort);
    Requestor requestor = new StandardJSONRequestor(clientRequestHandler);
    Game game = new GameProxy("Stub", requestor);

    DrawingEditor editor =
            new MiniDrawApplication( "Try to play SemiCiv",
                    new HotCivFactory4(game));

    editor.open();
    editor.showStatus("Playable SemiCiv.");
    editor.setTool( new CompositionTool(game, new SelectionTool(editor)) );
  }
}