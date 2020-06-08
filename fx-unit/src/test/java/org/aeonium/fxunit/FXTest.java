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
import javafx.stage.Stage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 * Test cases for {@link FX}.
 *
 * @author robert rohm
 */
public class FXTest {

  public FXTest() {
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

  protected static final String SOME_ID = "some ID";

  /**
   * Test of lookup method, of class FX.
   */
  @Test(expected = NullPointerException.class)
  public void testLookup_negtive_nullStage_String() {
    System.out.println("lookup");
    String id = SOME_ID;
    FX.lookup(id);
  }

  /**
   * Test of lookup method, of class FX.
   */
  @Test(expected = NullPointerException.class)
  public void testLookup_negative_Stage_null_String() {
    System.out.println("lookup");
    Stage stage = null;
    String id = SOME_ID;
    FX.lookup(stage, id);
  }

  /**
   * Test of children method, of class FX.
   */
  @Test
  @Ignore("TODO")
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
   */
  @Test
  @Ignore("TODO")
  public void testHasChildren() {
    System.out.println("hasChildren");
    int count = 0;
    FX instance = null;
    FX expResult = null;
    FX result = instance.hasChildren(count);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of hasText method, of class FX.
   */
  @Test
  @Ignore("TODO")
  public void testHasText() {
    System.out.println("hasText");
    String text = "";
    FX instance = null;
    FX expResult = null;
    FX result = instance.hasText(text);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of hasTooltipText method, of class FX.
   */
  @Test
  @Ignore("TODO")
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
  @Ignore("TODO")
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
   */
  @Test
  @Ignore("TODO")
  public void testIsNotManaged() {
    System.out.println("isNotManaged");
    FX instance = null;
    FX expResult = null;
    FX result = instance.isNotManaged();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of isNotSelected method, of class FX.
   */
  @Test
  @Ignore("TODO")
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
   */
  @Test
  @Ignore("TODO")
  public void testIsNotVisible() {
    System.out.println("isNotVisible");
    FX instance = null;
    FX expResult = null;
    FX result = instance.isNotVisible();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of isSelected method, of class FX.
   */
  @Test
  @Ignore("TODO")
  public void testIsSelected() {
    System.out.println("isSelected");
    FX instance = null;
    FX expResult = null;
    FX result = instance.isSelected();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of isManaged method, of class FX.
   */
  @Test
  @Ignore("TODO")
  public void testIsManaged() {
    System.out.println("isManaged");
    FX instance = null;
    FX expResult = null;
    FX result = instance.isManaged();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of isVisible method, of class FX.
   */
  @Test
  @Ignore("TODO")
  public void testIsVisible() {
    System.out.println("isVisible");
    FX instance = null;
    FX expResult = null;
    FX result = instance.isVisible();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of getNode method, of class FX.
   */
  @Test
  @Ignore("TODO")
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
  @Ignore("TODO")
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
  @Ignore("TODO")
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
