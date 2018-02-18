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

import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.aeonium.fxunit.i18n.I18N;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Unit tests for the AssertFX class.
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class AssertFXTest {

  Stage stage;

  public AssertFXTest() {
  }

  @Rule
  public final ExpectedException expectedExceptionRule = ExpectedException.none();

  @BeforeClass
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

  @AfterClass
  public static void tearDownClass() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException ex) {
      Logger.getLogger(FXHelper.class.getName()).log(Level.INFO, null, ex);
      Thread.currentThread().interrupt();
    }
  }

  @Before
  public void setUp() {
    Platform.runLater(() -> {
      stage = new Stage();
      stage.show();
    });
  }

  @After
  public void tearDown() {
    Platform.runLater(() -> {
      if (stage != null) {
        stage.hide();
      }
    });
  }

  /**
   * Test of assertFocused method, of class AssertFX.
   *
   * @throws Exception any
   */
  @Test
  public void testAssertFocused() throws Exception {
    System.out.println("assertFocused");
    Node node = new Button("button");
    node.requestFocus();

    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(new Button("other 1"), node, new Button("other 2")));
      stage.setScene(scene);
      node.requestFocus();
      latch.countDown();
    });

    Thread.sleep(200);
    latch.await();
    AssertFX.assertFocused(node);
  }

  @Test
  public void testAssertFocused_negative_null_node() throws Exception {
    System.out.println("assertFocused");
    Node node = null;

    expectedExceptionRule.expectMessage(I18N.getString(I18N.NODE_IS_NULL));
    AssertFX.assertFocused(node);
  }

  @Test(expected = AssertionError.class)
  public void testAssertFocused_negative() throws Exception {
    System.out.println("assertFocused");
    Node node = new Button("button");
    node.requestFocus();

    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      final Button node2 = new Button("other 2");
      Scene scene = new Scene(new VBox(new Button("other 1"), node, node2));
      stage.setScene(scene);
      node2.requestFocus();
      latch.countDown();
    });

    Thread.sleep(200);
    latch.await();
    AssertFX.assertFocused(node);
  }

  /**
   * Test of assertNotShowing method, of class AssertFX.
   *
   * @throws java.lang.Exception any
   */
  @Test
  public void testAssertNotShowing() throws Exception {
    System.out.println("assertNotShowing");

    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      stage.hide();
      latch.countDown();
    });

    latch.await();

    AssertFX.assertNotShowing(stage);
  }

  @Test(expected = AssertionError.class)
  public void testAssertNotShowing_negative() throws Exception {
    System.out.println("assertNotShowing");

    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      stage.show();
      latch.countDown();
    });

    latch.await();

    AssertFX.assertNotShowing(stage);
  }

  @Test
  public void testAssertNotShowing_negative_null_window() throws Exception {
    System.out.println("assertNotShowing");
    expectedExceptionRule.expectMessage(I18N.getString(I18N.WINDOW_IS_NULL));
    AssertFX.assertNotShowing(null);
  }

  /**
   * Test of assertNotVisible method, of class AssertFX.
   *
   * @throws java.lang.Exception any
   */
  @Test
  public void testAssertNotVisible() throws Exception {
    System.out.println("assertNotVisible");
    Node node = new Button("button");
    node.requestFocus();

    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(new Button("other 1"), node, new Button("other 2")));
      stage.setScene(scene);
      node.setVisible(false);
      latch.countDown();
    });

    latch.await();
    AssertFX.assertNotVisible(node);
  }

  @Test(expected = AssertionError.class)
  public void testAssertNotVisible_negative() throws Exception {
    System.out.println("assertNotVisible");
    Node node = new Button("button");
    node.requestFocus();

    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(new Button("other 1"), node, new Button("other 2")));
      stage.setScene(scene);
      node.setVisible(true);
      latch.countDown();
    });

    latch.await();
    AssertFX.assertNotVisible(node);
  }

  @Test
  public void testAssertNotVisible_negative_null_node() throws Exception {
    System.out.println("assertNotVisible");
    Node node = null;
    expectedExceptionRule.expectMessage(I18N.getString(I18N.NODE_IS_NULL));
    AssertFX.assertNotVisible(node);
  }

  /**
   * Test of assertSelected method, of class AssertFX.
   *
   * @throws java.lang.Exception any
   */
  @Test
  public void testAssertSelected() throws Exception {
    System.out.println("assertSelected");
    TabPane tabPane = new TabPane();
    Tab tab1 = new Tab("First");
    tab1.setId("first");
    Tab tab2 = new Tab("Second");
    tab2.setId("second");
    Tab tab3 = new Tab("Second");
    tab3.setId("third");

    String id = "second";
    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(tabPane);
      tabPane.getTabs().addAll(tab1, tab2, tab3);
      tabPane.getSelectionModel().select(tab2);
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    AssertFX.assertSelected(tabPane, id);
  }

  @Test(expected = AssertionError.class)
  public void testAssertSelected_negative() throws Exception {
    System.out.println("assertSelected");
    TabPane tabPane = new TabPane();
    Tab tab1 = new Tab("First");
    tab1.setId("first");
    Tab tab2 = new Tab("Second");
    tab2.setId("second");
    Tab tab3 = new Tab("Second");
    tab3.setId("third");

    String id = "first";
    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(tabPane);
      tabPane.getTabs().addAll(tab1, tab2, tab3);
      tabPane.getSelectionModel().select(tab2);
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    AssertFX.assertSelected(tabPane, id);
  }

  @Test
  public void testAssertSelected_negative_tabPane_null() throws Exception {
    System.out.println("assertSelected");
    TabPane tabPane = null;

    expectedExceptionRule.expectMessage(I18N.getString(I18N.TABPANE_IS_NULL));
    AssertFX.assertSelected(tabPane, "something");
  }

  @Test
  public void testAssertSelected_negative_id_null() throws Exception {
    System.out.println("assertSelected");
    TabPane tabPane = new TabPane();
    Tab tab1 = new Tab("First");
    tab1.setId("first");
    Tab tab2 = new Tab("Second");
    tab2.setId("second");
    Tab tab3 = new Tab("Second");
    tab3.setId("third");

    String id = "second";
    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(tabPane);
      tabPane.getTabs().addAll(tab1, tab2, tab3);
      tabPane.getSelectionModel().select(tab2);
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    expectedExceptionRule.expectMessage(I18N.getString(I18N.ID_IS_NULL));
    AssertFX.assertSelected(tabPane, null);
  }

  @Test(expected = AssertionError.class)
  public void testAssertSelected_negative_id_nonexistent() throws Exception {
    System.out.println("assertSelected");
    TabPane tabPane = new TabPane();
    Tab tab1 = new Tab("First");
    tab1.setId("first");
    Tab tab2 = new Tab("Second");
    tab2.setId("second");
    Tab tab3 = new Tab("Second");
    tab3.setId("third");

    String id = "dows not exist";
    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(tabPane);
      tabPane.getTabs().addAll(tab1, tab2, tab3);
      tabPane.getSelectionModel().select(tab2);
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    AssertFX.assertSelected(tabPane, id);
  }

  @Test(expected = AssertionError.class)
  public void testAssertSelected_negative_null_selection() throws Exception {
    System.out.println("assertSelected");
    TabPane tabPane = new TabPane();

    String id = "first";
    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(tabPane);
      tabPane.getSelectionModel().clearSelection();
      stage.setScene(scene);
      latch.countDown();
    });
    
    latch.await();
    AssertFX.assertSelected(tabPane, id);
  }

  /**
   * Test of assertShowing method, of class AssertFX.
   *
   * @throws java.lang.Exception any
   */
  @Test
  public void testAssertShowing() throws Exception {
    System.out.println("assertShowing");

    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      stage.show();
      latch.countDown();
    });

    latch.await();
    AssertFX.assertShowing(stage);
  }

  @Test(expected = AssertionError.class)
  public void testAssertShowing_negative() throws Exception {
    System.out.println("assertShowing");

    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      stage.hide();
      latch.countDown();
    });

    latch.await();
    AssertFX.assertShowing(stage);
  }

  @Test
  public void testAssertShowing_negative_null_window() throws Exception {
    System.out.println("assertNotShowing");
    expectedExceptionRule.expectMessage(I18N.getString(I18N.WINDOW_IS_NULL));
    AssertFX.assertShowing(null);
  }

  /**
   * Test of assertVisible method, of class AssertFX.
   *
   * @throws java.lang.Exception any
   */
  @Test
  public void testAssertVisible() throws Exception {
    System.out.println("assertVisible");
    Node node = new Button("button");
    node.requestFocus();

    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(new Button("other 1"), node, new Button("other 2")));
      stage.setScene(scene);
      node.setVisible(true);
      latch.countDown();
    });

    latch.await();
    AssertFX.assertVisible(node);
  }

  @Test(expected = AssertionError.class)
  public void testAssertVisible_negative() throws Exception {
    System.out.println("assertVisible");
    Node node = new Button("button");
    node.requestFocus();

    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(new Button("other 1"), node, new Button("other 2")));
      stage.setScene(scene);
      node.setVisible(false);
      latch.countDown();
    });

    latch.await();
    AssertFX.assertVisible(node);
  }

  @Test
  public void testAssertVisible_negative_null_node() throws Exception {
    System.out.println("assertVisible");
    Node node = null;
    expectedExceptionRule.expectMessage(I18N.getString(I18N.NODE_IS_NULL));
    AssertFX.assertVisible(node);
  }

}
