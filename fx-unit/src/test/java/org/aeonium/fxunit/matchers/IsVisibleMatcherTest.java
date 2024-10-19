/*
 * Copyright (C) 2024 Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;.
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
package org.aeonium.fxunit.matchers;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.aeonium.fxunit.DriverApp.FXUnitApp;
import org.aeonium.fxunit.FXHelper;
import static org.aeonium.fxunit.matchers.IsVisibleMatcher.DESCRIPTION;
import org.hamcrest.Description;
import org.hamcrest.StringDescription;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test cases for {@link IsVisibleMatcher}.
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class IsVisibleMatcherTest {

  Stage stage;

  @BeforeAll
  public static void setUpClass() {
    Thread t = new Thread("JavaFX Init Thread") {
      @Override
      public void run() {
        try {
          Application.launch(FXUnitApp.class, new String[0]);
        } catch (IllegalStateException ex) {
          if (!ex.getMessage().equals("Application launch must not be called more than once")) {
            throw ex;
          }
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

  @AfterAll
  public static void tearDownClass() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException ex) {
      Logger.getLogger(FXHelper.class.getName()).log(Level.INFO, null, ex);
      Thread.currentThread().interrupt();
    }
  }

  @BeforeEach
  public void setUp() {
    Platform.runLater(() -> {
      stage = new Stage();
      stage.show();
    });
  }

  @AfterEach
  public void tearDown() {
    Platform.runLater(() -> {
      if (stage != null) {
        stage.hide();
      }
    });
  }

  /**
   * Test of matchesSafely method, of class IsVisibleMatcher.
   *
   * @throws java.lang.Exception any
   */
  @Test
  public void testMatchesSafely() throws Exception {
    System.out.println("matchesSafely");
    Node node = new Label();

    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(node));
      stage.setScene(scene);
      node.setVisible(false);
      latch.countDown();

    });
    latch.await();
    IsVisibleMatcher instance = new IsVisibleMatcher();
    boolean expResult = false;
    boolean result = instance.matchesSafely(node);
    assertEquals(expResult, result);
  }

  @Test
  public void testMatchesSafely_negative() throws Exception {
    System.out.println("matchesSafely_negative");
    Node node = new Label();

    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(node));
      stage.setScene(scene);
      node.setVisible(true);
      latch.countDown();

    });
    latch.await();
    IsVisibleMatcher instance = new IsVisibleMatcher();
    boolean expResult = true;
    boolean result = instance.matchesSafely(node);
    assertEquals(expResult, result);
  }

  @Test
  @SuppressWarnings("ThrowableResultIgnored")
  public void testMatchesSafely_null_throws() throws Exception {
    System.out.println("matchesSafely_null_throws");
    Assertions.assertThrows(NullPointerException.class, () -> {
      Node node = null;
      IsVisibleMatcher instance = new IsVisibleMatcher();
      boolean expResult = false;
      boolean result = instance.matchesSafely(node);
      assertEquals(expResult, result);
    });
  }

  /**
   * Test of describeTo method, of class IsVisibleMatcher.
   */
  @Test
  public void testDescribeTo() {
    System.out.println("describeTo");
    Description d = new StringDescription();
    IsVisibleMatcher instance = new IsVisibleMatcher();
    instance.describeTo(d);

    assertTrue(d.toString().contains(DESCRIPTION));
  }

}
