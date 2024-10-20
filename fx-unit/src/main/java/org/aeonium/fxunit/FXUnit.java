/*
 * Copyright (C) 2024 Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;.
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
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.aeonium.fxunit.DriverApp.FXUnitApp;

/**
 * Main class of the framework, provides only the static initializer method.
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class FXUnit {

  private static final String LOCATION_IS_NOT_SET = "Location is not set. Please provide a valid URL.";

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
  @SuppressWarnings("unchecked")
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
        try {
          Application.launch(FXUnitApp.class, new String[0]);
        } catch (IllegalStateException e) {
          System.err.println(e.getMessage());
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

  public static void load(String url) {
    load(FXUnit.class.getResource(url));
  }

  /**
   * Load an FXML UI from the given URL and set the controller and root node
   * properties according to the FXML.
   *
   * @param url The FXML URL.
   */
  public static void load(URL url) {
    if (url == null) {
      throw new NullPointerException(LOCATION_IS_NOT_SET);
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

  public static void load(URL url, ResourceBundle rb) {
    if (url == null) {
      throw new NullPointerException(LOCATION_IS_NOT_SET);
    }
    if (rb == null) {
      throw new NullPointerException("ResourceBundle must not be null.");
    }

    try {
      FXMLLoader loader = new FXMLLoader(url);
      loader.setResources(rb);
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
  @SuppressWarnings("unchecked")
  public static void load(URL url, Class<?> controllerClass) {
    if (url == null) {
      throw new NullPointerException(LOCATION_IS_NOT_SET);
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
   * Show the given node in a testing stage. The method waits for the stage to
   * be showen on the JavaFX thread.
   *
   * @param node The node to test.
   */
  public static void show(Node node) {
    final CountDownLatch latch = new CountDownLatch(1);
    Platform.runLater(() -> {
      showTestingStage(node);
      latch.countDown();
    });

    try {
      latch.await();
    } catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
    }
  }

  /**
   * Load an FXML UI into a new (undecorated) stage and show it. This method
   * internally uses {@link #load(java.net.URL) } which sets the references to
   * the current {@link #getController() controller} and the current
   * {@link #getRoot() root node}. The method waits for the stage to be showen
   * on the JavaFX thread.
   *
   * @param url The url string for the FXML document do test.
   */
  public static void show(String url) {
    show(FXUnit.class.getResource(url));
  }

  /**
   * Load an FXML UI into a new (undecorated) stage and show it. This method
   * internally uses {@link #load(java.net.URL) } which sets the references to
   * the current {@link #getController() controller} and the current
   * {@link #getRoot() root node}. The method waits for the stage to be showen
   * on the JavaFX thread.
   *
   * @param url URL of the FXML file.
   */
  public static void show(URL url) {
    final CountDownLatch latch = new CountDownLatch(1);
    Platform.runLater(() -> {
      load(url);
      showTestingStage(url);
      latch.countDown();
    });

    try {
      latch.await();
    } catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
    }
  }

  /**
   * Load an FXML UI into a new (undecorated) stage and show it. This method
   * internally uses {@link #load(java.net.URL, java.util.ResourceBundle)} which
   * sets the references to the current {@link #getController() controller} and
   * the current {@link #getRoot() root node}. The method waits for the stage to
   * be showen on the JavaFX thread.
   *
   * @param url URL of the FXML file.
   * @param rb The resource bundle for I18N.
   */
  public static void show(URL url, ResourceBundle rb) {
    final CountDownLatch latch = new CountDownLatch(1);
    Platform.runLater(() -> {
      load(url, rb);
      showTestingStage(url);
      latch.countDown();
    });

    try {
      latch.await();
    } catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
    }
  }

  /**
   * Load an FXML UI into a new (undecorated) stage and show it, using the given
   * controller object for this FXML UI. The method waits for the stage to be
   * showen on the JavaFX thread.
   *
   * @param url URL of the FXML file.
   * @param controller The controller instance to use with the UI to test.
   */
  public static void show(URL url, Class<?> controller) {
    final CountDownLatch latch = new CountDownLatch(1);
    Platform.runLater(() -> {
      load(url, controller);
      showTestingStage(url);
      latch.countDown();
    });

    try {
      latch.await();
    } catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
    }
  }

  /**
   * Close the current stage, if it is not null, and add a delay of a second,
   * e.g., to ensure cleanups to get actual done.
   */
  public static void closeStage() {
    if (stage != null) {
      FXHelper.shutdownStage(stage);
    }

    try {
      Thread.sleep(1000);
    } catch (InterruptedException ex) {
      Logger.getLogger(FXUnitTestBase.class.getName()).log(Level.INFO, null, ex);
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

  private static void showTestingStage(Node node) {
    stage = new Stage(StageStyle.UNDECORATED);
    stage.setTitle("FXUnit testing " + node);

    Scene scene;
    if (node instanceof Parent) {
      scene = new Scene((Parent) node);
    } else {
      Parent parent = new Pane(node);
      scene = new Scene(parent);
    }
    stage.setScene(scene);
//    stage.setWidth(node.prefWidth(stage.getWidth()));
//    stage.setHeight(node.prefHeight(stage.getHeight()));
    stage.show();
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
