package com.redis.lettucemod.api.sync;

import com.redis.lettucemod.api.StatefulRedisModulesConnection;

import io.lettuce.core.api.sync.RedisCommands;

public interface RedisModulesCommands<K, V>
		extends RedisCommands<K, V>, RediSearchCommands<K, V>, RedisTimeSeriesCommands<K, V>, RedisBloomCommands<K, V> {

	@Override
	StatefulRedisModulesConnection<K, V> getStatefulConnection();

}
