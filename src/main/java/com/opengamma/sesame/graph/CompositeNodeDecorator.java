/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sesame.graph;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.opengamma.util.ArgumentChecker;

/**
 * TODO this badly needs a test, it was quietly broken
 */
public class CompositeNodeDecorator implements NodeDecorator, AutoCloseable {

  private static final Logger s_logger = LoggerFactory.getLogger(CompositeNodeDecorator.class);

  private final List<NodeDecorator> _decorators;

  public CompositeNodeDecorator(List<NodeDecorator> decorators) {
    this(decorators.toArray(new NodeDecorator[decorators.size()]));
  }

  public CompositeNodeDecorator(NodeDecorator... decorators) {
    // reverse the decorators so the first decorator is the first one presented with the argument
    // it's arguable which way round is least surprising. this makes most sense to me
    List<NodeDecorator> reversed = Lists.newArrayList(ArgumentChecker.notNull(decorators, "decorators"));
    Collections.reverse(reversed);
    _decorators = ImmutableList.copyOf(reversed);
  }

  @Override
  public Node decorateNode(Node node) {
    Node wrappedNode = node;
    for (NodeDecorator decorator : _decorators) {
      wrappedNode = decorator.decorateNode(wrappedNode);
    }
    return wrappedNode;
  }

  @Override
  public void close() {
    for (NodeDecorator decorator : _decorators) {
      if (decorator instanceof AutoCloseable) {
        try {
          ((AutoCloseable) decorator).close();
        } catch (Exception e) {
          s_logger.warn("Exception closing decorator", e);
        }
      }
    }
  }
}
