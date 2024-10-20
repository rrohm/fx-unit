
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

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author robert
 */
public class FXUnitTestBaseTest {

  public FXUnitTestBaseTest() {
  }

  @BeforeAll
  public static void setUpClass() {
  }

  @AfterAll
  public static void tearDownClass() {
  }

  @BeforeEach
  public void setUp() {
  }

  @AfterEach
  public void tearDown() {
  }

  /**
   * Test of setUpClass method, of class FXUnitTestBase.
   */
  @Test
  public void testSetUpClass() {
    System.out.println("setUpClass");
    FXUnitTestBase.setUpClass();
    assertTrue(true, "Initialization must not fail");
  }

  /**
   * Test of getRoot method, of class FXUnitTestBase.
   */
  @Test
  public void testGetRoot() {
    System.out.println("getRoot");
    FXUnitTestBase<?> instance = new FXUnitTestBaseImpl();
    Parent result = instance.getRoot();
    assertNull(result);
  }

  /**
   * Test of getController method, of class FXUnitTestBase.
   */
  @Test
  public void testGetController() {
    System.out.println("getController");
    FXUnitTestBase<?> instance = new FXUnitTestBaseImpl();
    Object result = instance.getController();
    assertNull(result);
  }

  /**
   * Test of getStage method, of class FXUnitTestBase.
   */
  @Test
  public void testGetStage() {
    System.out.println("getStage");
    FXUnitTestBase<?> instance = new FXUnitTestBaseImpl();
    Stage result = instance.getStage();
    assertNull(result);
  }

  /**
   * Test of show method, of class FXUnitTestBase.
   */
  @Test
  public void testShow() {
    System.out.println("show");
    Assertions.assertThrows(NullPointerException.class, () -> {
      Node node = null;
      FXUnitTestBase<?> instance = new FXUnitTestBaseImpl();
      instance.show(node);
    });
  }

  public class FXUnitTestBaseImpl extends FXUnitTestBase<Object> {
    // no op
  }

}
