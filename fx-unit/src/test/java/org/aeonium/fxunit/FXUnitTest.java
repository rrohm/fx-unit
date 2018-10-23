/*
 * Copyright (C) 2018 Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;.
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
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.aeonium.fxunit.testUI.FXMLController;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test for the {@link FXUnit} class, must init the FX framework only once,
 * hence tests for {@link FXUnit#init(javafx.application.Application) } have
 * been mived to {@link FXUnitInitApplicationTest}.
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class FXUnitTest {

  private boolean isToolkitInitialized = false;

  public FXUnitTest() {
  }

  @BeforeClass
  public static void setUpClass() {
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  /**
   * Test of init method, of class FXUnit: the framework must allow usage of the
   * FX framework, e.g., showing a new stage.
   */
  @Test
  public void testInit() {
    System.out.println("init");
    FXUnit.init();

    Platform.runLater(() -> {
      Stage testStage = new Stage();
      testStage.setScene(new Scene(new VBox(new Label("Init OK."))));
      testStage.show();
      testStage.hide();
    });
  }

  @Test
  public void testLoad() {
    System.out.println("load");
    initializeToolkit();

    FXUnit.load(FXMLController.class.getResource("FXML.fxml"));
    assertNotNull(FXUnit.getController());
    assertNotNull(FXUnit.getRoot());
    assertNull(FXUnit.getStage());
  }

  /**
   * {@link FXUnit#show(java.net.URL)} loads an FXML file and initializes the
   * controller, the stage and the root node.
   */
  @Test
  public void testShow() {
    System.out.println("show");
    initializeToolkit();

    FXUnit.show(FXMLController.class.getResource("FXML.fxml"));
    assertNotNull(FXUnit.getController());
    assertNotNull(FXUnit.getStage());
    assertNotNull(FXUnit.getRoot());
  }

  private void initializeToolkit() {
    if (!isToolkitInitialized) {
      Thread t = new Thread("FXUnit Init Thread") {
        @Override
        public void run() {
          isToolkitInitialized = true;
          Application.launch(FXUnitApp.class, new String[0]);
        }
      };
      t.setDaemon(true);
      t.start();
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ex) {
        Logger.getLogger(FXUnitTest.class.getName()).log(Level.INFO, null, ex);
        Thread.currentThread().interrupt();
      }
    }
  }

  /**
   * Test of shutdown method, of class FXUnit, currently only adding delay time.
   */
  @Test
  public void testShutdown() {
    System.out.println("shutdown");
    long timeBefore = System.currentTimeMillis();
    FXUnit.shutdown();
    long timeAfter = System.currentTimeMillis();

    assertTrue("Delay time >= 1000 ms", timeAfter >= timeBefore + 1000);
  }
}
