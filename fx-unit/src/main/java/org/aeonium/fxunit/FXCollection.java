package org.aeonium.fxunit;

import javafx.collections.ObservableList;
import javafx.scene.Node;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class FXCollection {

  private final ObservableList<Node> nodes;

  FXCollection(ObservableList<Node> childrenUnmodifiable) {
    this.nodes = childrenUnmodifiable;
  }
  
  public FX get(int index){
    if (this.nodes.size() > index) {
      return new FX(this.nodes.get(index));
    } else {
      throw new AssertionError("Index out or range: " + index);
    }
  }

  public FXCollection hasChildren(int count){
    for (Node node : this.nodes) {
      new FX(node).hasChildren(count);
    }
    
    return this;
  }
  
  public FXCollection isEmpty(){
    for (Node node : this.nodes) {
      new FX(node).isEmpty();
    }
    
    return this;
  }
  
}
