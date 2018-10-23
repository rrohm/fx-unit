/*
 * Copyright (C) 2018 Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;.
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
package org.aeonium.fxunit.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Internationalization tool. 
 * 
 * @author Robert Rohm&lt;r.rohm@aeonium-systems.de&gt;
 */
public final class I18N {
  
  public static final String ID_IS_NULL           = "ID_IS_NULL";
  public static final String NODE_IS_NULL         = "NODE_IS_NULL";
  public static final String TABPANE_IS_NULL      = "TABPANE_IS_NULL";
  public static final String WINDOW_IS_NULL       = "WINDOW_IS_NULL";
  
  private static final String BUNDLE_NAME = "org.aeonium.fxunit.i18n.messages";
  private static ResourceBundle bundle = null;

  private I18N() {
    // no op
  }

  /**
   * Get the default locale's resource bundle.
   * @return The resource bundle.
   */
  public static ResourceBundle getLanguageBundle(){
    if (bundle == null) {
      bundle = ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault());
    }
    return bundle;
  }

  /**
   * Get a string according to the default locale.
   * @param t The string name
   * @return The string
   */
  public static String getString(String t){
    return getLanguageBundle().getString(t);
  }
}
