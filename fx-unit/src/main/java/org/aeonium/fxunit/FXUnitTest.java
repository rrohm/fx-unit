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
import org.junit.jupiter.api.BeforeAll;

/**
 *
 * @author robert
 * @param <T> Controller type;
 */
public abstract class FXUnitTest<T> {
  
  @BeforeAll
  public static void setUpClass() {
    FXUnit.init();
  }
  
  protected Parent getRoot(){
    return FXUnit.getRoot();
  }
  
  protected T getController() {
    return FXUnit.getController();
  }
  
  protected Stage getStage(){
    return FXUnit.getStage();
  }
  
  protected void show(Node node){
    FXUnit.show(node);
  }
}
