/*
 * Copyright (C) 2020 Aeonium Software Systems.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package org.aeonium.fxunit;

import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;

/**
 * Utility for testing Menues with a fluent API.
 *
 * @author robert
 */
public class FXMenu {

//  private final Menu menu;
  private final ObservableList<MenuItem> items;
//
//  public FXMenu(Menu menu) {
//    this.menu = menu;
//  }

  public FXMenu(ObservableList<MenuItem> items) {
    this.items = items;
  }

  /**
   * Assert that there is a menu item with the given ID, and return it wrapped
   * in an FXMenuItem.
   *
   * @param id The menu item's ID, prefixed with '#'.
   * @return An FXMenuItem test wrapper. 
   */
  public FXMenuItem getMenuItem(String id) {
    if (this.items == null) {
      throw new AssertionError("items is null, but should not.");
    }

    if (!id.startsWith("#")) {
      id = "#".concat(id);
    }

    id = id.substring(1);
    for (MenuItem item : this.items) {
      if (item.getId() != null && item.getId().equals(id)) {
        return new FXMenuItem(item);
      }
    }
    throw new AssertionError("There is no MenuItem with id #" + id);
  }

  public FXMenu hasMenuItem(String id) {
    if (this.items == null) {
      throw new AssertionError("items is null, but should not.");
    }
    
    if (!id.startsWith("#")) {
      id = "#".concat(id);
    }

    boolean found = false;
    id = id.substring(1);
    for (MenuItem item : this.items) {
      if (item.getId() != null && item.getId().equals(id)) {
        found = true;
        break;
      }
    }
    if (found) {
      return this;
    }
    throw new AssertionError("There is no MenuItem with id #" + id);
  }

//  public FXMenuItem getMenuItem(String id){
//    if (!id.startsWith("#")) {
//      id = "#".concat(id);
//    }
//    if (this.menu.getParentPopup() instanceof ContextMenu) {
//      ContextMenu contextMenu = (ContextMenu) this.menu.getParentPopup();
//      
//      boolean found = false;
//      id = id.substring(1);
//      for (MenuItem item : contextMenu.getItems()) {
//        if (item.getId() != null && item.getId().equals(id)) {
//          return new FXMenuItem(item);
//        }
//      }
//      throw new AssertionError("There is no MenuItem with id #" + id);
// 
//    } else {
//      throw new AssertionError("Cannot have MenuItems. Window is of class " + this.menu.getParentPopup().getClass());
//    }
//  }
}
