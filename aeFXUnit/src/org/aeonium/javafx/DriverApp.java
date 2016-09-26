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
 *  2016 Aeonium Software Systems, Robert Rohm.
 */
package org.aeonium.javafx;

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
