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

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.aeonium.fxunit.DriverApp.FXUnitApp;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author robert
 */
public class DriverAppTest {
  
  Stage stage; 
  
  public DriverAppTest() {
  }

  @BeforeAll
  public static void setUpClass() {
    final Class<? extends Application> appClass = FXUnitApp.class;
    Thread t = new Thread("JavaFX Init Thread") {
      @Override
      public void run() {
        try {
          Application.launch(appClass, new String[0]);
        } catch (IllegalStateException ex) {
          if (!ex.getMessage().equals("Application launch must not be called more than once")) {
            throw ex;
          }
        }
      }
    };
    t.setDaemon(true);
    t.start();
    try {
      Thread.sleep(1000);
    } catch (InterruptedException ex) {
      Logger.getLogger(FXHelper.class.getName()).log(Level.INFO, null, ex);
      Thread.currentThread().interrupt();
    }
  }

  @AfterAll
  public static void tearDownClass() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException ex) {
      Logger.getLogger(FXHelper.class.getName()).log(Level.INFO, null, ex);
      Thread.currentThread().interrupt();
    }
  }

  @BeforeEach
  public void setUp() {
    Platform.runLater(() -> {
      stage = new Stage();
      stage.show();
    });
  }

  @AfterEach
  public void tearDown() {
    Platform.runLater(() -> {
      if (stage != null) {
        stage.hide();
      }
    });
  }


  /**
   * Test of setApplication method, of class DriverApp.
   */
  @Test
  public void testSetApplication() {
    System.out.println("setApplication");
    Application fxApp = new FXUnitApp();
    DriverApp.setApplication(fxApp);
    assertEquals(fxApp, DriverApp.app);
  }

  /**
   * Test of init method, of class DriverApp.
   * @throws java.lang.Exception any
   */
  @Test
  public void testInit() throws Exception {
    System.out.println("init");
    DriverApp instance = new DriverApp();
    instance.init();
    // TODO: testability
  }

  /**
   * Test of start method, of class DriverApp.
   * @throws java.lang.Exception any
   */
  @Test
  public void testStart() throws Exception {
    System.out.println("start");
    Stage primaryStage = null;
    DriverApp instance = new DriverApp();
    instance.start(primaryStage);
    // TODO: testability
  }
  
}
