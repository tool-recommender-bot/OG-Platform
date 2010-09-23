/**
 * Copyright (C) 2009 - 2009 by OpenGamma Inc.
 * 
 * Please see distribution for license.
 */
package com.opengamma.engine.function;

import java.util.Collections;
import java.util.Set;

import com.opengamma.engine.value.ValueSpecification;

/**
 * The base class from which most {@link FunctionDefinition} implementations
 * should inherit.
 *
 * @author kirk
 */
public abstract class AbstractFunction implements FunctionDefinition {
  private String _uniqueIdentifier;

  /**
   * @return the uniqueIdentifier
   */
  public String getUniqueIdentifier() {
    return _uniqueIdentifier;
  }

  /**
   * @param uniqueIdentifier the uniqueIdentifier to set
   */
  public void setUniqueIdentifier(String uniqueIdentifier) {
    if (_uniqueIdentifier != null) {
      throw new IllegalStateException("Function unique ID already set");
    }
    _uniqueIdentifier = uniqueIdentifier;
  }

  @Override
  public void init(FunctionCompilationContext context) {
  }

  @Override
  public Set<ValueSpecification> getRequiredLiveData() {
    return Collections.emptySet();
  }

  @Override
  public FunctionParameters getDefaultParameters() {
    // by default, a function has no parameters.
    return new EmptyFunctionParameters();
  }
  
}
