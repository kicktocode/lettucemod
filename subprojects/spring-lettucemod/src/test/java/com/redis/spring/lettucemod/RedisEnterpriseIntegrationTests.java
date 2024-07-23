package com.redis.spring.lettucemod;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.junit.jupiter.Container;

import com.redis.enterprise.Database;
import com.redis.enterprise.RedisModule;
import com.redis.enterprise.testcontainers.RedisEnterpriseContainer;
import com.redis.lettucemod.cluster.RedisModulesClusterClient;

@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
@EnabledOnOs(value = OS.LINUX)
class RedisEnterpriseIntegrationTests {

	private static final Database database = Database.builder().name("ModulesTests").memoryMB(110).ossCluster(true)
			.modules(RedisModule.SEARCH, RedisModule.JSON, RedisModule.TIMESERIES).build();

	@Autowired
	private RedisModulesClusterClient client;

	@Container
	private static final RedisEnterpriseContainer container = new RedisEnterpriseContainer(
			RedisEnterpriseContainer.DEFAULT_IMAGE_NAME.withTag("latest")).withDatabase(database);

	@DynamicPropertySource
	static void redisProperties(DynamicPropertyRegistry registry) {
		container.start();
		registry.add("spring.redis.url", container::getRedisURI);
		registry.add("spring.redis.cluster.enabled", () -> true);
	}

	@Test
	void connectionTest() {
		Assertions.assertEquals("PONG", client.connect().sync().ping());
	}
}