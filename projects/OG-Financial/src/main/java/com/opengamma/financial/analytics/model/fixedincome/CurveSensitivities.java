/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */

package com.opengamma.financial.analytics.model.fixedincome;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.google.common.collect.ImmutableMap;
import com.opengamma.util.money.Currency;
import com.opengamma.util.tuple.Pair;

/**
 * The sensitivities of an instrument to a set of curves.
 */
@BeanDefinition
public final class CurveSensitivities implements ImmutableBean {

  /**
   * Sensitivities as a map keyed by a {@link Pair} of curve name and currency, and holding the curve sensitivity
   */
  @PropertyDefinition(validate = "notNull")
  private final Map<Pair<String, Currency>, Double> _sensitivities;

  /**
   * Create curve sensitivities from an input map.
   *
   * @param sensitivities the sensitivities as a map keyed by a {@link Pair} of curve name and currency,
   * and holding the curve sensitivity
   * @return the sensitivities object
   */
  public static CurveSensitivities of(Map<Pair<String, Currency>, Double> sensitivities) {
    return builder().sensitivities(sensitivities).build();
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code CurveSensitivities}.
   * @return the meta-bean, not null
   */
  public static CurveSensitivities.Meta meta() {
    return CurveSensitivities.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(CurveSensitivities.Meta.INSTANCE);
  }

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static CurveSensitivities.Builder builder() {
    return new CurveSensitivities.Builder();
  }

  private CurveSensitivities(
      Map<Pair<String, Currency>, Double> sensitivities) {
    JodaBeanUtils.notNull(sensitivities, "sensitivities");
    this._sensitivities = ImmutableMap.copyOf(sensitivities);
  }

  @Override
  public CurveSensitivities.Meta metaBean() {
    return CurveSensitivities.Meta.INSTANCE;
  }

  @Override
  public <R> Property<R> property(String propertyName) {
    return metaBean().<R>metaProperty(propertyName).createProperty(this);
  }

  @Override
  public Set<String> propertyNames() {
    return metaBean().metaPropertyMap().keySet();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets sensitivities as a map keyed by a {@link Pair} of curve name and currency, and holding the curve sensitivity
   * @return the value of the property, not null
   */
  public Map<Pair<String, Currency>, Double> getSensitivities() {
    return _sensitivities;
  }

  //-----------------------------------------------------------------------
  /**
   * Returns a builder that allows this bean to be mutated.
   * @return the mutable builder, not null
   */
  public Builder toBuilder() {
    return new Builder(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      CurveSensitivities other = (CurveSensitivities) obj;
      return JodaBeanUtils.equal(getSensitivities(), other.getSensitivities());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getSensitivities());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(64);
    buf.append("CurveSensitivities{");
    buf.append("sensitivities").append('=').append(JodaBeanUtils.toString(getSensitivities()));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code CurveSensitivities}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code sensitivities} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<Map<Pair<String, Currency>, Double>> _sensitivities = DirectMetaProperty.ofImmutable(
        this, "sensitivities", CurveSensitivities.class, (Class) Map.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "sensitivities");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 1226228605:  // sensitivities
          return _sensitivities;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public CurveSensitivities.Builder builder() {
      return new CurveSensitivities.Builder();
    }

    @Override
    public Class<? extends CurveSensitivities> beanType() {
      return CurveSensitivities.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code sensitivities} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Map<Pair<String, Currency>, Double>> sensitivities() {
      return _sensitivities;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 1226228605:  // sensitivities
          return ((CurveSensitivities) bean).getSensitivities();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      metaProperty(propertyName);
      if (quiet) {
        return;
      }
      throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
    }

  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code CurveSensitivities}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<CurveSensitivities> {

    private Map<Pair<String, Currency>, Double> _sensitivities = new HashMap<Pair<String, Currency>, Double>();

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(CurveSensitivities beanToCopy) {
      this._sensitivities = new HashMap<Pair<String, Currency>, Double>(beanToCopy.getSensitivities());
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 1226228605:  // sensitivities
          return _sensitivities;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 1226228605:  // sensitivities
          this._sensitivities = (Map<Pair<String, Currency>, Double>) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public Builder set(MetaProperty<?> property, Object value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setString(String propertyName, String value) {
      setString(meta().metaProperty(propertyName), value);
      return this;
    }

    @Override
    public Builder setString(MetaProperty<?> property, String value) {
      super.setString(property, value);
      return this;
    }

    @Override
    public Builder setAll(Map<String, ? extends Object> propertyValueMap) {
      super.setAll(propertyValueMap);
      return this;
    }

    @Override
    public CurveSensitivities build() {
      return new CurveSensitivities(
          _sensitivities);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the {@code sensitivities} property in the builder.
     * @param sensitivities  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder sensitivities(Map<Pair<String, Currency>, Double> sensitivities) {
      JodaBeanUtils.notNull(sensitivities, "sensitivities");
      this._sensitivities = sensitivities;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(64);
      buf.append("CurveSensitivities.Builder{");
      buf.append("sensitivities").append('=').append(JodaBeanUtils.toString(_sensitivities));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
