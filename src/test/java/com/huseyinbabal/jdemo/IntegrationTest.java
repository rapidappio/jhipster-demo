package com.huseyinbabal.jdemo;

import com.huseyinbabal.jdemo.config.AsyncSyncConfiguration;
import com.huseyinbabal.jdemo.config.EmbeddedElasticsearch;
import com.huseyinbabal.jdemo.config.EmbeddedRedis;
import com.huseyinbabal.jdemo.config.EmbeddedSQL;
import com.huseyinbabal.jdemo.config.JacksonConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { JDemoApp.class, JacksonConfiguration.class, AsyncSyncConfiguration.class })
@EmbeddedRedis
@EmbeddedElasticsearch
@EmbeddedSQL
public @interface IntegrationTest {
}
