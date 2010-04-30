/**
 * Copyright (C) 2009 - 2009 by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.engine;

import java.util.Collection;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opengamma.engine.position.PortfolioNode;
import com.opengamma.engine.position.Position;
import com.opengamma.engine.position.PositionMaster;
import com.opengamma.engine.security.Security;
import com.opengamma.engine.security.SecurityMaster;
import com.opengamma.engine.view.ViewProcessingContext;
import com.opengamma.id.Identifier;
import com.opengamma.id.IdentifierBundle;
import com.opengamma.util.ArgumentChecker;

/**
 * Adheres to the {@link ComputationTargetResolver} interface, satisfying results using
 * elements of the {@link ViewProcessingContext}.
 *
 * @author kirk
 */
public class DefaultComputationTargetResolver implements ComputationTargetResolver {
  private static final Logger s_logger = LoggerFactory.getLogger(DefaultComputationTargetResolver.class);
  private final SecurityMaster _securityMaster;
  private final PositionMaster _positionMaster;
  
  public DefaultComputationTargetResolver(SecurityMaster securityMaster, PositionMaster positionMaster) {
    ArgumentChecker.notNull(securityMaster, "Security Master");
    ArgumentChecker.notNull(positionMaster, "Position master");
    _securityMaster = securityMaster;
    _positionMaster = positionMaster;
  }

  /**
   * @return the securityMaster
   */
  public SecurityMaster getSecurityMaster() {
    return _securityMaster;
  }

  /**
   * @return the positionMaster
   */
  public PositionMaster getPositionMaster() {
    return _positionMaster;
  }

  @Override
  public ComputationTarget resolve(
      ComputationTargetSpecification targetSpecification) {
    switch(targetSpecification.getType()) {
    case PRIMITIVE:
      return new ComputationTarget(targetSpecification.getType(), targetSpecification.getIdentifier());
    case SECURITY:
      Security security = resolveSecurity(targetSpecification.getIdentifier());
      s_logger.info("Resolved security ID {} to security {}", targetSpecification.getIdentifier(), security);
      if(security == null) {
        return null;
      } else {
        return new ComputationTarget(ComputationTargetType.SECURITY, security);
      }
    case POSITION:
      Position position = getPositionMaster().getPosition(targetSpecification.getIdentifier());
      s_logger.info("Resolved position ID {} to security {}", targetSpecification.getIdentifier(), position);
      if(position == null) {
        return null;
      } else {
        return new ComputationTarget(ComputationTargetType.POSITION, position);
      }
    case MULTIPLE_POSITIONS:
      PortfolioNode portfolioNode = getPositionMaster().getPortfolioNode(targetSpecification.getIdentifier());
      s_logger.info("Resolved portfolio node ID {} to security {}", targetSpecification.getIdentifier(), portfolioNode);
      if(portfolioNode == null) {
        return null;
      } else {
        return new ComputationTarget(ComputationTargetType.MULTIPLE_POSITIONS, portfolioNode);
      }
    default:
      throw new NotImplementedException("Unhandled computation target type " + targetSpecification.getType());
    }
  }

  /**
   * @param identifier
   * @return
   */
  private Security resolveSecurity(Identifier identifier) {
    if(ObjectUtils.equals(Security.SECURITY_IDENTITY_KEY_DOMAIN, identifier.getScheme())) {
      return getSecurityMaster().getSecurity(identifier);
    }
    // Must not be an identity key.
    IdentifierBundle bundle = new IdentifierBundle(identifier);
    Collection<Security> securities = getSecurityMaster().getSecurities(bundle);
    if(securities.size() > 1) {
      s_logger.warn("Got more than one result for {}:{}",identifier, securities);
    }
    if(securities.isEmpty()) {
      return null;
    } else {
      return securities.iterator().next();
    }
  }

}
