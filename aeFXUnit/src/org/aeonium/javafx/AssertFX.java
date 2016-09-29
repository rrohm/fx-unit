/*
 * Copyright (C) 2016 Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
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
package org.aeonium.javafx;

import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Window;

/**
 * Assertions for JavaFX UI testing. All assertion methods throw a
 * AssertionError in case of validation errors.
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class AssertFX {

  /**
   * Assert that the give node is focused, i.e., it's focused property is not
   * true.
   *
   * @param node The node to be checked
   */
  public static void assertFocused(Node node) {
    if (node == null) {
      throw new AssertionError("Node is null");
    }
    if (node.isFocused() != true) {
      throw new AssertionError("Node " + node + " should be focused, but is not.");
    }
  }

  /**
   * Assert that the given window is not showing, i.e., it's showing property is
   * not true.
   *
   * @param window The window to be checked.
   */
  public static void assertNotShowing(Window window) {
    if (window == null) {
      throw new AssertionError("Window is null");
    }
    if (window.isShowing() == true) {
      throw new AssertionError("Window " + window + " is showing, but should not.");
    }
  }

  /**
   * Assert that the give node is focused, i.e., it's focused property is not
   * true.
   *
   * @param node The node to be checked
   */
  public static void assertNotVisible(Node node) {
    if (node == null) {
      throw new AssertionError("Node is null");
    }
    if (node.isVisible() == true) {
      throw new AssertionError("Node " + node + " is visible, but should not.");
    }
  }

  /**
   * Assert that in the given tab pane there is a selected tab with the given
   * ID.
   *
   * @param tabPane The tab pane
   * @param id The ID that the selected tab must have.
   */
  public static void assertSelected(TabPane tabPane, String id) {
    if (tabPane == null) {
      throw new AssertionError("tabPane is null");
    }
    if (id == null) {
      throw new AssertionError("id is null");
    }
    Tab selectedItem = tabPane.getSelectionModel().getSelectedItem();
    if (selectedItem == null) {
      throw new AssertionError("tabPane.getSelectionModel().getSelectedItem() is null");
    }
    if (!selectedItem.getId().equals(id)) {
      throw new AssertionError("tabPane.getSelectionModel().getSelectedItem().getId() is \"" + selectedItem.getId() + "\", should be \"" + id + "\".");
    }
  }

  /**
   * Assert that the given window is showing, i.e., it's showing property is
   * true.
   *
   * @param window The window to be checked.
   */
  public static void assertShowing(Window window) {
    if (window == null) {
      throw new AssertionError("Window is null");
    }
    if (window.isShowing() == false) {
      throw new AssertionError("Window " + window + " should be showing, but is not.");
    }
  }

  /**
   * Assert that the give node is visible, i.e., it's visibel property is true.
   *
   * @param node The node to be checked
   */
  public static void assertVisible(Node node) {
    if (node == null) {
      throw new AssertionError("Node is null");
    }
    if (node.isVisible() != true) {
      throw new AssertionError("Node " + node + " should be visible, but is not.");
    }
  }
}
