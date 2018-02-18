/*
 * Copyright (C) 2018 Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;.
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

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test for the {@link FXUnit} class, must init the FX framework only once,
 * hence tests for {@link FXUnit#init(javafx.application.Application) } have
 * been mived to {@link FXUnitInitApplicationTest}.
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class FXUnitTest {

  public FXUnitTest() {
  }

  @BeforeClass
  public static void setUpClass() {
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  /**
   * Test of init method, of class FXUnit: the framework must allow usage of the
   * FX framework, e.g., showing a new stage.
   */
  @Test
  public void testInit() {
    System.out.println("init");
    FXUnit.init();

    Platform.runLater(() -> {
      Stage testStage = new Stage();
      testStage.setScene(new Scene(new VBox(new Label("Init OK."))));
      testStage.show();
      testStage.hide();
    });
  }
//
//  /**
//   * Test of shutdown method, of class FXUnit.
//   */
//  @Test
//  public void testShutdown() {
//    System.out.println("shutdown");
//    FXUnit.shutdown();
//  }
//

}
