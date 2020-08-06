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

/**
 * Test cases for {@link FXMenu}.
 *
 * @author robert rohm
 */
public class FXMenuTest {

  Stage stage;

  public FXMenuTest() {
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
   * Test of getMenuItem method, of class FXMenu.
   */
  @Test(expected = AssertionError.class)
  public void testGetMenuItem_nullMenu_throws() {
    System.out.println("getMenuItem");
    String id = "#doNotKnow";
    FXMenu instance = new FXMenu(null);
    instance.getMenuItem(id);
  }

  @Test(expected = AssertionError.class)
  public void testGetMenuItem_itemNotFound_throws() {
    System.out.println("getMenuItem");
    String id = "#doNotKnow";
    FXMenu instance = new FXMenu(new Menu().getItems());
    instance.getMenuItem(id);
  }

  @Test
  public void testGetMenuItem_itemFound() {
    System.out.println("getMenuItem");
    String id = "doNotKnow";
    MenuItem item = new MenuItem("Test");
    item.setId(id);
    FXMenu instance = new FXMenu(new Menu("Test-Menu", null, item).getItems());
    instance.getMenuItem("#" + id);
  }

  @Test
  public void testGetMenuItem_itemFound_withoutPrefix() {
    System.out.println("getMenuItem");
    String id = "doNotKnow";
    MenuItem item = new MenuItem("Test");
    item.setId(id);
    FXMenu instance = new FXMenu(new Menu("Test-Menu", null, item).getItems());
    instance.getMenuItem(id);
  }

  /**
   * Test of hasMenuItem method, of class FXMenu.
   */
  @Test(expected = AssertionError.class)
  public void testHasMenuItem_nullMenu_throws() {
    System.out.println("hasMenuItem");
    String id = "#doNotKnow";
    FXMenu instance = new FXMenu(null);
    instance.hasMenuItem(id);
  }

  @Test(expected = AssertionError.class)
  public void testHasMenuItem_itemNotFound_throws() {
    System.out.println("hasMenuItem");
    String id = "#doNotKnow";
    FXMenu instance = new FXMenu(new Menu().getItems());
    instance.hasMenuItem(id);
  }

  @Test
  public void testHasMenuItem() {
    System.out.println("hasMenuItem");
    String id = "doNotKnow";
    MenuItem item = new MenuItem("Test");
    item.setId(id);
    FXMenu instance = new FXMenu(new Menu("Test-Menu", null, item).getItems());
    instance.hasMenuItem(id);
  }

  @Test
  public void testHasMenuItem_withPrefix() {
    System.out.println("hasMenuItem");
    String id = "doNotKnow";
    MenuItem item = new MenuItem("Test");
    item.setId(id);
    FXMenu instance = new FXMenu(new Menu("Test-Menu", null, item).getItems());
    instance.hasMenuItem("#" + id);
  }

}
