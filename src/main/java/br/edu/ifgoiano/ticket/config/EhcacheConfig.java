package br.edu.ifgoiano.ticket.config;

import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class EhcacheConfig {

    @Bean(name = "ehcacheManager")
    public CacheManager cacheManager() {
        CacheConfiguration<String, String> cacheConfig = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(100))
                .withExpiry(org.ehcache.config.builders.ExpiryPolicyBuilder.timeToLiveExpiration(java.time.Duration.ofMinutes(30)))
                .build();

        return CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("categoriaCache", cacheConfig)
                .withCache("departamentoCache", cacheConfig)
                .withCache("regraPrioridadeCache", cacheConfig)
                .withCache("ticketCache", cacheConfig)
                .build(true);
    }
}
