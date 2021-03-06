
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
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.aeonium.fxunit.DriverApp.FXUnitApp;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Test cases for {@link FX}.
 *
 * @author robert rohm
 */
public class FXTest {

  protected static final String SOME_ID = "some_ID";
  Stage stage;

  public FXTest() {
  }

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
   * Test of lookup method, of class FX.
   */
  @Test
  public void testLookup_negtive_nullStage_String() {
    System.out.println("lookup");
    Assertions.assertThrows(NullPointerException.class, () -> {
      String id = SOME_ID;
      FX.lookup(id);
    });
  }

  /**
   * Test of lookup method, of class FX.
   */
  @Test
  public void testLookup_negative_Stage_null_String() {
    System.out.println("lookup");
    Assertions.assertThrows(NullPointerException.class, () -> {
      Stage stage = null;
      String id = SOME_ID;
      FX.lookup(stage, id);
    });
  }

  /**
   * Test of children method, of class FX.
   */
  @Test
  @Disabled("TODO")
  public void testChildren_negative_nullNode() {
    System.out.println("children");
    FX instance = null;
    FXCollection expResult = null;
    FXCollection result = instance.children();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of hasChildren method, of class FX.
   *
   * @throws Exception any
   */
  @Test
  public void testHasChildren() throws Exception {
    System.out.println("hasChildren");
    int count = 1;

    final Node node = new Button("button");
    final CountDownLatch latch = new CountDownLatch(1);
    final VBox vBox = new VBox(node);
    vBox.setId("SUT");

    Platform.runLater(() -> {
      Scene scene = new Scene(vBox);
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    Thread.sleep(300);

    FX instance = FX.lookup(stage, "#SUT");
    assertNotNull(instance);
    FX result = instance.hasChildren(count);
  }

  /**
   * Test of hasText method, of class FX.
   *
   * @throws java.lang.Exception any
   */
  @Test
  public void testHasText() throws Exception {
    System.out.println("hasText");
    String text = "button";

    final Node node = new Button("button");
    final CountDownLatch latch = new CountDownLatch(1);
    final VBox vBox = new VBox(node);
    node.setId("SUT");

    Platform.runLater(() -> {
      Scene scene = new Scene(vBox);
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    Thread.sleep(300);

    FX instance = FX.lookup(stage, "#SUT");
    instance.hasText("button");
  }

  /**
   * Test of hasTooltipText method, of class FX.
   */
  @Test
  @Disabled("TODO")
  public void testHasTooltipText() {
    System.out.println("hasTooltipText");
    String text = "";
    FX instance = null;
    FX expResult = null;
    FX result = instance.hasTooltipText(text);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of isEmpty method, of class FX.
   */
  @Test
  @Disabled("TODO")
  public void testIsEmpty() {
    System.out.println("isEmpty");
    FX instance = null;
    FX expResult = null;
    FX result = instance.isEmpty();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of isNotManaged method, of class FX.
   *
   * @throws java.lang.Exception any
   */
  @Test
  public void testIsNotManaged() throws Exception {
    System.out.println("isNotManaged");
    Node node = new Button("button");
    node.setManaged(false);
    node.setId("button");

    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(node));
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    FX instance = FX.lookup(stage, "#button");
    FX result = instance.isNotManaged();
    assertSame(instance, result);
  }

  /**
   * Test of isNotSelected method, of class FX.
   */
  @Test
  @Disabled("TODO")
  public void testIsNotSelected() {
    System.out.println("isNotSelected");
    FX instance = null;
    FX expResult = null;
    FX result = instance.isNotSelected();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of isNotVisible method, of class FX.
   *
   * @throws java.lang.Exception any
   */
  @Test
  public void testIsNotVisible() throws Exception {
    System.out.println("isNotVisible");
    Node node = new Button("button");
    node.setVisible(false);
    node.setId("button");

    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(node));
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    FX instance = FX.lookup(stage, "#button");
    FX result = instance.isNotVisible();
    assertSame(instance, result);
  }

  /**
   * Test of isSelected method, of class FX.
   *
   * @throws java.lang.Exception Any
   */
  @Test
  public void testIsSelected() throws Exception {
    System.out.println("isSelected");
    FlowPane tb = new FlowPane(Orientation.VERTICAL);
    ToggleButton tb1 = new ToggleButton("First");
    tb1.setId("first");
    ToggleButton tb2 = new ToggleButton("Second");
    tb2.setId("second");
    ToggleButton tb3 = new ToggleButton("Second");
    tb3.setId("third");

    String id = "second";
    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(tb);
      tb.getChildren().addAll(tb1, tb2, tb3);
      tb1.fire();
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    FX instance = FX.lookup(stage, "#first");
    FX result = instance.isSelected();
    assertSame(instance, result);
  }

  @Test
  public void testIsSelected_notToggle_throws() throws Exception {
    System.out.println("isSelected");
    Assertions.assertThrows(AssertionError.class, () -> {
      FlowPane tb = new FlowPane(Orientation.VERTICAL);
      tb.setId("aPane");
      ToggleButton tb1 = new ToggleButton("First");
      tb1.setId("first");
      ToggleButton tb2 = new ToggleButton("Second");
      tb2.setId("second");
      ToggleButton tb3 = new ToggleButton("Second");
      tb3.setId("third");

      String id = "second";
      final CountDownLatch latch = new CountDownLatch(1);

      Platform.runLater(() -> {
        Scene scene = new Scene(tb);
        tb.getChildren().addAll(tb1, tb2, tb3);
        tb1.fire();
        stage.setScene(scene);
        latch.countDown();
      });

      latch.await();
      FX instance = FX.lookup(stage, "#aPane");
      FX result = instance.isSelected();
      assertSame(instance, result);
    });
  }

  /**
   * Test of isManaged method, of class FX.
   *
   * @throws java.lang.Exception any
   */
  @Test
  public void testIsManaged() throws Exception {
    System.out.println("isManaged");
    Node node = new Button("button");
    node.setManaged(true);
    node.setId("button");

    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(node));
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    FX instance = FX.lookup(stage, "#button");
    FX result = instance.isManaged();
    assertSame(instance, result);
  }

  /**
   * Test of isVisible method, of class FX.
   *
   * @throws java.lang.Exception any
   */
  @Test
  public void testIsVisible() throws Exception {
    System.out.println("isVisible");
    Node node = new Button("button");
    node.setVisible(true);
    node.setId("button");

    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(node));
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    FX instance = FX.lookup(stage, "#button");
    FX result = instance.isVisible();
    assertSame(instance, result);
  }

  /**
   * Test of getContextMenu method, of class FX.
   *
   * @throws java.lang.Exception any
   */
  @Test
  public void testGetContextMenu() throws Exception {
    System.out.println("getContextMenu");
    final Control node = new Button("button");
    final CountDownLatch latch = new CountDownLatch(1);
    final VBox vBox = new VBox(node);
    node.setId("SUT");
    ContextMenu contextMenu = new ContextMenu(new MenuItem("Erstens"), new MenuItem("Zweitens"));
    node.setContextMenu(contextMenu);

    Platform.runLater(() -> {
      Scene scene = new Scene(vBox);
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    Thread.sleep(300);

    FX instance = FX.lookup(stage, "#SUT");
    FXMenu contextMenuFX = instance.getContextMenu();
    assertNotEquals(instance, contextMenuFX);
    Thread.sleep(100);
  }

  @Test
  public void testHasMenuItem() throws Exception {
    System.out.println("hasMenuItem");
    final Control node = new Button("button");
    final CountDownLatch latch = new CountDownLatch(1);
    final VBox vBox = new VBox(node);
    node.setId("SUT");
    final MenuItem menuItem2 = new MenuItem("Zweitens");
    menuItem2.setId("item2");
    ContextMenu contextMenu = new ContextMenu(new MenuItem("Erstens"), menuItem2);
    node.setContextMenu(contextMenu);

    Platform.runLater(() -> {
      Scene scene = new Scene(vBox);
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    Thread.sleep(300);

    FX instance = FX.lookup(stage, "#SUT");
    FXMenu contextMenuFX = instance.getContextMenu();
    assertNotEquals(instance, contextMenuFX);
    contextMenuFX.hasMenuItem("#item2");
    Thread.sleep(100);
  }

  @Test
  public void testGetContextMenu_notControl_throws() throws Exception {
    System.out.println("getContextMenu");
    Assertions.assertThrows(AssertionError.class, () -> {
      final Control node = new Button("button");
      final CountDownLatch latch = new CountDownLatch(1);
      final VBox vBox = new VBox(node);
      vBox.setId("SUT");
      ContextMenu contextMenu = new ContextMenu(new MenuItem("Erstens"), new MenuItem("Zweitens"));
      node.setContextMenu(contextMenu);

      Platform.runLater(() -> {
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        latch.countDown();
      });

      latch.await();
      Thread.sleep(300);

      FX instance = FX.lookup(stage, "#SUT");
      FXMenu contextMenuFX = instance.getContextMenu();
      assertNotEquals(instance, contextMenuFX);
      Thread.sleep(100);
    });
  }

  /**
   * Test of getNode method, of class FX.
   */
  @Test
  @Disabled("TODO")
  public void testGetNode() {
    System.out.println("getNode");
    FX instance = null;
    Node expResult = null;
    Node result = instance.getNode();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of hasValue method, of class FX.
   */
  @Test
  @Disabled("TODO")
  public void testHasValue() {
    System.out.println("hasValue");
    Object value = null;
    FX instance = null;
    FX expResult = null;
    FX result = instance.hasValue(value);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of setValue method, of class FX.
   */
  @Test
  @Disabled("TODO")
  public void testSetValue() {
    System.out.println("setValue");
    Object value = null;
    FX instance = null;
    FX expResult = null;
    FX result = instance.setValue(value);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

}
