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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * A helper class for UI testing.
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public final class FXHelper {

  /**
   * Delay in milliseconds, defaults to 200. Maybe you need to adjust this.
   */
  private static int delay = 200;

  /**
   * Private contructor, there is no need to instantiate this class.
   */
  private FXHelper() {
    // no op.
  }

  /**
   * Clear the text of a Labeled component, i.e., set it to an empty string.
   *
   * @param labeled The component, must be an instance or descendant of Labeled.
   */
  public static void clearText(Labeled labeled) {
    invokeOnFXThread(() -> labeled.setText(""));
    doDelay();
  }

  /**
   * Find a tab sheet by ID in the given tab pane.
   *
   * @param tabPane The tab pane.
   * @param id The id.
   * @return The tab with the given ID.
   */
  public static Tab findTab(TabPane tabPane, String id) {
    ObservableList<Tab> tabs = tabPane.getTabs();
    for (Tab tab : tabs) {
      if (id.equals(tab.getId())) {
        return tab;
      }
    }
    return null;
  }

  /**
   * Request the focus for a given node and ensure that this happens on the
   * JavaFX Application Thread. After this, delay further actions by the
   * specified delay time {@link FXHelper#delay}
   *
   * @param node The node to request the focus for.
   */
  public static void focus(Node node) {

    invokeOnFXThread(node::requestFocus);
    doDelay();
  }

  /**
   * Ensure that the given runnable gets executed on the JavaFX applcation
   * thread.
   *
   * @param runnable
   */
  public static void invokeOnFXThread(final Runnable runnable) {
    if (runnable == null) {
      throw new NullPointerException("No runnable given.");
    }

    if (Platform.isFxApplicationThread()) {
      runnable.run();
    } else {
      Platform.runLater(runnable);
    }
  }

  /**
   * If the current thread is the JavaFX Application thread, run the code of the
   * given runnable on the current thread, i.e., invoke it's <code>run()</code>
   * method directly, otherwise put it on the JavaFX Application thread and wait
   * for completion of this task.
   *
   * @param runnable The code to run on the JavaFX Application thread
   * @throws ExecutionException In case of an error in the executing task.
   */
  public static void runAndWait(Runnable runnable) throws ExecutionException {
    if (runnable == null) {
      throw new NullPointerException("No runnable given.");
    }

    if (Platform.isFxApplicationThread()) {
      runnable.run();
    } else {
      FutureTask future = new FutureTask(runnable, null);
      Platform.runLater(future);
      try {
        future.get();
      } catch (InterruptedException ex) {
        Thread.currentThread().interrupt();
      }
    }
  }

  public static void selectTab(TabPane tabPane, String id) {
    Tab findTab = findTab(tabPane, id);
    if (findTab == null) {
      throw new NullPointerException("No Tab found with ID '" + id + "'");
    }
    tabPane.getSelectionModel().select(findTab);
    doDelay();
  }

  /**
   * Shut down a test stage, i.e., hide it an release it's content.
   *
   * @param stage The stage to be closed.
   */
  public static void shutdownStage(Stage stage) {
    if (stage == null) {
      return;
    }
    FXHelper.invokeOnFXThread(() -> {
      stage.hide();
      stage.setScene(null);
    });
  }

  /**
   * Send key events to the target node that simulate the typing of the given
   * character sequence.
   *
   * @param target The target node
   * @param t The character sequence
   */
  public static void typeKey(Node target, String t) {
    for (char c : t.toCharArray()) {
      typeKey(target, c);
    }
  }

  /**
   * Send key events to the target node that simulate the typing of the given
   * character.
   *
   * @param target The target node
   * @param c The character
   */
  public static void typeKey(Node target, Character c) {

    final String t = c.toString();
    final KeyCode keyCode = KeyCode.getKeyCode(t.toUpperCase());
    if (keyCode == null) {
      throw new FXUnitException("No keycode found for " + c);
    }

    invokeOnFXThread(() -> {
      Event.fireEvent(target, new KeyEvent(KeyEvent.KEY_PRESSED, t, t, keyCode, false, false, false, false));
      Event.fireEvent(target, new KeyEvent(KeyEvent.KEY_TYPED, t, t, keyCode, false, false, false, false));
      Event.fireEvent(target, new KeyEvent(KeyEvent.KEY_RELEASED, t, t, keyCode, false, false, false, false));
    });
    doDelay();
  }

  /**
   * Sleep for n milliseconds specified by {@link #delay} if NOT executing on
   * the JavaFX applicaton thread.
   */
  private static void doDelay() {
    if (delay > 0 && !Platform.isFxApplicationThread()) {
      try {
        Thread.sleep(delay);
      } catch (InterruptedException ex) {
        Logger.getLogger(FXHelper.class.getName()).log(Level.INFO, null, ex);
        Thread.currentThread().interrupt();
      }
    }
  }

  /**
   * Get the delay in ms.
   *
   * @return The delay in ms.
   */
  public static int getDelay() {
    return delay;
  }

  /**
   * Set the delay time in ms.
   *
   * @param aDelay ms
   */
  public static void setDelay(int aDelay) {
    delay = aDelay;
  }

}
