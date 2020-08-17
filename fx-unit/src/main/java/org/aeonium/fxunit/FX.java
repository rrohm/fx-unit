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

import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

/**
 * Utility for testing with a fluent API.
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class FX {
  
  private static final String NO_NODE_FOUND_FOR_ID_ = "No node found for ID ";
  private static final String VALUE_OF_ = "Value of ";

  private final Node node;

  public static FX lookup(String id) {
    if (FXUnit.getStage() == null) {
      throw new NullPointerException("FXUnit.getStage() is null. Did you initialize the framework properly or do you rather want to test a stage created by yourself? In this case have a look at FX.lookup(stage, id)");
    }
    if (FXUnit.getStage().getScene() == null) {
      throw new NullPointerException("FXUnit.getStage() has no valid scene. Did you load content?");
    }
    Node node = FXUnit.getStage().getScene().lookup(id);
    if (node == null) {
      throw new AssertionError(NO_NODE_FOUND_FOR_ID_ + id);
    }
    return new FX(node);
  }

  public static FX lookup(Stage stage, String id) {
    if (stage == null) {
      throw new NullPointerException("stage must not be null.");
    }
    if (stage.getScene() == null) {
      throw new NullPointerException("stage.getScene() must not be null.");
    }
    Node node = stage.getScene().lookup(id);
    if (node == null) {
      throw new AssertionError(NO_NODE_FOUND_FOR_ID_ + id);
    }
    return new FX(node);
  }

  /**
   * Get a collection of child nodes, in order to run further tests on them.
   *
   * @return An FXCollection wrapping the child nodes.
   */
  public FXCollection children() {
    if (this.node instanceof Parent) {
      final Parent parent = (Parent) this.node;
      if (parent.getChildrenUnmodifiable().size() > 0) {
        return new FXCollection(parent.getChildrenUnmodifiable());
      } else {
        throw new AssertionError("This parent has no children.");
      }
    } else {
      throw new AssertionError("This node is not an instance of Parent");
    }
  }

  /**
   * Assert that the selected node has the given number of child nodes.
   *
   * @param count The required child node count.
   * @return The FX instance, for call chaining ("fluent API").
   * @see AssertFX#assertHasChildren(javafx.scene.Parent, int) 
   */
  public FX hasChildren(int count) {
    if (this.node instanceof Parent) {
      Parent parent = (Parent) this.node;
      AssertFX.assertHasChildren(parent, count);
    } else {
      if (count == 0) {
        LOG.log(Level.WARNING, "{0} is not an instance of Parent, cannot have child nodes.", this.node);
      } else {
        throw new AssertionError(this.node + " is not an instance of Parent, cannot have child nodes.");
      }
    }

    return this;
  }

  /**
   * Assert that the selected node is an instance of Labeled or an instance of
   * TextInputControl and has a given value in it's text property.
   *
   * @param text The text content to be tested for.
   * @return The FX instance, for call chaining ("fluent API").
   * @see AssertFX#assertText(javafx.scene.control.TextInputControl, java.lang.String) 
   * @see AssertFX#assertText(javafx.scene.control.Labeled, java.lang.String) 
   */
  public FX hasText(String text) {
    if (this.node instanceof Labeled) {
      Labeled labeled = (Labeled) this.node;
      AssertFX.assertText(labeled, text);
    } else if (this.node instanceof TextInputControl) {
      TextInputControl textInputControl = (TextInputControl) this.node;
      AssertFX.assertText(textInputControl, text);
    } else {
      throw new AssertionError("hasText() is only supported on Labeled/TextInputControl and descendants. Node is a " + this.node.getClass().getName());
    }
    return this;
  }

  /**
   * Assert that the selected node has a tooltip with the given text content.
   *
   * @param text The tooltip text
   * @return The FX instance, for call chaining ("fluent API").
   * @see AssertFX#assertTooltipText(javafx.scene.control.Control, java.lang.String) 
   */
  public FX hasTooltipText(String text) {
    if (this.node instanceof Control) {
      Control control = (Control) this.node;
      AssertFX.assertTooltipText(control, text);
    } else {
      throw new AssertionError("hasTooltipText() is only supported on Control and descendants. Node is a " + this.node.getClass().getName());
    }
    return this;
  }

  /**
   * Assert that the selected node is disabled.
   *
   * @see AssertFX#assertDisabled(javafx.scene.Node) 
   * @return The FX instance, for call chaining ("fluent API").
   */
  public FX isDisabled() {
    AssertFX.assertDisabled(this.node);
    return this;
  }

  /**
   * Assert that the selected node is enabled.
   *
   * @see AssertFX#assertEnabled(javafx.scene.Node) 
   * @return The FX instance, for call chaining ("fluent API").
   */
  public FX isEnabled() {
    AssertFX.assertEnabled(this.node);
    return this;
  }

  /**
   * Assert that the given node is editable, i.e., it is a descendant of
   * TextInputControl and it's "editable" property is true;
   * 
   * @see AssertFX#assertEditable(javafx.scene.Node) 
   * @return The FX instance, for call chaining ("fluent API").
   */
  public FX isEditable() {
    AssertFX.assertEditable(this.node);
    return this;
  }
  /**
   * Assert that the selected node has no child nodes.
   *
   * @return The FX instance, for call chaining ("fluent API").
   */
  public FX isEmpty() {
    if (this.node instanceof Parent) {
      Parent parent = (Parent) this.node;
      AssertFX.assertHasChildren(parent, 0);
    } else {
      LOG.log(Level.WARNING, "{0} is not an instance of Parent, cannot have child nodes.", this.node);
    }

    return this;
  }

  /**
   * Assert that the selected node is focused.
   *
   * @return The FX instance, for call chaining ("fluent API").
   * @see AssertFX#assertFocused(javafx.scene.Node) 
   */
  public FX isFocused() {
    AssertFX.assertFocused(this.node);
    return this;
  }

  private static final Logger LOG = Logger.getLogger(FX.class.getName());

  /**
   * Assert that the selected node is an instance of Parent and has any
   * children.
   *
   * @return The FX instance, for call chaining ("fluent API").
   */
  public FX isNotEmpty() {
    if (this.node instanceof Parent) {
      final Parent parent = (Parent) this.node;
      if (parent.getChildrenUnmodifiable().isEmpty()) {
        throw new AssertionError("This parent has no children, but should not be empty.");
      }
    } else {
      throw new AssertionError("This node is not an instance of Parent");
    }
    return this;
  }

  /**
   * Assert that the selected node is <i>not</i> in managed state.
   *
   * @return The FX instance, for call chaining ("fluent API").
   * @see AssertFX#assertNotManaged(javafx.scene.Node) 
   */
  public FX isNotManaged() {
    AssertFX.assertNotManaged(node);
    return this;
  }

  /**
   * Assert that the selected node is an instance of ToggleButton and is
   * <i>not</i> selected.
   *
   * @return The FX instance, for call chaining ("fluent API").
   * @see AssertFX#assertNotSelected(javafx.scene.control.ToggleButton) 
   */
  public FX isNotSelected() {
    if (this.node instanceof ToggleButton) {
      ToggleButton toggleButton = (ToggleButton) this.node;
      AssertFX.assertNotSelected(toggleButton);
    } else {
      throw new AssertionError(this.node + " is not an instance of ToggleButton, does not support isSelected().");
    }
    return this;
  }

  /**
   * Assert that the selected node is <i>not</i> visible.
   *
   * @return The FX instance, for call chaining ("fluent API").
   * @see AssertFX#assertNotVisible(javafx.scene.Node) 
   */
  public FX isNotVisible() {
    AssertFX.assertNotVisible(node);
    return this;
  }

  /**
   * Assert that there is no node with the given ID.
   *
   * @param id The node ID.
   */
  public static void isNotPresent(String id) {
    if (FXUnit.getStage() == null) {
      throw new NullPointerException("FXUnit.getStage() is null. Did you initialize the framework properly or do you rather want to test a stage created by yourself? In this case have a look at FX.lookup(stage, id)");
    }
    if (FXUnit.getStage().getScene() == null) {
      throw new NullPointerException("FXUnit.getStage() has no valid scene. Did you load content?");
    }
    Node nodeNotToBePresent = FXUnit.getStage().getScene().lookup(id);
    if (nodeNotToBePresent != null) {
      throw new AssertionError("Node with ID " + id + " is present, but should not.");
    }
  }

  /**
   * Assert that the selected node is an instance of ToggleButton and is
   * selected.
   *
   * @return The FX instance, for call chaining ("fluent API").
   * @see AssertFX#assertSelected(javafx.scene.control.ToggleButton) 
   */
  public FX isSelected() {
    if (this.node instanceof ToggleButton) {
      ToggleButton toggleButton = (ToggleButton) this.node;
      AssertFX.assertSelected(toggleButton);
    } else {
      throw new AssertionError(this.node + " is not an instance of ToggleButton, does not support isSelected().");
    }
    return this;
  }

  /**
   * Assert that the selected node is in managed state.
   *
   * @return The FX instance, for call chaining ("fluent API").
   * @see AssertFX#assertManaged(javafx.scene.Node) 
   */
  public FX isManaged() {
    AssertFX.assertManaged(node);
    return this;
  }

  /**
   * Assert that the selected node is visible.
   *
   * @return The FX instance, for call chaining ("fluent API").
   * @see AssertFX#assertVisible(javafx.scene.Node) 
   */
  public FX isVisible() {
    AssertFX.assertVisible(node);
    return this;
  }

  /**
   * Create a new FX-Unit test helper that operates on the given node. The given
   * node may be tested with the fluent API of fx-unit.
   *
   * @param node
   */
  public FX(Node node) {
    this.node = node;
  }

  /**
   * Execute the fire() method on the selected node, i.e., simulate a mouse
   * click with the JavaFX API. This method takes care of firing the button on 
   * the FX application thread.
   *
   * @return The FX instance, for call chaining ("fluent API").
   */
  public FX fire() {
    if (node instanceof ButtonBase) {
      ButtonBase button = (ButtonBase) node;
      try {
        FXHelper.runAndWait(() -> {
          button.fire();
        });
      } catch (ExecutionException ex) {
        Logger.getLogger(FX.class.getName()).log(Level.SEVERE, null, ex);
        throw new RuntimeException(ex);
      }
    } else {
      throw new UnsupportedOperationException("Type " + this.node.getClass().getName() + " is not supported. Currently, fire() supports ButtonBase and descendants only.");
    }
    return this;
  }

  /**
   * Assert that the selected node has a context menu and show it.
   *
   * @return
   */
  public FXMenu getContextMenu() {
    if (this.node instanceof Control) {
      Control control = (Control) this.node;
      ContextMenu contextMenu = control.getContextMenu();
      if (contextMenu == null) {
        throw new AssertionError("This node no context menu.");
      }
      try {
        FXHelper.runAndWait(() -> {
          contextMenu.show(control.getScene().getWindow());
        });
      } catch (ExecutionException ex) {
        Logger.getLogger(FX.class.getName()).log(Level.SEVERE, null, ex);
        throw new RuntimeException(ex);
      }
      return new FXMenu(contextMenu.getItems());
    } else {
      throw new AssertionError("This node cannot have a context menu. It is of type " + this.node.getClass());
    }
  }

  /**
   * Returns the node (SUT, system-under-test) that this instance operates on -
   * must not return null, since construction of an instance fails if the node
   * is not found.
   *
   * @param <T>
   * @return The node that this instance operates on.
   */
  public <T extends Node> T getNode() {
    if (this.node == null) {
      throw new NullPointerException("This method should not return null. Please use the constructor FX(Node node) only.");
    }
    return (T) this.node;
  }

  public <T extends Node> T getNode(Class<T> t) {
    if (this.node == null) {
      throw new NullPointerException("This method should not return null. Please use the constructor FX(Node node) only.");
    }
    return (T) this.node;
  }

  public FX hasMenuItem(String id) {
    if (!id.startsWith("#")) {
      id = "#".concat(id);
    }
    if (this.node.getScene().getWindow() instanceof ContextMenu) {
      ContextMenu contextMenu = (ContextMenu) this.node.getScene().getWindow();

      boolean found = false;
      id = id.substring(1);
      for (MenuItem item : contextMenu.getItems()) {
        if (item.getId() != null && item.getId().equals(id)) {
          found = true;
          break;
        }
      }
      if (!found) {
        throw new AssertionError("There is no MenuItem with id #" + id);
      }

    } else {
      throw new AssertionError("Cannot have MenuItems. Window is of class " + this.node.getScene().getWindow().getClass());
    }
    return this;
  }

  public <T> FX hasValue(T value) {
    if (this.node instanceof ChoiceBox) {
      ChoiceBox<T> choiceBox = (ChoiceBox<T>) this.node;
      if (value == null) {
        if (choiceBox.getValue() != null) {
          throw new AssertionError(VALUE_OF_ + this.node + " should be null, but is " + choiceBox.getValue());
        }
      } else {
        if (!value.equals(choiceBox.getValue())) {
          throw new AssertionError(VALUE_OF_ + this.node + " should be " + value + ", but is " + choiceBox.getValue());
        }
      }
    } else {
      throw new UnsupportedOperationException("Type " + this.node.getClass().getName() + " is not supported. Currently, hasValue() supports ChoiceBox only.");
    }
    return this;
  }

  /**
   * Select the n-th element of the wrapped control.
   *
   * @param <T> The item type
   * @param index The index n to set the selection model to.
   * @return The FX instance, for call chaining ("fluent API").
   */
  public <T> FX select(int index) {
    if (this.node instanceof ChoiceBox) {
      ChoiceBox<T> choiceBox = (ChoiceBox<T>) this.node;
      if (choiceBox.getItems().size() <= index) {
        throw new IndexOutOfBoundsException(this.node.toString() + " has only " + choiceBox.getItems().size() + " items.");
      }
      try {
        FXHelper.runAndWait(() -> {
          choiceBox.getSelectionModel().select(index);
        });
      } catch (ExecutionException ex) {
        Logger.getLogger(FX.class.getName()).log(Level.SEVERE, null, ex);
        throw new RuntimeException(ex);
      }
    } else if (this.node instanceof ListView) {
      ListView<T> listView = (ListView<T>) this.node;
      if (listView.getItems().size() <= index) {
        throw new IndexOutOfBoundsException(this.node.toString() + " has only " + listView.getItems().size() + " items.");
      }
      try {
        FXHelper.runAndWait(() -> {
          listView.getSelectionModel().select(index);
        });
      } catch (ExecutionException ex) {
        Logger.getLogger(FX.class.getName()).log(Level.SEVERE, null, ex);
        throw new RuntimeException(ex);
      }
    } else if (this.node instanceof TableView) {
      TableView<T> tableView = (TableView<T>) this.node;
      if (tableView.getItems().size() <= index) {
        throw new IndexOutOfBoundsException(this.node.toString() + " has only " + tableView.getItems().size() + " items.");
      }
      try {
        FXHelper.runAndWait(() -> {
          tableView.getSelectionModel().select(index);
        });
      } catch (ExecutionException ex) {
        Logger.getLogger(FX.class.getName()).log(Level.SEVERE, null, ex);
        throw new RuntimeException(ex);
      }
    } else {
      throw new UnsupportedOperationException("Type " + this.node.getClass().getName() + " is not supported. Currently, select() supports ChoiceBox, ListView and TableView only.");
    }
    return this;
  }

//  public <T> FX select(T value) {
//    if (this.node instanceof ChoiceBox) {
//      ChoiceBox<T> choiceBox = (ChoiceBox<T>) this.node;
//      choiceBox.getSelectionModel().select(value);
//      if (choiceBox.getValue() == null) {
//        if (choiceBox.getValue() != null) {
//          throw new AssertionError("Value of " + this.node + " should be null, but is " + choiceBox.getValue());
//        }
//      } else {
//        if (!value.equals(choiceBox.getValue())) {
//          throw new AssertionError("Value of " + this.node + " should be " + value + ", but is " + choiceBox.getValue());
//        }
//      }
//    } else {
//      throw new UnsupportedOperationException("Type " + this.node.getClass().getName() + " is not supported. Currently, hasValue() supports ChoiceBox only.");
//    }
//    return this;
//  }
  public <T> FX setValue(T value) {
    if (this.node instanceof ChoiceBox) {
      ChoiceBox<T> choiceBox = (ChoiceBox<T>) this.node;
      choiceBox.setValue(value);
      if (value == null) {
        if (choiceBox.getValue() != null) {
          throw new AssertionError(VALUE_OF_ + this.node + " should be null, but is " + choiceBox.getValue());
        }
      } else {
        if (!value.equals(choiceBox.getValue())) {
          throw new AssertionError(VALUE_OF_ + this.node + " should be " + value + ", but is " + choiceBox.getValue());
        }
      }
    } else {
      throw new UnsupportedOperationException("Type " + this.node.getClass().getName() + " is not supported. Currently, hasValue() supports ChoiceBox only.");
    }
    return this;
  }

  /**
   * Convenience method for delaying for a number of milliseconds – if not
   * running on the JavaFX Application Thread. In this case, the method will not
   * wait, in order to not slow down the UI. Hence, use this method if you think
   * it is necessary to allow UI rendering to catch up with state changes like
   * selection, highlighting etc.
   *
   * @param millis Milliseconds to delay, if not running on the JavaFX
 Application Thread.
   * @return The FX instance, for call chaining ("fluent API").
   */
  public FX delay(int millis) {
    if (!Platform.isFxApplicationThread()) {
      try {
        Thread.sleep(millis);
      } catch (InterruptedException ex) {
        Logger.getLogger(FX.class.getName()).log(Level.SEVERE, null, ex);
        Thread.currentThread().interrupt();
      }
    } else {
      LOG.warning("Delaying on the FX application thread? Seriously?");
    }
    return this;
  }
}
