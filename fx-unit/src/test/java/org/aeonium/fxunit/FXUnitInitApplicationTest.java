/*
 * Copyright (C) 2024 Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;.
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

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.aeonium.fxunit.DriverApp.FXUnitApp;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for the {@link FXUnit} class, must init the FX framework only once,
 * hence tests for {@link FXUnit#init() } have
 * been mived to {@link FXUnitTestBase}.
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class FXUnitInitApplicationTest {

  public FXUnitInitApplicationTest() {
  }

  @BeforeAll
  public static void setUpClass() {
  }

  @AfterAll
  public static void tearDownClass() {
  }

  @BeforeEach
  public void setUp() {
  }

  @AfterEach
  public void tearDown() {
  }

  /**
   * Test of init method, of class FXUnit: the framework must allow usage of the
   * FX framework, e.g., showing a new stage.
   */
  @Test
  public void testInit_Application() {
    System.out.println("init");
    Platform.setImplicitExit(true);
    BooleanProperty ok = new SimpleBooleanProperty(false);

    Thread t = new Thread(() -> {
      Application instance = new FXUnitApp();
      FXUnit.init(instance);
    });
    t.setDaemon(true);
    t.start();
    try {
      Thread.sleep(1000);
    } catch (InterruptedException ex) {
      Logger.getLogger(FXUnitInitApplicationTest.class.getName()).log(Level.SEVERE, null, ex);
      Thread.currentThread().interrupt();
    }

    Platform.runLater(() -> {
      Stage testStage = new Stage();
      testStage.setScene(new Scene(new VBox(new Label("Init OK."))));
      testStage.show();
      testStage.hide();
      ok.set(true);
    });
    
    try {
      Thread.sleep(1000);
    } catch (InterruptedException ex) {
      Logger.getLogger(FXUnitTestBase.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    Assertions.assertTrue(ok.getValue(), "Do not fail initialization.");
  }
//
//  /**
//   * Test of closeStage method, of class FXUnit.
//   */
//  @Test
//  public void testShutdown_Stage() {
//    System.out.println("closeStage");
//    Stage stage = null;
//    FXUnit.closeStage(stage);
//    // TODO review the generated test code and remove the default call to fail.
//    fail("The test case is a prototype.");
//  }

}
