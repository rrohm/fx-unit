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

import javafx.scene.control.MenuItem;

/**
 * Utility for testing MenuItems with a fluent API.
 * @author robert rohm
 */
public class FXMenuItem {
  
  private final MenuItem item;

  public FXMenuItem(MenuItem item) {
    this.item = item;
  }
  
  public FXMenuItem isDisabled(){
    if (this.item == null) {
      throw new AssertionError("MenuItem is null.");
    }
    if (!this.item.isDisable()) {
      throw new AssertionError("MenuItem " + this.item + " should be disabled, but is not.");
    }
    return this;
  }
  
  public FXMenuItem isEnabled(){
    if (this.item == null) {
      throw new AssertionError("MenuItem is null.");
    }
    if (this.item.isDisable()) {
      throw new AssertionError("MenuItem " + this.item + " should be enabled, but is not.");
    }
    return this;
  }
  
  public FXMenuItem isVisible(){
    if (this.item == null) {
      throw new AssertionError("MenuItem is null.");
    }
    if (!this.item.isVisible()) {
      throw new AssertionError("MenuItem " + this.item + " should be visible, but is not.");
    }
    return this;
  }
  
  public FXMenuItem isNotVisible(){
    if (this.item == null) {
      throw new AssertionError("MenuItem is null.");
    }
    if (this.item.isVisible()) {
      throw new AssertionError("MenuItem " + this.item + " should not be visible, but is.");
    }
    return this;
  }
}
