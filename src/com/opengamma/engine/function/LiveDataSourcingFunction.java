/**
 * Copyright (C) 2009 - 2009 by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.engine.function;

import java.util.Collections;
import java.util.Set;

import org.apache.commons.lang.NotImplementedException;

import com.opengamma.engine.ComputationTarget;
import com.opengamma.engine.ComputationTargetType;
import com.opengamma.engine.value.ComputedValue;
import com.opengamma.engine.value.ValueRequirement;
import com.opengamma.engine.value.ValueSpecification;
import com.opengamma.util.ArgumentChecker;

/**
 * 
 *
 * @author kirk
 */
public class LiveDataSourcingFunction extends AbstractFunction
implements FunctionInvoker {
  private final ValueRequirement _requirement;
  private final ValueSpecification _result;
  
  public LiveDataSourcingFunction(ValueRequirement requirement) {
    ArgumentChecker.notNull(requirement, "Value Requirement");
    _requirement = requirement;
    _result = new ValueSpecification(requirement);
  }

  /**
   * @return the requirement
   */
  public ValueRequirement getRequirement() {
    return _requirement;
  }
  
  public ValueSpecification getResult() {
    return _result;
  }

  @Override
  public boolean canApplyTo(FunctionCompilationContext context, ComputationTarget target) {
    // Special pseudo-function. If constructed, we apply.
    return true;
  }

  @Override
  public Set<ValueRequirement> getRequirements(FunctionCompilationContext context, ComputationTarget target) {
    // None by design.
    return Collections.emptySet();
  }

  @Override
  public Set<ValueSpecification> getResults(FunctionCompilationContext context, ComputationTarget target) {
    return Collections.singleton(_result);
  }

  @Override
  public String getShortName() {
    return "LiveDataSourcingFunction";
  }

  @Override
  public ComputationTargetType getTargetType() {
    return _requirement.getTargetSpecification().getType();
  }

  @Override
  public Set<ComputedValue> execute(
      FunctionExecutionContext executionContext, FunctionInputs inputs,
      ComputationTarget target, Set<ValueRequirement> desiredValues) {
    throw new NotImplementedException("LiveDataSourcingFunction should never be executed.");
  }

}
