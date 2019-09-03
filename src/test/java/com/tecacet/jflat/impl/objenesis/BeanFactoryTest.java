package com.tecacet.jflat.impl.objenesis;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import com.tecacet.jflat.domain.ClassicQuote;
import com.tecacet.jflat.domain.ImmutableQuote;

class BeanFactoryTest {

    @Test
    public void testNormalConstructor() {
        BeanFactory<ClassicQuote> beanFactory = new BeanFactory<>(ClassicQuote.class);
        ClassicQuote bean = beanFactory.get();
        assertEquals(ClassicQuote.class, bean.getClass());
    }

    @Test
    public void testNoDefaultConstructor() {
        BeanFactory<ImmutableQuote> beanFactory = new BeanFactory<>(ImmutableQuote.class);
        ImmutableQuote bean = beanFactory.get();
        assertEquals(ImmutableQuote.class, bean.getClass());
    }
}
