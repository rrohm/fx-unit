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
 * This application is used to initialize the JavaFX framework. 
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class DriverApp extends Application {

  private static final Logger LOG = Logger.getLogger(DriverApp.class.getName());

  private static Application app;

  public static void setApplication(Application fxApp) {
    LOG.log(Level.INFO, "setApplication: {0}", fxApp);
    app = fxApp;
  }

  @Override
  public void init() throws Exception {
    LOG.info("init");
    try {
      app.init();
    } catch (Exception exception) {
      LOG.log(Level.SEVERE, exception.getMessage(), exception);
    }
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    LOG.info("start");
    try {
      app.start(primaryStage);
    } catch (Exception exception) {
      LOG.log(Level.SEVERE, exception.getMessage(), exception);
    }
  }

}
