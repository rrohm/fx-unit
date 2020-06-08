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

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;

/**
 * Base class for easier testing of FXML controllers, can be subclassed with the
 * controller class as type parameter.
 *
 * @author robert
 * @param <T> Controller type;
 */
public abstract class FXUnitTestBase<T> {

  /**
   * Initializes the FXUnit testing framework.
   */
  @BeforeAll
  public static void setUpClass() {
    FXUnit.init();
  }

  /**
   * Provides access to the root node of the SUT.
   *
   * @return The root node.
   */
  protected Parent getRoot() {
    return FXUnit.getRoot();
  }

  /**
   * Provides access to the controller class (the SUT)
   *
   * @return
   */
  protected T getController() {
    return FXUnit.getController();
  }

  /**
   * Provides access to the stage of the SUT.
   *
   * @return
   */
  protected Stage getStage() {
    return FXUnit.getStage();
  }

  /**
   * Shows the SUT node on the default stage.
   *
   * @param node The SUT
   */
  protected void show(Node node) {
    if (node == null) {
      throw new NullPointerException("node must not be null");
    }
    FXUnit.show(node);
  }
}
