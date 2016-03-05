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

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;

/**
 * Main class of the framework, provides only the static initializer method.
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class FXUnit {

  /**
   * Initialize the JavaFX application framework - necessary for testing JavaFX
   * GUIs.
   */
  public static void init() {
    Thread t = new Thread("JavaFX Init Thread") {
      @Override
      public void run() {
        Application.launch(MyDummyApp.class, new String[0]);
      }
    };
    t.setDaemon(true);
    t.start();
    try {
      Thread.sleep(1000);
    } catch (InterruptedException ex) {
      Logger.getLogger(AssertFX.class.getName()).log(Level.SEVERE, null, ex);
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
      Thread.currentThread().interrupt();
    }
  }

}
