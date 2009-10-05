/**
 * Copyright (C) 2009 - 2009 by OpenGamma Inc.
 * 
 * Please see distribution for license.
 */
package com.opengamma.financial.model.option.definition;

import java.util.Arrays;
import java.util.List;

import javax.time.InstantProvider;
import javax.time.calendar.ZonedDateTime;

import com.opengamma.financial.greeks.Greek;
import com.opengamma.financial.greeks.SingleGreekResult;
import com.opengamma.financial.model.option.pricing.analytic.AnalyticOptionModel;
import com.opengamma.financial.model.option.pricing.analytic.BlackScholesMertonModel;
import com.opengamma.math.function.Function1D;
import com.opengamma.util.time.Expiry;

/**
 * A simple chooser option gives the holder the right to choose whether the
 * option is to be a standard call or put (both with the same expiry) after a
 * certain time. The exercise style of the option, once the choice has been
 * made, is European.
 * <p>
 * The payoff from the option with strike <i>K</i> and spot <i>S</i> is
 * <i>max(c<sub>BSM</i>(S, K, T), p<sub>BSM</sub>(S, K, T)</i>, where
 * <i>C<sub>BSM</sub></i> is the Black-Scholes-Merton call price,
 * <i>P<sub>BSM</sub></i> is the Black-Scholes-Merton put price and <i>T</i> is
 * the time to maturity of the put or call.
 * 
 * @author emcleod
 */

public class SimpleChooserOptionDefinition extends OptionDefinition<StandardOptionDataBundle> {
  private final ZonedDateTime _chooseDate;
  protected final EuropeanVanillaOptionDefinition _callDefinition;
  protected final EuropeanVanillaOptionDefinition _putDefinition;
  protected final AnalyticOptionModel<EuropeanVanillaOptionDefinition, StandardOptionDataBundle> BSM = new BlackScholesMertonModel();
  protected final List<Greek> GREEKS = Arrays.asList(new Greek[] { Greek.PRICE });

  /**
   * 
   * @param strike
   * @param underlyingExpiry
   * @param chooseDate
   * @param optionExpiry
   * @param vars
   */
  public SimpleChooserOptionDefinition(double strike, Expiry underlyingExpiry, ZonedDateTime chooseDate) {
    super(strike, underlyingExpiry, null);
    if (chooseDate.toInstant().isAfter(underlyingExpiry.toInstant()))
      throw new IllegalArgumentException("Underlying option expiry must be after the choice date");
    _chooseDate = chooseDate;
    _callDefinition = new EuropeanVanillaOptionDefinition(strike, underlyingExpiry, true);
    _putDefinition = new EuropeanVanillaOptionDefinition(strike, underlyingExpiry, false);
  }

  @Override
  protected void initPayoffAndExerciseFunctions() {
    _payoffFunction = new Function1D<StandardOptionDataBundle, Double>() {

      @Override
      public Double evaluate(StandardOptionDataBundle data) {
        final double callPrice = ((SingleGreekResult)BSM.getGreeks(_callDefinition, data, GREEKS).get(Greek.PRICE)).getResult();
        final double putPrice = ((SingleGreekResult)BSM.getGreeks(_putDefinition, data, GREEKS).get(Greek.PRICE)).getResult();
        return Math.max(callPrice, putPrice);
      }

    };

    _exerciseFunction = new Function1D<StandardOptionDataBundle, Boolean>() {

      @Override
      public Boolean evaluate(StandardOptionDataBundle data) {
        return false;
      }

    };
  }

  public InstantProvider getChooseDate() {
    return _chooseDate;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result
        + ((_callDefinition == null) ? 0 : _callDefinition.hashCode());
    result = prime * result
        + ((_chooseDate == null) ? 0 : _chooseDate.hashCode());
    result = prime * result
        + ((_putDefinition == null) ? 0 : _putDefinition.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    SimpleChooserOptionDefinition other = (SimpleChooserOptionDefinition) obj;
    if (_callDefinition == null) {
      if (other._callDefinition != null)
        return false;
    } else if (!_callDefinition.equals(other._callDefinition))
      return false;
    if (_chooseDate == null) {
      if (other._chooseDate != null)
        return false;
    } else if (!_chooseDate.equals(other._chooseDate))
      return false;
    if (_putDefinition == null) {
      if (other._putDefinition != null)
        return false;
    } else if (!_putDefinition.equals(other._putDefinition))
      return false;
    return true;
  }
}
