/*
 * Copyright (C) 2020 Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;.
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
package org.aeonium.fxunit;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.control.Labeled;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleButton;
import javafx.stage.Window;
import org.aeonium.fxunit.i18n.I18N;
import static org.aeonium.fxunit.i18n.I18N.*;

/**
 * Assertions for JavaFX UI testing. All assertion methods throw a
 * AssertionError in case of validation errors.
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class AssertFX {

  protected static final String TOOLTIPGETTEXT_SHOULDBE = "getTooltip().getText() should be ";
  protected static final String TEXT_SHOULDBE = "getText() should be ";
  protected static final String BUT_IS = ", but is ";

  private static final String NODE = "Node ";
  private static final String WINDOW = "Window ";

  /**
   * Private contructor, there is no need to instantiate this class.
   */
  private AssertFX() {
    // no op.
  }

  /**
   * Assert that the given node is disabled, i.e., that it's "disabled" property
   * is true.
   *
   * @param node The node to be checked
   */
  public static void assertDisabled(Node node) {
    if (node == null) {
      throw new AssertionError(I18N.getString(NODE_IS_NULL));
    }
    if (!node.isDisabled()) {
      throw new AssertionError(NODE + node + " should be disabled, but is not.");
    }
  }

  /**
   * Assert that the given node is editable, i.e., it is a descendant of
   * TextInputControl and it's "editable" property is true;
   *
   * @param node The node to be checked, needs to be a descendant of
   * TextInputControl.
   */
  public static void assertEditable(Node node) {
    if (node == null) {
      throw new AssertionError(I18N.getString(NODE_IS_NULL));
    }
    if (node instanceof TextInputControl) {
      TextInputControl textInputControl = (TextInputControl) node;
      if (!textInputControl.isEditable()) {
        throw new AssertionError(NODE + node + " should be editable, but is not.");
      }
    } else {
      throw new AssertionError(NODE + node + " is no descendant of TextInputControl does not have an 'editable' property.");
    }
  }

  /**
   * Assert that the given node is enabled, i.e., that it's "disabled" property
   * is false.
   *
   * @param node The node to be checked
   */
  public static void assertEnabled(Node node) {
    if (node == null) {
      throw new AssertionError(I18N.getString(NODE_IS_NULL));
    }
    if (node.isDisabled()) {
      throw new AssertionError(NODE + node + " should be enabled, but is not.");
    }
  }

  /**
   * Assert that the given node is focused, i.e., it's focused property is not
   * true.
   *
   * @param node The node to be checked
   */
  public static void assertFocused(Node node) {
    if (node == null) {
      throw new AssertionError(I18N.getString(NODE_IS_NULL));
    }
    if (!node.isFocused()) {
      throw new AssertionError(NODE + node + " should be focused, but is not.");
    }
  }

  /**
   * Assert that the given parent node has a given children count.
   *
   * @param parent
   * @param children
   */
  public static void assertHasChildren(Parent parent, int children) {
    if (parent == null) {
      throw new AssertionError(I18N.getString(NODE_IS_NULL));
    }
    if (children < 0) {
      throw new IllegalArgumentException("Children count cannot be less than 0");
    }
    final int size = parent.getChildrenUnmodifiable().size();

    if (size != children) {
      throw new AssertionError(parent + " should have " + children + " children, but actually has " + size);
    }
  }

  /**
   * Assert that the given node is managed, i.e., it's managed property is true
   * and it's layout is managed by it's parent node.
   *
   * @param node The node to be checked
   */
  public static void assertManaged(Node node) {
    if (node == null) {
      throw new AssertionError(I18N.getString(NODE_IS_NULL));
    }
    if (!node.isManaged()) {
      throw new AssertionError(NODE + node + " should be managed, but is not.");
    }
  }

  /**
   * Assert that the given node is not managed, i.e., it's managed property is
   * false and it's layout is not managed by it's parent node.
   *
   * @param node The node to be checked
   */
  public static void assertNotManaged(Node node) {
    if (node == null) {
      throw new AssertionError(I18N.getString(NODE_IS_NULL));
    }
    if (node.isManaged()) {
      throw new AssertionError(NODE + node + " is managed, but should not.");
    }
  }

  /**
   * Assert that the given ToggleButton is not selected.
   *
   * @param toggleButton The toggle button to be checked.
   */
  public static void assertNotSelected(ToggleButton toggleButton) {
    if (toggleButton == null) {
      throw new AssertionError(I18N.getString(TOGGLEBUTTON_IS_NULL));
    }
    if (toggleButton.isSelected()) {
      throw new AssertionError("toggleButton.isSelected() is " + toggleButton.isSelected() + ", should be " + false + ".");
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
      throw new AssertionError(I18N.getString(WINDOW_IS_NULL));
    }
    if (window.isShowing()) {
      throw new AssertionError(WINDOW + window + " is showing, but should not.");
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
      throw new AssertionError(I18N.getString(NODE_IS_NULL));
    }
    if (node.isVisible()) {
      throw new AssertionError(NODE + node + " is visible, but should not.");
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
      throw new AssertionError(I18N.getString(TABPANE_IS_NULL));
    }
    if (id == null) {
      throw new AssertionError(I18N.getString(ID_IS_NULL));
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
   * Assert that the given ToggleButton is selected.
   *
   * @param toggleButton The toggle button to be checked.
   */
  public static void assertSelected(ToggleButton toggleButton) {
    if (toggleButton == null) {
      throw new AssertionError(I18N.getString(TOGGLEBUTTON_IS_NULL));
    }
    if (!toggleButton.isSelected()) {
      throw new AssertionError("toggleButton.isSelected() is " + toggleButton.isSelected() + ", should be " + true + ".");
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
      throw new AssertionError(I18N.getString(WINDOW_IS_NULL));
    }
    if (!window.isShowing()) {
      throw new AssertionError(WINDOW + window + " should be showing, but is not.");
    }
  }

  /**
   * Assert that the given node has a Text property value of the given string.
   *
   * @param node An instance of Labeled.
   * @param text The required text string.
   */
  public static void assertText(Labeled node, String text) {
    if (node == null) {
      throw new AssertionError(I18N.getString(NODE_IS_NULL));
    }
    if (text == null) {
      if (node.getText() != null) {
        throw new AssertionError(TEXT_SHOULDBE + text + BUT_IS + node.getText());
      }

    } else {
      if (!text.equals(node.getText())) {
        throw new AssertionError(TEXT_SHOULDBE + text + BUT_IS + node.getText());
      }
    }
  }

  /**
   * Assert that the given node has a Text property value of the given string.
   *
   * @param node An instance of TextInputControl.
   * @param text The required text string.
   */
  public static void assertText(TextInputControl node, String text) {
    if (node == null) {
      throw new AssertionError(I18N.getString(NODE_IS_NULL));
    }
    if (text == null) {
      if (node.getText() != null) {
        throw new AssertionError(TEXT_SHOULDBE + text + BUT_IS + node.getText());
      }

    } else {
      if (!text.equals(node.getText())) {
        throw new AssertionError(TEXT_SHOULDBE + text + BUT_IS + node.getText());
      }
    }
  }

  /**
   * Assert that the given node has a tooltip assigned with the given content
   * string.
   *
   * @param node An instance of Control.
   * @param text The required text string.
   */
  public static void assertTooltipText(Control node, String text) {
    if (node == null) {
      throw new AssertionError(I18N.getString(NODE_IS_NULL));
    }
    if (text == null) {
      if (node.getTooltip() != null) {
        throw new AssertionError("Tooltip is not null, but should be.");
      }
    } else {
      if (node.getTooltip() == null) {
        throw new AssertionError(TOOLTIPGETTEXT_SHOULDBE + text + ", but is null");
      }
      if (!text.equals(node.getTooltip().getText())) {
        throw new AssertionError(TOOLTIPGETTEXT_SHOULDBE + text + BUT_IS + node.getTooltip().getText());
      }
    }
  }

  /**
   * Assert that the give node is visible, i.e., it's visibel property is true.
   *
   * @param node The node to be checked
   */
  public static void assertVisible(Node node) {
    if (node == null) {
      throw new AssertionError(I18N.getString(NODE_IS_NULL));
    }
    if (!node.isVisible()) {
      throw new AssertionError(NODE + node + " should be visible, but is not.");
    }
  }
}
