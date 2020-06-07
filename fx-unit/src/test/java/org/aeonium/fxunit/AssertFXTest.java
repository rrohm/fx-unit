/*
 * Copyright (C) 2020 Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;.
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
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
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

  protected static final String TEXT = "text";

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

  @Test(expected = AssertionError.class)
  public void testAssertDisabled_negative_null() throws Exception {
    System.out.println("disabled");
    AssertFX.assertDisabled(null);
  }

  @Test(expected = AssertionError.class)
  public void testAssertDisabled_negative_false() throws Exception {
    System.out.println("disabled");
    Node node = new Button("button");
    node.disableProperty().set(false);

    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(node));
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    AssertFX.assertDisabled(node);
  }

  @Test
  public void testAssertDisabled() throws Exception {
    System.out.println("disabled");
    Node node = new Button("button");
    node.disableProperty().set(true);

    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(node));
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    AssertFX.assertDisabled(node);
  }

  @Test(expected = AssertionError.class)
  public void testAssertEnabled_negative_null() throws Exception {
    System.out.println("enabled");
    AssertFX.assertEnabled(null);
  }

  @Test(expected = AssertionError.class)
  public void testAssertEnabled_negative_false() throws Exception {
    System.out.println("enabled");
    Node node = new Button("button");
    node.disableProperty().set(true);

    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(node));
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    AssertFX.assertEnabled(node);
  }

  @Test
  public void testAssertEnabled() throws Exception {
    System.out.println("enabled");
    Node node = new Button("button");
    node.disableProperty().set(false);

    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(node));
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    AssertFX.assertEnabled(node);
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

    Thread.sleep(300);
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

  @Test(expected = AssertionError.class)
  public void testAssertHasChildren_negative_null() throws Exception {
    System.out.println("assertHasChildren");
    AssertFX.assertHasChildren(null, 0);
  }

  @Test(expected = AssertionError.class)
  public void testAssertHasChildren_negative_1() throws Exception {
    System.out.println("assertHasChildren");
    Pane node = new Pane(new Label("a Child"));

    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(node));
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    AssertFX.assertHasChildren(node, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAssertHasChildren_negative_minus1() throws Exception {
    System.out.println("assertHasChildren");
    Pane node = new Pane(new Label("a Child"));

    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(node));
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    AssertFX.assertHasChildren(node, -1);
  }

  @Test
  public void testAssertHasChildren() throws Exception {
    System.out.println("assertHasChildren");
    Pane node = new Pane();

    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(node));
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    AssertFX.assertHasChildren(node, 0);
  }

  @Test(expected = AssertionError.class)
  public void testAssertManaged_negative_null() throws Exception {
    System.out.println("assertManaged");
    AssertFX.assertManaged(null);
  }

  @Test(expected = AssertionError.class)
  public void testAssertManaged_negative_false() throws Exception {
    System.out.println("assertManaged");
    Node node = new Button("button");
    node.setManaged(false);

    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(node));
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    AssertFX.assertManaged(node);
  }

  @Test
  public void testAssertManaged() throws Exception {
    System.out.println("assertManaged");
    Node node = new Button("button");
    node.setManaged(true);

    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(node));
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    AssertFX.assertManaged(node);
  }

  @Test(expected = AssertionError.class)
  public void testAssertNotManaged_negative_null() throws Exception {
    System.out.println("assertNotManaged");
    AssertFX.assertNotManaged(null);
  }

  @Test(expected = AssertionError.class)
  public void testAssertNotManaged_negative_false() throws Exception {
    System.out.println("assertNotManaged");
    Node node = new Button("button");
    node.setManaged(true);

    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(node));
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    AssertFX.assertNotManaged(node);
  }

  @Test
  public void testAssertNotManaged() throws Exception {
    System.out.println("assertNotManaged");
    Node node = new Button("button");
    node.setManaged(false);

    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(node));
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    AssertFX.assertNotManaged(node);
  }

  @Test(expected = AssertionError.class)
  public void testAssertNotSelected_negative_null() throws Exception {
    System.out.println("assertNotSelected");
    AssertFX.assertNotSelected(null);
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

  @Test
  public void testAssertSelected_Toggle() throws Exception {
    System.out.println("assertSelected");
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
    AssertFX.assertSelected(tb1);
    AssertFX.assertNotSelected(tb2);
  }

  @Test(expected = AssertionError.class)
  public void testAssertSelected_Toggle_false() throws Exception {
    System.out.println("assertSelected");
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
    AssertFX.assertSelected(tb2);
  }

  @Test(expected = AssertionError.class)
  public void testAssertSelected_Toggle_negative_null() throws Exception {
    System.out.println("assertSelected");
    ToggleButton tb1 = null;
    AssertFX.assertSelected(tb1);
  }

  @Test(expected = AssertionError.class)
  public void testAssertSelected_Toggle_negative_notSelected() throws Exception {
    System.out.println("assertSelected");
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
    AssertFX.assertNotSelected(tb1);
    AssertFX.assertSelected(tb2);
  }

  /**
   * Test of assertSelected method, of class AssertFX.
   *
   * @throws java.lang.Exception any
   */
  @Test
  public void testAssertSelected_TabPane() throws Exception {
    System.out.println("assertSelected TabPane");
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
  public void testAssertSelected_TabPane_negative() throws Exception {
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
  public void testAssertSelected_TabPane_negative_tabPane_null() throws Exception {
    System.out.println("assertSelected");
    TabPane tabPane = null;

    expectedExceptionRule.expectMessage(I18N.getString(I18N.TABPANE_IS_NULL));
    AssertFX.assertSelected(tabPane, "something");
  }

  @Test
  public void testAssertSelected_TabPane_negative_id_null() throws Exception {
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
  public void testAssertSelected_TabPane_negative_id_nonexistent() throws Exception {
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
  public void testAssertSelected_TabPane_negative_null_selection() throws Exception {
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

  @Test(expected = AssertionError.class)
  public void testAssertText_negative_labeled_null() throws Exception {
    System.out.println("assertText");
    Labeled labeled = null;
    AssertFX.assertText(labeled, TEXT);
  }

  @Test(expected = AssertionError.class)
  public void testAssertText_negative_labeled_false() throws Exception {
    System.out.println("assertText");
    Labeled labeled = new Label("");

    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(labeled));
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    AssertFX.assertText(labeled, TEXT);
  }

  @Test(expected = AssertionError.class)
  public void testAssertText_negative_text_null() throws Exception {
    System.out.println("assertText");
    Labeled labeled = new Label("");

    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(labeled));
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    AssertFX.assertText(labeled, null);
  }

  @Test
  public void testAssertText() throws Exception {
    System.out.println("assertText");
    Labeled labeled = new Label(TEXT);

    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(labeled));
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    AssertFX.assertText(labeled, TEXT);
  }

  @Test(expected = AssertionError.class)
  public void testAssertText_negative_textInputControl_null() throws Exception {
    System.out.println("assertText");
    TextInputControl textInputControl = null;
    AssertFX.assertText(textInputControl, TEXT);
  }

  @Test(expected = AssertionError.class)
  public void testAssertText_negative_textInputControl_false() throws Exception {
    System.out.println("assertText");
    TextInputControl textInputControl = new TextField("");
    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(textInputControl));
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    AssertFX.assertText(textInputControl, TEXT);
  }

  @Test(expected = AssertionError.class)
  public void testAssertText_negative_textInputControl_text_null() throws Exception {
    System.out.println("assertText");
    TextInputControl textInputControl = new TextField("");
    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(textInputControl));
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    AssertFX.assertText(textInputControl, null);
  }

  @Test
  public void testAssertText_texinputControl() throws Exception {
    System.out.println("assertText");
    TextInputControl textInputControl = new TextField(TEXT);
    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(textInputControl));
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    AssertFX.assertText(textInputControl, TEXT);
  }

  @Test(expected = AssertionError.class)
  public void testAssertTooltip_negative_null() throws Exception {
    System.out.println("assertTooltipText");
    Control control = null;
    AssertFX.assertTooltipText(control, TEXT);
  }

  @Test(expected = AssertionError.class)
  public void testAssertTooltip_negative_no_tooltip() throws Exception {
    System.out.println("assertTooltipText");
    Control control = new Button();

    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(control));
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    AssertFX.assertTooltipText(control, TEXT);
  }

  @Test(expected = AssertionError.class)
  public void testAssertTooltip_negative_wrong_tooltip() throws Exception {
    System.out.println("assertTooltipText");
    Control control = new Button();
    control.setTooltip(new Tooltip("other text"));

    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(control));
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    AssertFX.assertTooltipText(control, TEXT);
  }

  @Test(expected = AssertionError.class)
  public void testAssertTooltip_negative_futile_tooltip() throws Exception {
    System.out.println("assertTooltipText");
    Control control = new Button();
    control.setTooltip(new Tooltip("other text"));

    final CountDownLatch latch = new CountDownLatch(1);

    Platform.runLater(() -> {
      Scene scene = new Scene(new VBox(control));
      stage.setScene(scene);
      latch.countDown();
    });

    latch.await();
    AssertFX.assertTooltipText(control, null);
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
