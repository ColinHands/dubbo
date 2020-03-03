/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.config.spring.context.annotation;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ConflictingBeanDefinitionException;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;

import java.util.Set;

import static org.springframework.context.annotation.AnnotationConfigUtils.registerAnnotationConfigProcessors;

/**
 * Dubbo {@link ClassPathBeanDefinitionScanner} that exposes some methods to be public.
 *
 * 一个bean定义扫描器，它检测类路径上的候选bean，
 * 使用给定的注册表({@code BeanFactory}或者{@code ApplicationContext})注册相应的bean定义
 *
 * <p>Candidate classes are detected through configurable type filters. The
 *  * default filters include classes that are annotated with Spring's
 *  * {@link org.springframework.stereotype.Component @Component},
 *  * {@link org.springframework.stereotype.Repository @Repository},
 *  * {@link org.springframework.stereotype.Service @Service}, or
 *  * {@link org.springframework.stereotype.Controller @Controller} stereotype.
 *  *
 *  * <p>Also supports Java EE 6's {@link javax.annotation.ManagedBean} and
 *  * JSR-330's {@link javax.inject.Named} annotations, if available.
 *
 * 通过可配置的类型筛选器检测候选类。
 * 默认的过滤器包括用Spring注释的类
 *
 * @see #doScan(String...)
 * @see #registerDefaultFilters()
 * @since 2.5.7
 */
public class DubboClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {


    public DubboClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters, Environment environment,
                                               ResourceLoader resourceLoader) {

        super(registry, useDefaultFilters);

        setEnvironment(environment);

        setResourceLoader(resourceLoader);

        registerAnnotationConfigProcessors(registry);

    }

    public DubboClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, Environment environment,
                                               ResourceLoader resourceLoader) {

        this(registry, false, environment, resourceLoader);

    }

    /**
     * Perform a scan within the specified base packages,
     * returning the registered bean definitions.
     * <p>This method does <i>not</i> register an annotation config processor
     * but rather leaves this up to the caller.
     * 在指定的基本包中执行扫描，
     * 返回注册的bean定义。
     * 此方法不注册注释配置处理器，而是将此任务留给调用者。
     * @param basePackages the packages to check for annotated classes
     * @return set of beans registered if any for tooling registration purposes (never {@code null})
     */
    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        return super.doScan(basePackages);
    }

    /**
     * Check the given candidate's bean name, determining whether the corresponding
     * bean definition needs to be registered or conflicts with an existing definition.
     *
     * 检查给定候选bean的名称，确定是否需要注册对应的bean定义，或者是否与现有定义冲突。
     * @param beanName the suggested name for the bean
     * @param beanDefinition the corresponding bean definition
     * @return {@code true} if the bean can be registered as-is;
     * {@code false} if it should be skipped because there is an
     * existing, compatible bean definition for the specified name
     * @throws ConflictingBeanDefinitionException if an existing, incompatible
     * bean definition has been found for the specified name
     */
    @Override
    public boolean checkCandidate(String beanName, BeanDefinition beanDefinition) throws IllegalStateException {
        return super.checkCandidate(beanName, beanDefinition);
    }

}
