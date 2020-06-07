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
package org.aeonium.fxunit;

/**
 * Exception for aeFXUnit problems that may occur anywhere or in overridden
 * methods that cannot declare an exception.
 *
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public class FXUnitException extends RuntimeException {

  /**
   * Create a new FXUnitException.
   */
  public FXUnitException() {
    super();
  }

  /**
   * Create a new FXUnitException with message.
   * @param message The message.
   */
  public FXUnitException(String message) {
    super(message);
  }
  
  
  /**
   * Create a new FXUnitException with message and a cause.
   * @param message The message.
   * @param cause The cause.
   */
  public FXUnitException(String message, Throwable cause) {
    super(message, cause);
  }
  
}
