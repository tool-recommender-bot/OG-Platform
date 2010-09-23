/**
 * Copyright (C) 2009 - 2010 by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.engine.security;

import com.opengamma.id.IdentifierBundle;
import com.opengamma.id.UniqueIdentifiable;
import com.opengamma.id.UniqueIdentifier;

/**
 * A security that it may be possible to hold a position in.
 * <p>
 * A security generically defined as anything that a position can be held in.
 */
public interface Security extends UniqueIdentifiable {

  /**
   * Gets the unique identifier of the security.
   * @return the identifier, not null
   */
  UniqueIdentifier getUniqueIdentifier();

  /**
   * Gets the name of the security intended for display purposes.
   * @return the name, not null
   */
  String getName();

  /**
   * Gets the bundle of identifiers that define the security.
   * @return the identifiers defining the security, not null
   */
  IdentifierBundle getIdentifiers();

  /**
   * Gets the text-based type of this security.
   * @return the text-based type of this security
   */
  String getSecurityType();

}
