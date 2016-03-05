/*
 *  This code is released under Creative Commons Attribution 4.0 International
 *  (CC BY 4.0) license, http://creativecommons.org/licenses/by/4.0/legalcode .
 *  That means:
 * 
 *  You are free to:
 * 
 *      Share — copy and redistribute the material in any medium or format
 *      Adapt — remix, transform, and build upon the material
 *               for any purpose, even commercially.
 * 
 *      The licensor cannot revoke these freedoms as long as you follow the
 *      license terms.
 * 
 *  Under the following terms:
 * 
 *      Attribution — You must give appropriate credit, provide a link to the
 *      license, and indicate if changes were made. You may do so in any
 *      reasonable manner, but not in any way that suggests the licensor endorses
 *      you or your use.
 * 
 *  No additional restrictions — You may not apply legal terms or technological
 *  measures that legally restrict others from doing anything the license
 *  permits.
 * 
 *
 *  2015 Aeonium Software Systems, Robert Rohm.
 */
package org.aeonium.javafx;

import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Window;

/**
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class AssertFX {

  public static void assertNotShowing(Window window) {
    if (window == null) {
      throw new AssertionError("Window is null");
    }
    if (window.isShowing() == true) {
      throw new AssertionError("Window " + window + " is showing, but should not.");
    }
  }

  public static void assertNotVisible(Node node) {
    if (node == null) {
      throw new AssertionError("Node is null");
    }
    if (node.isVisible() == true) {
      throw new AssertionError("Node " + node + " is visible, but should not.");
    }
  }

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

  public static void assertShowing(Window window) {
    if (window == null) {
      throw new AssertionError("Window is null");
    }
    if (window.isShowing() == false) {
      throw new AssertionError("Window " + window + " should be showing, but is not.");
    }
  }

  public static void assertVisible(Node node) {
    if (node == null) {
      throw new AssertionError("Node is null");
    }
    if (node.isVisible() != true) {
      throw new AssertionError("Node " + node + " should be visible, but is not.");
    }
  }

}
