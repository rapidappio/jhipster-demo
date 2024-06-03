package com.huseyinbabal.jdemo.config;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.test.context.ContextConfigurationAttributes;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.ContextCustomizerFactory;
import org.springframework.test.context.MergedContextConfiguration;

public class RedisTestContainersSpringContextCustomizerFactory implements ContextCustomizerFactory {

    private Logger log = LoggerFactory.getLogger(RedisTestContainersSpringContextCustomizerFactory.class);

    private static RedisTestContainer redisBean;

    @Override
    public ContextCustomizer createContextCustomizer(Class<?> testClass, List<ContextConfigurationAttributes> configAttributes) {
        return new ContextCustomizer() {
            @Override
            public void customizeContext(ConfigurableApplicationContext context, MergedContextConfiguration mergedConfig) {
                ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
                TestPropertyValues testValues = TestPropertyValues.empty();
                EmbeddedRedis redisAnnotation = AnnotatedElementUtils.findMergedAnnotation(testClass, EmbeddedRedis.class);
                if (null != redisAnnotation) {
                    log.debug("detected the EmbeddedRedis annotation on class {}", testClass.getName());
                    log.info("Warming up the redis database");
                    if (null == redisBean) {
                        redisBean = beanFactory.createBean(RedisTestContainer.class);
                        beanFactory.registerSingleton(RedisTestContainer.class.getName(), redisBean);
                        // ((DefaultListableBeanFactory)beanFactory).registerDisposableBean(RedisTestContainer.class.getName(), redisBean);
                    }
                    testValues = testValues.and(
                        "jhipster.cache.redis.server=redis://" +
                        redisBean.getRedisContainer().getContainerIpAddress() +
                        ":" +
                        redisBean.getRedisContainer().getMappedPort(6379)
                    );
                }
                testValues.applyTo(context);
            }

            @Override
            public int hashCode() {
                return RedisTestContainer.class.getName().hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                return this.hashCode() == obj.hashCode();
            }
        };
    }
}
