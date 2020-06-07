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

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

/**
 * Utility for testing with a fluent API.
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class FX {

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
      throw new AssertionError("No node found for ID " + id);
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
      throw new AssertionError("No node found for ID " + id);
    }
    return new FX(node);
  }
  
  public FXCollection children(){
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
  
  public FX hasText(String text){
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
  
  public FX hasTooltipText(String text){
    if (this.node instanceof Control) {
      Control control = (Control) this.node;
      AssertFX.assertTooltipText(control, text);
    } else {
      throw new AssertionError("hasTooltipText() is only supported on Control and descendants. Node is a " + this.node.getClass().getName());
    }
    return this;
  }

  public FX isEmpty() {
    if (this.node instanceof Parent) {
      Parent parent = (Parent) this.node;
      AssertFX.assertHasChildren(parent, 0);
    }
    LOG.log(Level.WARNING, "{0} is not an instance of Parent, cannot have child nodes.", this.node);
    return this;
  }
  private static final Logger LOG = Logger.getLogger(FX.class.getName());

  public FX isNotManaged() {
    AssertFX.assertNotManaged(node);
    return this;
  }
  
  public FX isNotSelected() {
    if (this.node instanceof ToggleButton) {
      ToggleButton toggleButton = (ToggleButton) this.node;
      AssertFX.assertNotSelected(toggleButton);
    } else {
      throw new AssertionError(this.node + " is not an instance of ToggleButton, does not support isSelected().");
    }
    return this;
  }

  public FX isNotVisible() {
    AssertFX.assertNotVisible(node);
    return this;
  }

  public FX isSelected() {
    if (this.node instanceof ToggleButton) {
      ToggleButton toggleButton = (ToggleButton) this.node;
      AssertFX.assertSelected(toggleButton);
    } else {
      throw new AssertionError(this.node + " is not an instance of ToggleButton, does not support isSelected().");
    }
    return this;
  }

  public FX isManaged() {
    AssertFX.assertManaged(node);
    return this;
  }

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
   * Returns the node (SUT, system-under-test) that this instance operates on -
   * must not return null, since construction of an instance fails if the node
   * is not found.
   *
   * @return The node that this instance operates on.
   */
  public Node getNode() {
    if (this.node == null) {
      throw new NullPointerException("This method shoul not return null. Please use the constructor FX(Node node) only.");
    }
    return this.node;
  }

  public <T> FX hasValue(T value) {
    if (this.node instanceof ChoiceBox) {
      ChoiceBox<T> choiceBox = (ChoiceBox<T>) this.node;
      if (value == null) {
        if (choiceBox.getValue() != null) {
          throw new AssertionError("Value of " + this.node + " should be null, but is " + choiceBox.getValue());
        }
      } else {
        if (!value.equals(choiceBox.getValue())) {
          throw new AssertionError("Value of " + this.node + " should be " + value + ", but is " + choiceBox.getValue());
        }
      }
    } else {
      throw new UnsupportedOperationException("Type " + this.node.getClass().getName() + " is not supported. Currently, hasValue() supports ChoiceBox only.");
    }
    return this;
  }
  
  
  public <T> FX setValue(T value) {
    if (this.node instanceof ChoiceBox) {
      ChoiceBox<T> choiceBox = (ChoiceBox<T>) this.node;
      choiceBox.setValue(value);
      if (value == null) {
        if (choiceBox.getValue() != null) {
          throw new AssertionError("Value of " + this.node + " should be null, but is " + choiceBox.getValue());
        }
      } else {
        if (!value.equals(choiceBox.getValue())) {
          throw new AssertionError("Value of " + this.node + " should be " + value + ", but is " + choiceBox.getValue());
        }
      }
    } else {
      throw new UnsupportedOperationException("Type " + this.node.getClass().getName() + " is not supported. Currently, hasValue() supports ChoiceBox only.");
    }
    return this;
  }
  
  
}
