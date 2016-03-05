/*
 *  This code is released under Creative Commons Attribution 4.0 International
 *  (CC BY 4.0) license, http://creativecommons.org/licenses/by/4.0/legalcode .
 *  That means:
 * 
 *  You are free to:
 * 
 *      Share — copy and redistribute the material in any medium or format
 *      Adapt — remix, transform, and build upon the material
 *               for any purpose, even commercially.
 * 
 *      The licensor cannot revoke these freedoms as long as you follow the
 *      license terms.
 * 
 *  Under the following terms:
 * 
 *      Attribution — You must give appropriate credit, provide a link to the
 *      license, and indicate if changes were made. You may do so in any
 *      reasonable manner, but not in any way that suggests the licensor endorses
 *      you or your use.
 * 
 *  No additional restrictions — You may not apply legal terms or technological
 *  measures that legally restrict others from doing anything the license
 *  permits.
 * 
 *
 *  2015 Aeonium Software Systems, Robert Rohm.
 */
package org.aeonium.javafx;

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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public final class FXHelper {
  
  public static int DELAY = 200;
  
  public static void clearText(Labeled labeled) {
    invokeOnFXThread(() -> {
      labeled.setText("");
    });
    doDelay();
  }

  /**
   * Find a tab sheet by ID in the given tab pane.
   *
   * @param tabPane
   * @param id
   * @return
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
   * If the current thread is the JavaFX Application thread, run the code of the
   * given runnable on the current thread, i.e., invoke it's <code>run()</code>
   * method directly, otherwise put it on the JavaFX Application thread and wait
   * for completion of this task.
   *
   * @param runnable
   * @throws InterruptedException
   * @throws ExecutionException
   */
  public static void runAndWait(Runnable runnable) throws InterruptedException, ExecutionException {
    if (Platform.isFxApplicationThread()) {
      runnable.run();
    } else {
      FutureTask future = new FutureTask(runnable, null);
      Platform.runLater(future);
      future.get();
    }
  }

  public static void selectTab(TabPane tabPane, String id) {
    Tab findTab = findTab(tabPane, id);
    if (findTab == null) {
      throw new NullPointerException("Tab not found for ID " + id);
    }
    tabPane.getSelectionModel().select(findTab);
    doDelay();
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
      throw new RuntimeException("No keycode found for " + c);
    }

    invokeOnFXThread(() -> {
      Event.fireEvent(target, new KeyEvent(KeyEvent.KEY_PRESSED, t, t, keyCode, false, false, false, false));
      Event.fireEvent(target, new KeyEvent(KeyEvent.KEY_TYPED, t, t, keyCode, false, false, false, false));
      Event.fireEvent(target, new KeyEvent(KeyEvent.KEY_RELEASED, t, t, keyCode, false, false, false, false));
    });
    doDelay();
  }

  public static void invokeOnFXThread(final Runnable runnable) {
    if (Platform.isFxApplicationThread()) {
      runnable.run();
    } else {
      Platform.runLater(runnable);
    }
  }

  private static void doDelay() {
    if (DELAY > 0) {
      if (!Platform.isFxApplicationThread()) {
        try {
          Thread.sleep(DELAY);
        } catch (InterruptedException ex) {
          Logger.getLogger(FXHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
  }
}
