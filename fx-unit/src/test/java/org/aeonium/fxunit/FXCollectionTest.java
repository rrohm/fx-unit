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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test cases for {@link FXCollection}.
 *
 * @author robert rohm
 */
public class FXCollectionTest {

  public FXCollectionTest() {
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

  /**
   * Test of get method, of class FXCollection.
   */
  @Test(expected = NullPointerException.class)
  public void testGet_negative_null_nodes() {
    System.out.println("get");
    int index = 0;
    FXCollection instance = new FXCollection(null);
    FX fx = instance.get(index);
    Assert.assertNotNull(fx);
  }

  /**
   * Test of hasChildren method, of class FXCollection.
   */
  @Test(expected = NullPointerException.class)
  public void testHasChildren_negative_null_nodes() {
    System.out.println("hasChildren");
    int count = 0;
    FXCollection instance = new FXCollection(null);
    FXCollection fXCollection = instance.hasChildren(count);
    Assert.assertNotNull(fXCollection);
  }

  /**
   * Test of isEmpty method, of class FXCollection.
   */
  @Test(expected = NullPointerException.class)
  public void testIsEmpty_negative_null_nodes() {
    System.out.println("isEmpty");
    FXCollection instance = new FXCollection(null);
    FXCollection fXCollection = instance.isEmpty();
    Assert.assertNotNull(fXCollection);
  }

}
