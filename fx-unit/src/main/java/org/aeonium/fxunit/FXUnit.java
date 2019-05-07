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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Main class of the framework, provides only the static initializer method.
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class FXUnit {

  private static Object controller;
  private static Parent root;
  private static Stage stage;

  /**
   * Returns the stage of the UI under test, after the FXML UI has been loaded
   * and displayed with the {@link #show(java.net.URL) } method.
   *
   * @return
   */
  public static Stage getStage() {
    return stage;
  }

  /**
   * Returns the controller of the FXML UI under test, after the FXML UI has
   * been loaded with the {@link #load(java.net.URL)} method or displayed using
   * {@link #show(java.net.URL)};
   *
   * @param <T> The type of the controller.
   * @return The controller.
   */
  public static <T> T getController() {
    return (T) controller;
  }

  /**
   * Returns the root node of the UI under test. Load the UI with the {@link #load(java.net.URL)
   * } or the {@link #show(java.net.URL) } method in order to use this getter.
   *
   * @return
   */
  public static Parent getRoot() {
    return root;
  }

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
   * Load an FXML UI from the given URL and set the controller and root node
   * properties according to the FXML.
   *
   * @param url The FXML URL.
   */
  public static void load(URL url) {
    if (url == null) {
      throw new NullPointerException("Location is not set. Please provide a valid URL.");
    }

    try {
      FXMLLoader loader = new FXMLLoader(url);
      loader.load();
      controller = loader.getController();
      root = loader.getRoot();

    } catch (IOException ex) {
      Logger.getLogger(FXUnit.class.getName()).log(Level.SEVERE, null, ex);
      throw new FXUnitException("Cannot load FXML.", ex);
    }
  }

  /**
   * Load an FXML UI from the given URL and instatiate the given controller
   * class. The {@link #controller} reference is set to the new instance.
   * {@link #root} is set to the root node of the FXML UI.
   *
   * @param url
   * @param controllerClass
   */
  public static void load(URL url, Class controllerClass) {
    if (url == null) {
      throw new NullPointerException("Location is not set. Please provide a valid URL.");
    }

    try {
      FXMLLoader loader = new FXMLLoader(url);
      loader.setController(controllerClass.getDeclaredConstructor().newInstance());
      loader.load();
      controller = loader.getController();
      root = loader.getRoot();

    } catch (IOException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException ex) {
      Logger.getLogger(FXUnit.class.getName()).log(Level.SEVERE, null, ex);
      throw new FXUnitException("Cannot load FXML and Controller.", ex);
    }
  }

  /**
   * Load an FXML UI into a new (undecorated) stage and show it. This method
   * internally uses {@link #load(java.net.URL) } which sets the references to
   * the current {@link #getController() controller} and the current
   * {@link #getRoot() root node}.
   *
   * @param url URL of the FXML file.
   */
  public static void show(URL url) {
    Platform.runLater(() -> {
      load(url);
      showTestingStage(url);
    });

    sleep();
  }

  public static void show(URL url, Class controller) {
    Platform.runLater(() -> {
      load(url, controller);
      showTestingStage(url);
    });

    sleep();
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

  private static void showTestingStage(URL url) {
    stage = new Stage(StageStyle.UNDECORATED);
    stage.setTitle("FXUnit testing " + getShortFilenameFromURL(url));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  private static void sleep() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException ex) {
      Logger.getLogger(FXUnit.class.getName()).log(Level.SEVERE, null, ex);
      Thread.currentThread().interrupt();
    }
  }

  private static String getShortFilenameFromURL(URL url) {
    final String filename = url.getFile();
    if (filename.contains("/")) {
      return filename.substring(filename.lastIndexOf("/") + 1);
    } else {
      return filename;
    }
  }

}
