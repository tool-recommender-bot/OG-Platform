/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.integration.marketdata.manipulator.dsl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.ImmutableConstructor;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.google.common.collect.ImmutableList;
import com.opengamma.analytics.ShiftType;
import com.opengamma.analytics.financial.model.interestrate.curve.YieldCurve;
import com.opengamma.analytics.financial.model.interestrate.curve.YieldCurveUtils;
import com.opengamma.engine.function.FunctionExecutionContext;
import com.opengamma.engine.marketdata.manipulator.function.StructureManipulator;
import com.opengamma.engine.value.ValueSpecification;
import com.opengamma.util.ArgumentChecker;

/**
 * A manipulator which applies a list of point shifts.
 */
@BeanDefinition
public final class YieldCurvePointShiftManipulator implements ImmutableBean, StructureManipulator<YieldCurve>  {

  /**
   * Shift type
   */
  @PropertyDefinition(validate = "notNull")
  private final ScenarioShiftType _shiftType;

  /**
   * The list of point shifts to apply
   */
  @PropertyDefinition(validate = "notNull")
  private final ImmutableList<YieldCurvePointShift> _pointShifts;

  @ImmutableConstructor
  /* package */ YieldCurvePointShiftManipulator(ScenarioShiftType shiftType, List<YieldCurvePointShift> pointShifts) {
    _shiftType = ArgumentChecker.notNull(shiftType, "shiftType");
    _pointShifts = ImmutableList.copyOf(ArgumentChecker.notNull(pointShifts, "pointShifts"));
  }

  @Override
  public YieldCurve execute(YieldCurve curve, ValueSpecification valueSpec, FunctionExecutionContext executionContext) {
    final List<Double> points = new ArrayList<>();
    final List<Double> shifts = new ArrayList<>();
    ShiftType shiftType = _shiftType.toAnalyticsType();
    Double[] xData = curve.getCurve().getXData();

    for (YieldCurvePointShift shift : _pointShifts) {
      int index = shift.getPointIndex();

      if (index >= xData.length) {
        throw new IllegalArgumentException("Shift index " + index + " is out of bounds, curve has " + xData.length + " points");
      }
      points.add(xData[index]);

      if (shiftType == ShiftType.RELATIVE) {
        // add shifts to 1. i.e. 10.pc actually means 'value * 1.1' and -10.pc means 'value * 0.9'
        shifts.add(shift.getShift() + 1);
      } else {
        shifts.add(shift.getShift());
      }
    }
    return YieldCurveUtils.withPointShifts(curve, points, shifts, shiftType);
  }

  @Override
  public Class<YieldCurve> getExpectedType() {
    return YieldCurve.class;
  }
  
  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code YieldCurvePointShiftManipulator}.
   * @return the meta-bean, not null
   */
  public static YieldCurvePointShiftManipulator.Meta meta() {
    return YieldCurvePointShiftManipulator.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(YieldCurvePointShiftManipulator.Meta.INSTANCE);
  }

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static YieldCurvePointShiftManipulator.Builder builder() {
    return new YieldCurvePointShiftManipulator.Builder();
  }

  @Override
  public YieldCurvePointShiftManipulator.Meta metaBean() {
    return YieldCurvePointShiftManipulator.Meta.INSTANCE;
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
   * Gets shift type
   * @return the value of the property, not null
   */
  public ScenarioShiftType getShiftType() {
    return _shiftType;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the list of point shifts to apply
   * @return the value of the property, not null
   */
  public ImmutableList<YieldCurvePointShift> getPointShifts() {
    return _pointShifts;
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
  public YieldCurvePointShiftManipulator clone() {
    return this;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      YieldCurvePointShiftManipulator other = (YieldCurvePointShiftManipulator) obj;
      return JodaBeanUtils.equal(getShiftType(), other.getShiftType()) &&
          JodaBeanUtils.equal(getPointShifts(), other.getPointShifts());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getShiftType());
    hash += hash * 31 + JodaBeanUtils.hashCode(getPointShifts());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(96);
    buf.append("YieldCurvePointShiftManipulator{");
    buf.append("shiftType").append('=').append(getShiftType()).append(',').append(' ');
    buf.append("pointShifts").append('=').append(JodaBeanUtils.toString(getPointShifts()));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code YieldCurvePointShiftManipulator}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code shiftType} property.
     */
    private final MetaProperty<ScenarioShiftType> _shiftType = DirectMetaProperty.ofImmutable(
        this, "shiftType", YieldCurvePointShiftManipulator.class, ScenarioShiftType.class);
    /**
     * The meta-property for the {@code pointShifts} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<ImmutableList<YieldCurvePointShift>> _pointShifts = DirectMetaProperty.ofImmutable(
        this, "pointShifts", YieldCurvePointShiftManipulator.class, (Class) ImmutableList.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "shiftType",
        "pointShifts");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 893345500:  // shiftType
          return _shiftType;
        case 244906465:  // pointShifts
          return _pointShifts;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public YieldCurvePointShiftManipulator.Builder builder() {
      return new YieldCurvePointShiftManipulator.Builder();
    }

    @Override
    public Class<? extends YieldCurvePointShiftManipulator> beanType() {
      return YieldCurvePointShiftManipulator.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code shiftType} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ScenarioShiftType> shiftType() {
      return _shiftType;
    }

    /**
     * The meta-property for the {@code pointShifts} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ImmutableList<YieldCurvePointShift>> pointShifts() {
      return _pointShifts;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 893345500:  // shiftType
          return ((YieldCurvePointShiftManipulator) bean).getShiftType();
        case 244906465:  // pointShifts
          return ((YieldCurvePointShiftManipulator) bean).getPointShifts();
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
   * The bean-builder for {@code YieldCurvePointShiftManipulator}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<YieldCurvePointShiftManipulator> {

    private ScenarioShiftType _shiftType;
    private List<YieldCurvePointShift> _pointShifts = new ArrayList<YieldCurvePointShift>();

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(YieldCurvePointShiftManipulator beanToCopy) {
      this._shiftType = beanToCopy.getShiftType();
      this._pointShifts = new ArrayList<YieldCurvePointShift>(beanToCopy.getPointShifts());
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 893345500:  // shiftType
          return _shiftType;
        case 244906465:  // pointShifts
          return _pointShifts;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 893345500:  // shiftType
          this._shiftType = (ScenarioShiftType) newValue;
          break;
        case 244906465:  // pointShifts
          this._pointShifts = (List<YieldCurvePointShift>) newValue;
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
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setAll(Map<String, ? extends Object> propertyValueMap) {
      super.setAll(propertyValueMap);
      return this;
    }

    @Override
    public YieldCurvePointShiftManipulator build() {
      return new YieldCurvePointShiftManipulator(
          _shiftType,
          _pointShifts);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the {@code shiftType} property in the builder.
     * @param shiftType  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder shiftType(ScenarioShiftType shiftType) {
      JodaBeanUtils.notNull(shiftType, "shiftType");
      this._shiftType = shiftType;
      return this;
    }

    /**
     * Sets the {@code pointShifts} property in the builder.
     * @param pointShifts  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder pointShifts(List<YieldCurvePointShift> pointShifts) {
      JodaBeanUtils.notNull(pointShifts, "pointShifts");
      this._pointShifts = pointShifts;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(96);
      buf.append("YieldCurvePointShiftManipulator.Builder{");
      buf.append("shiftType").append('=').append(JodaBeanUtils.toString(_shiftType)).append(',').append(' ');
      buf.append("pointShifts").append('=').append(JodaBeanUtils.toString(_pointShifts));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
