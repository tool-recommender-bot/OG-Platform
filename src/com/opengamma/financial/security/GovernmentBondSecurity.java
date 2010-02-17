/**
 * Copyright (C) 2009 - 2009 by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.security;

import com.opengamma.financial.Currency;
import com.opengamma.financial.convention.businessday.BusinessDayConvention;
import com.opengamma.financial.convention.daycount.DayCount;
import com.opengamma.financial.convention.frequency.Frequency;
import com.opengamma.util.time.Expiry;

/**
 * 
 *
 * @author Andrew
 */
public class GovernmentBondSecurity extends BondSecurity {

  /**
   * @param maturity
   * @param coupon
   * @param frequency
   * @param country
   * @param creditRating
   * @param currency
   * @param issuer
   * @param dayCountConvention
   * @param businessDayConvention
   */
  public GovernmentBondSecurity(Expiry maturity, double coupon,
      Frequency frequency, String country, String creditRating,
      Currency currency, String issuer, DayCount dayCountConvention,
      BusinessDayConvention businessDayConvention) {
    super(maturity, coupon, frequency, country, creditRating, currency, issuer,
        dayCountConvention, businessDayConvention);
    // TODO Auto-generated constructor stub
  }

  @Override
  public <T> T accept(FinancialSecurityVisitor<T> visitor) {
    return visitor.visitGovernmentBondSecurity (this);
  }
  
}