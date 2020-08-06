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

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test cases for {@link FXMenuItem}.
 *
 * @author robert rohm
 */
public class FXMenuItemTest {

  private Stage stage;

  public FXMenuItemTest() {
    // no op
  }

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
   * Test of isDisabled method, of class FXMenuItem.
   */
  @Test(expected = AssertionError.class)
  public void testIsDisabled_null_throws() {
    System.out.println("isDisabled");
    FXMenuItem instance = new FXMenuItem(null);
    FXMenuItem result = instance.isDisabled();
    assertSame(instance, result);
  }

  @Test(expected = AssertionError.class)
  public void testIsDisabled_enabled_throws() {
    System.out.println("isDisabled");
    FXMenuItem instance = new FXMenuItem(new MenuItem());
    FXMenuItem result = instance.isDisabled();
    assertSame(instance, result);
  }

  @Test
  public void testIsDisabled_disabled() {
    System.out.println("isDisabled");
    final MenuItem menuItem = new MenuItem();
    menuItem.setDisable(true);
    FXMenuItem instance = new FXMenuItem(menuItem);
    FXMenuItem result = instance.isDisabled();
    assertSame(instance, result);
  }

  /**
   * Test of isEnabled method, of class FXMenuItem.
   */
  @Test(expected = AssertionError.class)
  public void testIsEnabled_null_throws() {
    System.out.println("isEnabled");
    FXMenuItem instance = new FXMenuItem(null);
    FXMenuItem result = instance.isEnabled();
    assertSame(instance, result);
  }

  @Test(expected = AssertionError.class)
  public void testIsEnabled_disabled_throws() {
    System.out.println("isEnabled");
    final MenuItem menuItem = new MenuItem();
    menuItem.setDisable(true);
    FXMenuItem instance = new FXMenuItem(menuItem);
    FXMenuItem result = instance.isEnabled();
    assertSame(instance, result);
  }

  @Test
  public void testIsEnabled() {
    System.out.println("isEnabled");
    final MenuItem menuItem = new MenuItem();
    menuItem.setDisable(false);
    FXMenuItem instance = new FXMenuItem(menuItem);
    FXMenuItem result = instance.isEnabled();
    assertSame(instance, result);
  }

  /**
   * Test of isVisible method, of class FXMenuItem.
   */
  @Test(expected = AssertionError.class)
  public void testIsVisible_null_throws() {
    System.out.println("isVisible");
    FXMenuItem instance = new FXMenuItem(null);
    FXMenuItem result = instance.isVisible();
    assertSame(instance, result);
  }

  @Test(expected = AssertionError.class)
  public void testIsVisible_visible_throws() {
    System.out.println("isVisible");
    final MenuItem menuItem = new MenuItem();
    menuItem.setVisible(false);
    FXMenuItem instance = new FXMenuItem(menuItem);
    FXMenuItem result = instance.isVisible();
    assertSame(instance, result);
  }

  @Test
  public void testIsVisible() {
    System.out.println("isVisible");
    FXMenuItem instance = new FXMenuItem(new MenuItem());
    FXMenuItem result = instance.isVisible();
    assertSame(instance, result);
  }

  /**
   * Test of isNotVisible method, of class FXMenuItem.
   */
  @Test(expected = AssertionError.class)
  public void testIsNotVisible_null_throws() {
    System.out.println("isNotVisible");
    FXMenuItem instance = new FXMenuItem(null);;
    FXMenuItem result = instance.isNotVisible();
    assertSame(instance, result);
  }

  @Test(expected = AssertionError.class)
  public void testIsNotVisible_visible_throws() {
    System.out.println("isNotVisible");
    FXMenuItem instance = new FXMenuItem(new MenuItem());
    FXMenuItem result = instance.isNotVisible();
    assertSame(instance, result);
  }

  @Test
  public void testIsNotVisible() {
    System.out.println("isNotVisible");
    final MenuItem menuItem = new MenuItem();
    menuItem.setVisible(false);
    FXMenuItem instance = new FXMenuItem(menuItem);
    FXMenuItem result = instance.isNotVisible();
    assertSame(instance, result);
  }

}
