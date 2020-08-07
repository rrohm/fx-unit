/*
 * Copyright (C) 2020 Aeonium Software Systems.
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

import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.aeonium.fxunit.DriverApp.FXUnitApp;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test cases for {@link FXCollection}.
 *
 * @author robert rohm
 */
public class FXCollectionTest {

  Stage stage;

  public FXCollectionTest() {
    // no op
  }

  @BeforeAll
  public static void setUpClass() {
    System.err.println(0);
    Thread t = new Thread("JavaFX Init Thread") {
      @Override
      public void run() {
        System.err.println(1);
        try {
          Application.launch(FXUnitApp.class, new String[0]);
          System.err.println(2);
        } catch (IllegalStateException ex) {
          if (!ex.getMessage().equals("Application launch must not be called more than once")) {
            throw ex;
          }
        }
      }
    };
    System.err.println(3);
    t.setDaemon(true);
    t.start();
    System.err.println(4);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException ex) {
      Logger.getLogger(FXHelper.class.getName()).log(Level.INFO, null, ex);
      Thread.currentThread().interrupt();
    }
    System.err.println(5);
  }

  @AfterAll
  public static void tearDownClass() {
  }

  @BeforeEach
  public void setUp() throws InterruptedException {
    final CountDownLatch latch = new CountDownLatch(1);
    Platform.runLater(() -> {
      stage = new Stage();
      stage.show();
      latch.countDown();
    });
    latch.await();
  }

  @AfterEach
  public void tearDown() throws InterruptedException {
    final CountDownLatch latch = new CountDownLatch(1);
    Platform.runLater(() -> {
      if (stage != null) {
        stage.hide();
        latch.countDown();
      }
    });
    latch.await();
  }

  /**
   * Test of get method, of class FXCollection.
   */
  @Test
  public void testGet_negative_null_nodes() {
    System.out.println("get");
    Assertions.assertThrows(NullPointerException.class, () -> {
      int index = 0;
      FXCollection instance = new FXCollection(null);
      FX fx = instance.get(index);
      Assertions.assertNotNull(fx);
    });
  }

  @Test
  public void testGet_indexError() {
    System.out.println("get");
    Assertions.assertThrows(AssertionError.class, () -> {
      int index = 0;
      ObservableList<Node> nodes = FXCollections.observableArrayList();
      FXCollection instance = new FXCollection(nodes);
      FX fx = instance.get(index);
      Assertions.assertNotNull(fx);
    });
  }

  @Test
  public void testGet() {
    System.out.println("get");
    int index = 0;
    Label label1 = new Label("one");
    Label label2 = new Label("two");
    ObservableList<Node> nodes = FXCollections.observableArrayList(label1, label2);
    FXCollection instance = new FXCollection(nodes);
    FX fx = instance.get(index);
    Assertions.assertNotNull(fx);
    Assertions.assertSame(label1, fx.getNode());
  }

  /**
   * Test of hasChildren method, of class FXCollection.
   */
  @Test
  public void testHasChildren_negative_null_nodes() {
    System.out.println("hasChildren");
    Assertions.assertThrows(NullPointerException.class, () -> {
      int count = 0;
      FXCollection instance = new FXCollection(null);
      FXCollection fXCollection = instance.hasChildren(count);
      Assertions.assertNotNull(fXCollection);
    });
  }

  /**
   * Test of isEmpty method, of class FXCollection.
   */
  @Test
  public void testIsEmpty_negative_null_nodes() {
    System.out.println("isEmpty");
    Assertions.assertThrows(NullPointerException.class, () -> {
      FXCollection instance = new FXCollection(null);
      FXCollection fXCollection = instance.isEmpty();
      Assertions.assertNotNull(fXCollection);
    });
  }

}
