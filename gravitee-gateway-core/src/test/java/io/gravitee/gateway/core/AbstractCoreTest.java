/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.gateway.core;

import io.gravitee.gateway.core.spring.CoreConfiguration;
import io.vertx.core.Vertx;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author David BRASSELY (brasseld at gmail.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public abstract class AbstractCoreTest implements ApplicationContextAware {

    protected ApplicationContext applicationContext;

    public AbstractCoreTest() {
        try {
            System.setProperty("gravitee.home",
                    URLDecoder.decode(AbstractCoreTest.class.getResource("/").getPath(), "UTF-8"));

        System.setProperty("gravitee.conf",
                URLDecoder.decode(AbstractCoreTest.class.getResource("/gravitee.yml").getPath(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Configuration
    @Import({CoreConfiguration.class})
    static class ContextConfiguration {

        // This bean is needed by HTTP client
        @Bean
        public Vertx vertx() {
            return Vertx.vertx();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
