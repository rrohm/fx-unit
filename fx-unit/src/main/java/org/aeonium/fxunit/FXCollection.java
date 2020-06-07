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

import javafx.collections.ObservableList;
import javafx.scene.Node;

/**
 * This class wrapps up a collection of SUT nodes, and provides assertion
 * methodes operating on the wrapped nodes.
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class FXCollection {

  private final ObservableList<Node> nodes;

  FXCollection(ObservableList<Node> childrenUnmodifiable) {
    this.nodes = childrenUnmodifiable;
  }

  public FX get(int index) {
    if (this.nodes.size() > index) {
      return new FX(this.nodes.get(index));
    } else {
      throw new AssertionError("Index out or range: " + index);
    }
  }

  public FXCollection hasChildren(int count) {
    for (Node node : this.nodes) {
      new FX(node).hasChildren(count);
    }

    return this;
  }

  public FXCollection isEmpty() {
    for (Node node : this.nodes) {
      new FX(node).isEmpty();
    }

    return this;
  }

}
