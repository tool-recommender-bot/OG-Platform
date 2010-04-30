/**
 * Copyright (C) 2009 - 2010 by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.engine.position;

import com.opengamma.id.Identifiable;
import com.opengamma.id.Identifier;

/**
 * A portfolio of positions, typically having business-level meaning.
 * <p>
 * A portfolio is the primary element of business-level grouping within the position master.
 * It consists of a number of positions which are grouped using a flexible tree structure.
 * <p>
 * A portfolio typically has meta-data.
 */
public interface Portfolio extends Identifiable {

  /**
   * Gets the identifier of the portfolio.
   * @return the identifier, never null
   */
  Identifier getIdentityKey();

  /**
   * Gets the name of the portfolio intended for display purposes.
   * @return the name, never null
   */
  String getName();

  /**
   * Gets the root node in the portfolio.
   * <p>
   * The positions stored in a portfolios are held in a tree structure.
   * This method accesses the root of the tree structure.
   * @return the root node of the tree structure, never null
   */
  PortfolioNode getRootNode();

  /**
   * Finds a specific node from this portfolio by identity key.
   * @param identityKey  the identity key, null returns null
   * @return the node, null if not found
   */
  PortfolioNode getNode(Identifier identityKey);

  /**
   * Finds a specific position from this portfolio by identity key.
   * @param identityKey  the identity key, null returns null
   * @return the position, null if not found
   */
  Position getPosition(Identifier identityKey);

}
