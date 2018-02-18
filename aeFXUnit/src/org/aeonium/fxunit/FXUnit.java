/*
 * Copyright (C) 2018 Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;.
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
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class of the framework, provides only the static initializer method.
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class FXUnit {

  /**
   * Hide implicit ctor.
   */
  private FXUnit() {
    // no op
  }

  
  /**
   * Initialize the JavaFX application framework - necessary for testing JavaFX
   * GUIs.
   */
  public static void init() {
    Thread t = new Thread("FXUnit Init Thread") {
      @Override
      public void run() {
        Application.launch(FXUnitApp.class, new String[0]);
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

  /**
   * Optional method for shutting down the FXUnit framework - currently it is
   * only used for adding a delay of 1s when finishing all test, in order to
   * ensure that the last log actions may terminate.
   */
  public static void shutdown() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException ex) {
      Logger.getLogger(FXHelper.class.getName()).log(Level.INFO, null, ex);
      Thread.currentThread().interrupt();
    }
  }

  public static void init(Application instance) {
    DriverApp.setApplication(instance);
    Application.launch(DriverApp.class, new String[0]);
  }

  public static void shutdown(Stage stage) {
    FXHelper.shutdownStage(stage);
  }

}
