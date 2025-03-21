package com.redis.lettucemod.cluster;

import java.time.Duration;
import java.util.function.Supplier;

import com.redis.lettucemod.cluster.api.StatefulRedisModulesClusterConnection;
import com.redis.lettucemod.cluster.api.async.RedisModulesAdvancedClusterAsyncCommands;
import com.redis.lettucemod.cluster.api.reactive.RedisModulesAdvancedClusterReactiveCommands;
import com.redis.lettucemod.cluster.api.sync.RedisModulesAdvancedClusterCommands;

import io.lettuce.core.RedisChannelWriter;
import io.lettuce.core.api.push.PushListener;
import io.lettuce.core.cluster.ClusterPushHandler;
import io.lettuce.core.cluster.StatefulRedisClusterConnectionImpl;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.json.JsonParser;

public class StatefulRedisModulesClusterConnectionImpl<K, V> extends StatefulRedisClusterConnectionImpl<K, V>
		implements StatefulRedisModulesClusterConnection<K, V> {

	/**
	 * Initialize a new connection.
	 *
	 * @param writer      the channel writer
	 * @param pushHandler the Cluster push handler
	 * @param codec       Codec used to encode/decode keys and values.
	 * @param timeout     Maximum time to wait for a response.
	 */
	public StatefulRedisModulesClusterConnectionImpl(RedisChannelWriter writer, ClusterPushHandler pushHandler,
			RedisCodec<K, V> codec, Duration timeout) {
		super(writer, pushHandler, codec, timeout);
	}

	public StatefulRedisModulesClusterConnectionImpl(RedisChannelWriter writer, ClusterPushHandler pushHandler,
			RedisCodec<K, V> codec, Duration timeout, Supplier<JsonParser> parser) {
		super(writer, pushHandler, codec, timeout, parser);
	}

	@Override
	protected RedisModulesAdvancedClusterAsyncCommandsImpl<K, V> newRedisAdvancedClusterAsyncCommandsImpl() {
		return new RedisModulesAdvancedClusterAsyncCommandsImpl<>(this, codec);
	}

	@Override
	protected RedisModulesAdvancedClusterCommands<K, V> newRedisAdvancedClusterCommandsImpl() {
		return clusterSyncHandler(RedisModulesAdvancedClusterCommands.class);
	}

	@Override
	protected RedisModulesAdvancedClusterReactiveCommandsImpl<K, V> newRedisAdvancedClusterReactiveCommandsImpl() {
		return new RedisModulesAdvancedClusterReactiveCommandsImpl<>(this, codec);
	}

	@Override
	public RedisModulesAdvancedClusterCommands<K, V> sync() {
		return (RedisModulesAdvancedClusterCommands<K, V>) super.sync();
	}

	@Override
	public RedisModulesAdvancedClusterAsyncCommands<K, V> async() {
		return (RedisModulesAdvancedClusterAsyncCommands<K, V>) super.async();
	}

	@Override
	public RedisModulesAdvancedClusterReactiveCommands<K, V> reactive() {
		return (RedisModulesAdvancedClusterReactiveCommands<K, V>) super.reactive();
	}

	@Override
	public void addListener(PushListener listener) {
		throw new UnsupportedOperationException("PushListener not supported for cluster connection");
	}

	@Override
	public void removeListener(PushListener listener) {
		throw new UnsupportedOperationException("PushListener not supported for cluster connection");
	}

	@Override
	public boolean isMulti() {
		return false;
	}

}
