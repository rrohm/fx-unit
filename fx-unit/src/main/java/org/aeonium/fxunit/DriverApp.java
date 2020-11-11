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
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This application is used to initialize the JavaFX framework.
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class DriverApp extends Application {

  public static class FXUnitApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
      final Label label = new Label("fx-unit test running ...");
      label.setTextFill(Color.WHITE);
      final ProgressIndicator progressIndicator = new ProgressIndicator(-1);
      progressIndicator.setStyle("-fx-progress-color: #fff");
      final VBox box = new VBox(10.0, label, progressIndicator);
      box.setPadding(new Insets(10.0));
      box.setAlignment(Pos.CENTER);
      box.setBackground(new Background(new BackgroundFill(Color.rgb(0, 102, 102), CornerRadii.EMPTY, Insets.EMPTY)));

      final Scene scene = new Scene(box);
      stage.setTitle("fx-unit test running ...");
      stage.setScene(scene);
      stage.initStyle(StageStyle.UNDECORATED);
      stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      launch(args);
    }

  }

  private static final Logger LOG = Logger.getLogger(DriverApp.class.getName());

  static Application app;

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
