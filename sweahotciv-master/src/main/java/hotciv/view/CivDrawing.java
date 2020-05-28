package hotciv.view;

import hotciv.framework.*;

import java.awt.*;
import java.util.*;
import java.util.List;

import minidraw.framework.*;
import minidraw.standard.*;

/** CivDrawing is a specialized Drawing (model component) from
 * MiniDraw that dynamically builds the list of Figures for MiniDraw
 * to render the Unit and other information objects that are visible
 * in the Game instance.
 *
 * This is a TEMPLATE for the SWEA Exercise! This means
 * that it is INCOMPLETE and that there are several options
 * for CLEANING UP THE CODE when you add features to it!

   This source code is from the book 
     "Flexible, Reliable Software:
       Using Patterns and Agile Development"
     published 2010 by CRC Press.
   Author: 
     Henrik B Christensen 
     Department of Computer Science
     Aarhus University
   
   Please visit http://www.baerbak.com/ for further information.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
 
       http://www.apache.org/licenses/LICENSE-2.0
 
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

public class CivDrawing 
  implements Drawing, GameObserver {
  
  protected Drawing delegate;
  /** store all moveable figures visible in this drawing = units */
  protected Map<Unit, UnitFigure> unitFigureMap;
  protected Map<City, CityFigure> cityFigureMap;

  /** the Game instance that this CivDrawing is going to render units
   * from */
  protected Game game;
  
  public CivDrawing( DrawingEditor editor, Game game ) {
    super();
    this.delegate = new StandardDrawing();
    this.game = game;
    this.unitFigureMap = new HashMap<>();
    this.cityFigureMap = new HashMap<>();

    // register this unit drawing as listener to any game state
    // changes...
    game.addObserver(this);
    // ... and build up the set of figures associated with
    // units in the game.
    defineCityMap();
    defineUnitMap();
    defineIcons();
  }
  
  /** The CivDrawing should not allow client side
   * units to add and manipulate figures; only figures
   * that renders game objects are relevant, and these
   * should be handled by observer events from the game
   * instance. Thus this method is 'killed'.
   */
  public Figure add(Figure arg0) {
    throw new RuntimeException("Should not be used...");
  }


  /** erase the old list of units, and build a completely new
   * one from scratch by iterating over the game world and add
   * Figure instances for each unit in the world.
   */
  protected void defineCityMap() {
    clearSelection();
    removeAllCityFigures();
    Position p;
    for ( int r = 0; r < GameConstants.WORLDSIZE; r++ ) {
      for ( int c = 0; c < GameConstants.WORLDSIZE; c++ ) {
        p = new Position(r,c);
        City city = game.getCityAt(p);
        if ( city != null ) {
          // convert the city's Position to (x,y) coordinates
          Point point = new Point( GfxConstants.getXFromColumn(p.getColumn()),
                                   GfxConstants.getYFromRow(p.getRow()) );
          CityFigure cityFigure = new CityFigure( city, point );
          cityFigure.addFigureChangeListener(this);
          cityFigureMap.put(city, cityFigure);

          // also insert in delegate list as it is
          // this list that is iterated by the
          // graphics rendering algorithms
          delegate.add(cityFigure);
        }
      }
    }
  }

  protected void defineUnitMap() {
    // ensure no units of the old list are accidental in
    // the selection!
    clearSelection();

    // remove all unit figures in this drawing
    removeAllUnitFigures();

    // iterate world, and create a unit figure for
    // each unit in the game world, as well as
    // create an association between the unit and
    // the unitFigure in 'unitFigureMap'.
    Position p;
    for ( int r = 0; r < GameConstants.WORLDSIZE; r++ ) {
      for ( int c = 0; c < GameConstants.WORLDSIZE; c++ ) {
        p = new Position(r,c);
        Unit unit = game.getUnitAt(p);
        if ( unit != null ) {
          String type = unit.getTypeString();
          // convert the unit's Position to (x,y) coordinates
          Point point = new Point( GfxConstants.getXFromColumn(p.getColumn()),
                                   GfxConstants.getYFromRow(p.getRow()) );
          UnitFigure unitFigure = new UnitFigure( type, point, unit );
          unitFigure.addFigureChangeListener(this);
          unitFigureMap.put(unit, unitFigure);

          // also insert in delegate list as it is
          // this list that is iterated by the
          // graphics rendering algorithms
          delegate.add(unitFigure);
        }
      }
    }
  }

  /** remove all unit figures in this
   * drawing, and reset the map (unit->unitfigure).
   * It is important to actually remove the figures
   * as it forces a graphical redraw of the screen
   * where the unit figure was.
   */
  protected void removeAllUnitFigures() {
    for (Unit u : unitFigureMap.keySet()) {
      UnitFigure uf = unitFigureMap.get(u);
      delegate.remove(uf);
    }
    unitFigureMap.clear();
  }

  protected void removeAllCityFigures() {
    for (City c : cityFigureMap.keySet()) {
      CityFigure cf = cityFigureMap.get(c);
      delegate.remove(cf);
    }
    cityFigureMap.clear();
  }

  protected ImageFigure turnShieldIcon;
  protected ImageFigure unitShieldIcon;
  protected ImageFigure cityShieldIcon;
  protected ImageFigure refreshIcon;
  protected TextFigure ageText;
  protected ImageFigure workFocus;
  protected ImageFigure cityProd;
  protected TextFigure unitMoveCount;

  protected void defineIcons() {
    cityShieldIcon = new ImageFigure( GfxConstants.NOTHING,
                                      new Point( GfxConstants.CITY_SHIELD_X,
                                                 GfxConstants.CITY_SHIELD_Y ) );

    unitShieldIcon = new ImageFigure( GfxConstants.NOTHING,
                                      new Point( GfxConstants.UNIT_SHIELD_X,
                                                 GfxConstants.UNIT_SHIELD_Y ) );

    Player inTurn = game.getPlayerInTurn();
    if(inTurn == Player.RED) {
      turnShieldIcon = new ImageFigure( GfxConstants.RED_SHIELD,
                                        new Point(GfxConstants.TURN_SHIELD_X,
                                                  GfxConstants.TURN_SHIELD_Y));
    }
    else
      turnShieldIcon = new ImageFigure( GfxConstants.BLUE_SHIELD,
                                        new Point(GfxConstants.TURN_SHIELD_X,
                                                  GfxConstants.TURN_SHIELD_Y));

    refreshIcon = new ImageFigure( GfxConstants.REFRESH_BUTTON,
                                   new Point(GfxConstants.REFRESH_BUTTON_X,
                                             GfxConstants.REFRESH_BUTTON_Y));
    workFocus = new ImageFigure( "black",
                                   new Point(GfxConstants.WORKFORCEFOCUS_X,
                                             GfxConstants.WORKFORCEFOCUS_Y));

    cityProd = new ImageFigure( "black",
                                   new Point(GfxConstants.CITY_PRODUCTION_X,
                                             GfxConstants.CITY_PRODUCTION_Y));

    unitMoveCount = new TextFigure("",
                                   new Point(GfxConstants.UNIT_COUNT_X,
                                             GfxConstants.UNIT_COUNT_Y));
    delegate.remove(ageText);
    int age = game.getAge();
    if(age < 0) {
      ageText = new TextFigure(Math.abs(age) + " BC.",
                new Point(GfxConstants.AGE_TEXT_X,
                          GfxConstants.AGE_TEXT_Y));
    }
    else {
      ageText = new TextFigure(age + " AD.",
                new Point(GfxConstants.AGE_TEXT_X,
                          GfxConstants.AGE_TEXT_Y));
    }

    // insert in delegate figure list to ensure graphical
    // rendering.
    delegate.add(turnShieldIcon);
    delegate.add(unitShieldIcon);
    delegate.add(cityShieldIcon);
    delegate.add(refreshIcon);
    delegate.add(ageText);
    delegate.add(workFocus);
    delegate.add(cityProd);
    delegate.add(unitMoveCount);
  }
 
  // === Observer Methods ===

  public void worldChangedAt(Position pos) {
    // this is a really brute-force algorithm: destroy
    // all known units and build up the entire set again
    defineCityMap();
    defineUnitMap();
    defineIcons();
  }

  public void turnEnds(Player nextPlayer, int age) {
    String playername = "blue";
    if ( nextPlayer == Player.BLUE ) { playername = "red"; }
    turnShieldIcon.set( playername + "shield",
                        new Point( GfxConstants.TURN_SHIELD_X,
                                   GfxConstants.TURN_SHIELD_Y ) );
    // sets the age of the game into the age text.
    if (game.getAge() >= 0) {
      ageText.setText(game.getAge() + " AD.");
    } else {
      ageText.setText(Math.abs(game.getAge()) + " BC.");
    }
    defineCityMap();
    defineUnitMap();
    defineIcons();
  }

  public void tileFocusChangedAt(Position position) {
    City city = game.getCityAt(position);
    Unit unit = game.getUnitAt(position);
    String playerShield = GfxConstants.RED_SHIELD;
    clearSelection();

    if (city != null) {
      if (city.getOwner() == Player.BLUE) playerShield = GfxConstants.BLUE_SHIELD;
      cityShieldIcon.set(playerShield,
                         new Point(GfxConstants.CITY_SHIELD_X,
                                   GfxConstants.CITY_SHIELD_Y));
      workFocus.set(city.getWorkforceFocus(),
                    new Point(GfxConstants.WORKFORCEFOCUS_X,
                              GfxConstants.WORKFORCEFOCUS_Y));
      cityProd.set(city.getProduction(),
                   new Point(GfxConstants.CITY_PRODUCTION_X,
                             GfxConstants.CITY_PRODUCTION_Y));
    }
    else {
      cityShieldIcon.set(GfxConstants.NOTHING,
              new Point(GfxConstants.CITY_SHIELD_X,
                      GfxConstants.CITY_SHIELD_Y));
      workFocus.set(GfxConstants.NOTHING,
              new Point(GfxConstants.WORKFORCEFOCUS_X,
                      GfxConstants.WORKFORCEFOCUS_Y));
      cityProd.set(GfxConstants.NOTHING,
              new Point(GfxConstants.CITY_PRODUCTION_X,
                      GfxConstants.CITY_PRODUCTION_Y));
    }

    if (unit != null) {
      if (unit.getOwner() == Player.BLUE) playerShield = GfxConstants.BLUE_SHIELD;
      unitShieldIcon.set(playerShield,
                         new Point(GfxConstants.UNIT_SHIELD_X,
                                   GfxConstants.UNIT_SHIELD_Y));
      unitMoveCount.setText("" + unit.getMoveCount());
    }
    else {
      unitShieldIcon.set(GfxConstants.NOTHING,
              new Point(GfxConstants.UNIT_SHIELD_X,
                      GfxConstants.UNIT_SHIELD_Y));
      unitMoveCount.setText("");
    }

    delegate.add(cityShieldIcon);
    delegate.add(workFocus);
    delegate.add(cityProd);
    delegate.add(unitShieldIcon);
    delegate.add(unitMoveCount);
  }

  @Override
  public void requestUpdate() {
    defineCityMap();
    defineUnitMap();
    defineIcons();
  }

  @Override
  public void addToSelection(Figure arg0) {
    delegate.addToSelection(arg0);
  }

  @Override
  public void clearSelection() {
    delegate.clearSelection();
  }

  @Override
  public void removeFromSelection(Figure arg0) {
    delegate.removeFromSelection(arg0);
  }

  @Override
  public List<Figure> selection() {
    return delegate.selection();
  }

  @Override
  public void toggleSelection(Figure arg0) {
    delegate.toggleSelection(arg0);
  }

  @Override
  public void figureChanged(FigureChangeEvent arg0) {
    delegate.figureChanged(arg0);
  }

  @Override
  public void figureInvalidated(FigureChangeEvent arg0) {
    delegate.figureInvalidated(arg0);
  }

  @Override
  public void figureRemoved(FigureChangeEvent arg0) {
    delegate.figureRemoved(arg0);
  }

  @Override
  public void figureRequestRemove(FigureChangeEvent arg0) {
    delegate.figureRequestRemove(arg0);
  }

  @Override
  public void figureRequestUpdate(FigureChangeEvent arg0) {
    delegate.figureRequestUpdate(arg0);
  }

  @Override
  public void addDrawingChangeListener(DrawingChangeListener arg0) {
    delegate.addDrawingChangeListener(arg0);   
  }

  @Override
  public void removeDrawingChangeListener(DrawingChangeListener arg0) {
    delegate.removeDrawingChangeListener(arg0);
  }

  @Override
  public Figure findFigure(int arg0, int arg1) {
    return delegate.findFigure(arg0, arg1);
  }

  @Override
  public Iterator<Figure> iterator() {
    return delegate.iterator();
  }

  @Override
  public void lock() {
    delegate.lock();
  }

  @Override
  public Figure remove(Figure arg0) {
    return delegate.remove(arg0);
  }

  @Override
  public void unlock() {
    delegate.unlock();
  }
}