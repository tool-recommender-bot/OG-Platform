/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.bbg;


import java.math.BigDecimal;
import java.util.Map;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBean;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.Month;

import com.opengamma.bbg.util.BloombergDataUtils;
import com.opengamma.core.id.ExternalSchemes;
import com.opengamma.financial.security.option.OptionType;
import com.opengamma.id.ExternalId;
import com.opengamma.util.ArgumentChecker;

/**
 * 
 */
@BeanDefinition
public class BloombergContractID extends DirectBean {
  
  private static final Logger s_logger = LoggerFactory.getLogger(BloombergContractID.class);
  
  /**
   * The prefix in classic bloomberg ticker
   */
  @PropertyDefinition
  private String _contractCode;
  
  /**
   * The yellow key also know as market sector;
   */
  @PropertyDefinition
  private String _marketSector;
  
  
  public BloombergContractID() {
  }
  
  public BloombergContractID(String contractCode, String marketSector) {
    ArgumentChecker.notNull(contractCode, "contractCode");
    ArgumentChecker.notNull(marketSector, "marketSector");
    setContractCode(contractCode);
    setMarketSector(marketSector);
  }
  
  public ExternalId toFutureExternalId(Integer month, Integer year) {
    ArgumentChecker.notNull(month, "month");
    ArgumentChecker.notNull(year, "year");
    
    ExternalId futureId = null;
    String futureMonthCode = BloombergDataUtils.futureMonthCode(Month.of(month));
    if (futureMonthCode != null) {
      String yearStr = String.valueOf(year);
      if (yearStr.length() > 1) {
        yearStr = "" + yearStr.charAt(yearStr.length() - 1);
      }
      futureId = ExternalSchemes.bloombergTickerSecurityId(String.format("%s%s%s %s", 
          getContractCode(), futureMonthCode, yearStr, getMarketSector()).toUpperCase());
    } else {
      s_logger.warn("Unable to resolve month {} to its future month's code", Month.of(month));
    }
    return futureId;
  }
  
  public ExternalId toOptionExternalId(Integer month, Integer year, Double strike, OptionType optionType) {
    ArgumentChecker.notNull(month, "month");
    ArgumentChecker.notNull(year, "year");
    ArgumentChecker.notNull(strike, "strike");
    ArgumentChecker.notNull(optionType, "optionType");
    
    ExternalId optionId = null;
    String monthCode = BloombergDataUtils.futureMonthCode(Month.of(month));
    if (monthCode != null) {
      String yearStr = String.valueOf(year);
      if (yearStr.length() > 1) {
        yearStr = "" + yearStr.charAt(yearStr.length() - 1);
      }
      optionId = ExternalSchemes.bloombergTickerSecurityId(String.format("%s%s%s%s %s %s", 
          getContractCode(), monthCode, yearStr, optionType.name().charAt(0), getRoundedPrice(strike, 3), getMarketSector()).toUpperCase());
    } else {
      s_logger.warn("Unable to resolve month {} to its future month's code", Month.of(month));
    }
    return optionId;
  }
  
  private String getRoundedPrice(final Double doubleValue, final int scale) {
    BigDecimal bigDecimal = BigDecimal.valueOf(doubleValue).setScale(scale, BigDecimal.ROUND_HALF_UP);
    return bigDecimal.toString();
  }
  
  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code BloombergContractID}.
   * @return the meta-bean, not null
   */
  public static BloombergContractID.Meta meta() {
    return BloombergContractID.Meta.INSTANCE;
  }
  static {
    JodaBeanUtils.registerMetaBean(BloombergContractID.Meta.INSTANCE);
  }

  @Override
  public BloombergContractID.Meta metaBean() {
    return BloombergContractID.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName, boolean quiet) {
    switch (propertyName.hashCode()) {
      case -1402840545:  // contractCode
        return getContractCode();
      case -98321726:  // marketSector
        return getMarketSector();
    }
    return super.propertyGet(propertyName, quiet);
  }

  @Override
  protected void propertySet(String propertyName, Object newValue, boolean quiet) {
    switch (propertyName.hashCode()) {
      case -1402840545:  // contractCode
        setContractCode((String) newValue);
        return;
      case -98321726:  // marketSector
        setMarketSector((String) newValue);
        return;
    }
    super.propertySet(propertyName, newValue, quiet);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      BloombergContractID other = (BloombergContractID) obj;
      return JodaBeanUtils.equal(getContractCode(), other.getContractCode()) &&
          JodaBeanUtils.equal(getMarketSector(), other.getMarketSector());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getContractCode());
    hash += hash * 31 + JodaBeanUtils.hashCode(getMarketSector());
    return hash;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the prefix in classic bloomberg ticker
   * @return the value of the property
   */
  public String getContractCode() {
    return _contractCode;
  }

  /**
   * Sets the prefix in classic bloomberg ticker
   * @param contractCode  the new value of the property
   */
  public void setContractCode(String contractCode) {
    this._contractCode = contractCode;
  }

  /**
   * Gets the the {@code contractCode} property.
   * @return the property, not null
   */
  public final Property<String> contractCode() {
    return metaBean().contractCode().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the yellow key also know as market sector;
   * @return the value of the property
   */
  public String getMarketSector() {
    return _marketSector;
  }

  /**
   * Sets the yellow key also know as market sector;
   * @param marketSector  the new value of the property
   */
  public void setMarketSector(String marketSector) {
    this._marketSector = marketSector;
  }

  /**
   * Gets the the {@code marketSector} property.
   * @return the property, not null
   */
  public final Property<String> marketSector() {
    return metaBean().marketSector().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code BloombergContractID}.
   */
  public static class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code contractCode} property.
     */
    private final MetaProperty<String> _contractCode = DirectMetaProperty.ofReadWrite(
        this, "contractCode", BloombergContractID.class, String.class);
    /**
     * The meta-property for the {@code marketSector} property.
     */
    private final MetaProperty<String> _marketSector = DirectMetaProperty.ofReadWrite(
        this, "marketSector", BloombergContractID.class, String.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "contractCode",
        "marketSector");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -1402840545:  // contractCode
          return _contractCode;
        case -98321726:  // marketSector
          return _marketSector;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends BloombergContractID> builder() {
      return new DirectBeanBuilder<BloombergContractID>(new BloombergContractID());
    }

    @Override
    public Class<? extends BloombergContractID> beanType() {
      return BloombergContractID.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code contractCode} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> contractCode() {
      return _contractCode;
    }

    /**
     * The meta-property for the {@code marketSector} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> marketSector() {
      return _marketSector;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
