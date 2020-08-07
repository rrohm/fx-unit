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
package org.aeonium.fxunit.matchers;

import javafx.scene.Node;
import org.hamcrest.Matcher;

/**
 * This class provides Hamcrest matchers for FXUnit.
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public abstract class Matchers {

  private Matchers() {
    // no op 
  }

  /**
   * Returns an {@link IsVisibleMatcher} matcher that returns true if the node
   * is visible, or throws an NullPointerException if the node is null.
   *
   * @return Whether the node is visible.
   */
  public static Matcher<Node> isVisible() {
    return new IsVisibleMatcher();
  }

  /**
   * Returns an {@link IsNotVisibleMatcher} matcher that returns true if the
   * node is NOT visible, or throws an NullPointerException if the node is null.
   *
   * @return Whether the node is invisible.
   */
  public static Matcher<Node> isNotVisible() {
    return new IsNotVisibleMatcher();
  }
}
