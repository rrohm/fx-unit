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
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for the FXHelper class.
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class FXHelperTest {

  Stage stage;

  public FXHelperTest() {
    // no op
  }

  @BeforeClass
  public static void setUpClass() {
    Thread t = new Thread("JavaFX Init Thread") {
      @Override
      public void run() {
        try{
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
   * Test of clearText method, of class FXHelper.
   */
  @Test
  public void testClearText() {
    System.out.println("clearText");
    final String dummy_content = "dummy content";

    Labeled labeled = new Label(dummy_content);
    assertEquals(dummy_content, labeled.getText());
    FXHelper.clearText(labeled);
    assertEquals("", labeled.getText());
  }

  @Test
  public void testClearText_null() {
    System.out.println("clearText");
    final String dummy_content = null;
    Labeled labeled = new Label(dummy_content);

    assertEquals(dummy_content, labeled.getText());
    FXHelper.clearText(labeled);
    assertEquals("", labeled.getText());
  }
 
  @Test
  public void testDelay(){
    int delay = FXHelper.getDelay();
    final int delta = 100;
    FXHelper.setDelay(delay + delta);
    assertEquals(delay + delta, FXHelper.getDelay());
  }

  /**
   * Test of findTab method, of class FXHelper.
   * @throws java.lang.InterruptedException If interrupted
   */
  @Test
  public void testFindTab() throws InterruptedException {
    System.out.println("findTab");
    final String tabID1 = "tab1";
    final String tabID2 = "tab2";
    final Tab tab1 = new Tab("1st Tab");
    tab1.setId(tabID1);
    final Tab tab2 = new Tab("2nd Tab");
    tab2.setId(tabID2);
    final TabPane tabPane = new TabPane(tab1, tab2);
    
    final CountDownLatch latch = new CountDownLatch(1);
    
    Platform.runLater(() -> {
      Scene scene = new Scene(tabPane);
      stage.setScene(scene);
      latch.countDown();
    });
    
    latch.await();
    
    Tab result = FXHelper.findTab(tabPane, tabID1);
    assertEquals(tab1, result);
    result = FXHelper.findTab(tabPane, tabID2);
    assertEquals(tab2, result);
  }
  
  @Test
  public void testFindTab_nonexisting_id() throws InterruptedException {
    System.out.println("findTab");
    final String tabID1 = "tab1";
    final String tabID2 = "tab2";
    final Tab tab1 = new Tab("1st Tab");
    tab1.setId(tabID1);
    final Tab tab2 = new Tab("2nd Tab");
    tab2.setId(tabID2);
    final TabPane tabPane = new TabPane(tab1, tab2);
    
    final CountDownLatch latch = new CountDownLatch(1);
    
    Platform.runLater(() -> {
      Scene scene = new Scene(tabPane);
      stage.setScene(scene);
      latch.countDown();
    });
    
    latch.await();
    Tab result = FXHelper.findTab(tabPane, tabID1);
    assertEquals(tab1, result);
    result = FXHelper.findTab(tabPane, "wrong ID");
    assertNull(result);
  }

  /**
   * Test of focus method, of class FXHelper.
   *
   * @throws java.lang.InterruptedException
   */
  @Test
  public void testFocus() throws InterruptedException {
    System.out.println("focus");

    final Node node = new Button("button");
    final Node node2 = new Button("other");

    final CountDownLatch latch = new CountDownLatch(1);
    
    Platform.runLater(() -> {
      final VBox vBox = new VBox(node, node2);
      Scene scene = new Scene(vBox);
      stage.setScene(scene);

      node2.requestFocus();
      latch.countDown();
    });

    latch.await();
    Thread.sleep(100);
    
    assertEquals(true, node2.isFocused());
    assertEquals(false, node.isFocused());
    
    FXHelper.focus(node);
    assertEquals(true, node.isFocused());
    assertEquals(false, node2.isFocused());
  }

  /**
   * Test of invokeOnFXThread method, of class FXHelper.
   * @throws java.lang.Exception any
   */
  @Test
  public void testInvokeOnFXThread() throws Exception{
    System.out.println("invokeOnFXThread");
    BooleanProperty done = new SimpleBooleanProperty(false);
    final CountDownLatch latch = new CountDownLatch(1);
    
    FXHelper.invokeOnFXThread(() -> {
      done.set(true);
      latch.countDown();
    });
    
    latch.await();
    assertTrue(done.get());
  }
  
  @Test
  public void testInvokeOnFXThread_onFXThread() throws Exception{
    System.out.println("invokeOnFXThread");
    BooleanProperty done = new SimpleBooleanProperty(false);
    final CountDownLatch latch = new CountDownLatch(1);
    
    Platform.runLater(() -> {
      FXHelper.invokeOnFXThread(() -> {
        done.set(true);
        latch.countDown();
      });
    });
    
    latch.await();
    assertTrue(done.get());
  }
  
  @Test(expected = NullPointerException.class)
  public void testInvokeOnFXThread_throws_when_null_input() {
    System.out.println("invokeOnFXThread");
    Runnable runnable = null;
    FXHelper.invokeOnFXThread(runnable);
  }

  /**
   * Test of runAndWait method, of class FXHelper.
   *
   * @throws java.lang.Exception any
   */
  @Test(expected = NullPointerException.class)
  public void testRunAndWait_throws_when_null_input() throws Exception {
    System.out.println("runAndWait");
    Runnable runnable = null;
    FXHelper.runAndWait(runnable);
  }

  @Test
  public void testRunAndWait_notOnFXThread() throws Exception {
    System.out.println("runAndWait");
    BooleanProperty done = new SimpleBooleanProperty(false);
    final CountDownLatch latch = new CountDownLatch(1);
    
    FXHelper.runAndWait(() -> {
      done.set(true);
      latch.countDown();
    });
    
    latch.await();
    assertTrue(done.get());
  }

  @Test
  public void testRunAnd_onFXThread() throws Exception {
    System.out.println("runAndWait");
    BooleanProperty done = new SimpleBooleanProperty(false);
    final CountDownLatch latch = new CountDownLatch(1);
    
    Platform.runLater(() -> {
      try {
        FXHelper.runAndWait(() -> {
          done.set(true);
          latch.countDown();
        });
      } catch (ExecutionException ex) {
        Logger.getLogger(FXHelperTest.class.getName()).log(Level.SEVERE, null, ex);
        assertFalse("Exception thrown: " + ex.getMessage(), true);
      }
    });
    
    latch.await();
    assertTrue(done.get());
  }

  /**
   * Test of selectTab method, of class FXHelper.
   * @throws java.lang.InterruptedException If interrupted
   */
  @Test
  public void testSelectTab() throws InterruptedException {
    System.out.println("selectTab");
    System.out.println("findTab");
    final String tabID1 = "tab1";
    final String tabID2 = "tab2";
    final Tab tab1 = new Tab("1st Tab");
    tab1.setId(tabID1);
    final Tab tab2 = new Tab("2nd Tab");
    tab2.setId(tabID2);
    final TabPane tabPane = new TabPane(tab1, tab2);
    
    final CountDownLatch latch = new CountDownLatch(1);
    
    Platform.runLater(() -> {
      Scene scene = new Scene(tabPane);
      stage.setScene(scene);
      latch.countDown();
    });
    
    latch.await();
    assertEquals(true, tab1.isSelected());
    assertEquals(false, tab2.isSelected());
    
    FXHelper.selectTab(tabPane, tabID1);
    assertEquals(true, tab1.isSelected());
    assertEquals(false, tab2.isSelected());
    
    FXHelper.selectTab(tabPane, tabID2);
    assertEquals(false, tab1.isSelected());
    assertEquals(true, tab2.isSelected());
  }
  
  @Test(expected = NullPointerException.class)
  public void testSelectTab_nonexisting_id() throws InterruptedException {
    System.out.println("selectTab");
    System.out.println("findTab");
    final String tabID1 = "tab1";
    final String tabID2 = "tab2";
    final Tab tab1 = new Tab("1st Tab");
    tab1.setId(tabID1);
    final Tab tab2 = new Tab("2nd Tab");
    tab2.setId(tabID2);
    final TabPane tabPane = new TabPane(tab1, tab2);
    
    final CountDownLatch latch = new CountDownLatch(1);
    
    Platform.runLater(() -> {
      Scene scene = new Scene(tabPane);
      stage.setScene(scene);
      latch.countDown();
    });
    
    latch.await();
    assertEquals(true, tab1.isSelected());
    assertEquals(false, tab2.isSelected());
    
    FXHelper.selectTab(tabPane, tabID2);
    assertEquals(false, tab1.isSelected());
    assertEquals(true, tab2.isSelected());
    
    FXHelper.selectTab(tabPane, "bla");
    assertEquals(false, tab1.isSelected());
    assertEquals(true, tab2.isSelected());
  }

  /**
   * Test of shutdownStage method, of class FXHelper.
   */
  @Test
  public void testShutdownStage_null() {
    System.out.println("shutdownStage null");
    Stage testStage = null;
    FXHelper.shutdownStage(testStage);

    assertTrue("Null input gets ignored.", true);
  }
  
  @Test
  public void testShutdownStage() throws Exception {
    System.out.println("shutdownStage");
    final CountDownLatch latch = new CountDownLatch(1);
    Platform.runLater(() -> {
      stage = new Stage();
      stage.setScene(new Scene(new VBox(new Label("Shutdown stage ..."))));
      latch.countDown();
    });
    latch.await();
    FXHelper.shutdownStage(stage);

    Thread.sleep(500);
    assertFalse(stage.isShowing());
    assertNull(stage.getScene());
  }

  /**
   * Test of typeKey method, of class FXHelper.
   *
   * @throws java.lang.InterruptedException If interrupted
   */
  @Test
  public void testTypeKey_Node_String() throws InterruptedException {
    System.out.println("typeKey");
    TextField target = new TextField();

    final CountDownLatch latch = new CountDownLatch(1);
    
    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(target));
      stage.setScene(scene);
      latch.countDown();
    });
    
    latch.await();
    assertEquals("", target.getText());
    
    String t = "input";
    FXHelper.typeKey(target, t);
    assertEquals(t, target.getText());
  }

  /**
   * Test of typeKey method, of class FXHelper.
   *
   * @throws java.lang.InterruptedException If interrupted
   */
  @Test
  public void testTypeKey_Node_Character() throws InterruptedException {
    System.out.println("typeKey");

    TextField target = new TextField();

    final CountDownLatch latch = new CountDownLatch(1);
    
    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(target));
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    assertEquals("", target.getText());
    
    Character c = 'O';
    FXHelper.typeKey(target, c);
    assertEquals(String.valueOf(c), target.getText());
  }
  
  @Test(expected = FXUnitException.class)
  public void testTypeKey_Node_KeyCode_Null() throws InterruptedException {
    System.out.println("typeKey keycode null");

    TextField target = new TextField();

    final CountDownLatch latch = new CountDownLatch(1);
    
    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(target));
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    assertEquals("", target.getText());
    
    String t = "O";
    KeyCode nullCode = null;
    FXHelper.typeKey(target, nullCode);
    assertEquals(t, target.getText());
  }

  @Test
  public void testTypeKey_Node_KeyCode_CharUpperCase() throws InterruptedException {
    System.out.println("typeKey keycode upperCase");

    TextField target = new TextField();

    final CountDownLatch latch = new CountDownLatch(1);
    
    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(target));
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    assertEquals("", target.getText());
    
    String t = "O";
    FXHelper.typeKey(target, KeyCode.O);
    assertEquals(t, target.getText());
  }
  
  @Test
  public void testTypeKey_Node_KeyCode_NonChar() throws InterruptedException {
    System.out.println("typeKey keycode nonchar");

    BooleanProperty enterTyped = new SimpleBooleanProperty(false);
    TextField target = new TextField();
    target.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
      if (event.getCode().equals(KeyCode.ENTER)) {
        enterTyped.set(true);
      }
    });

    final CountDownLatch latch = new CountDownLatch(1);
    
    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(target));
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    final String emptyString = "";
    assertEquals(emptyString, target.getText());
    
    FXHelper.typeKey(target, KeyCode.ENTER);
    assertEquals(emptyString, target.getText());
    assertTrue(enterTyped.get());
  }

}
