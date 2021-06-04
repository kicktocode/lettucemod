package com.redislabs.mesclun.cluster.api.reactive;

import com.redislabs.mesclun.cluster.api.StatefulRedisModulesClusterConnection;
import io.lettuce.core.cluster.api.reactive.RedisAdvancedClusterReactiveCommands;

public interface RedisModulesAdvancedClusterReactiveCommands<K, V> extends RedisAdvancedClusterReactiveCommands<K, V>, RedisModulesClusterReactiveCommands<K, V> {

    RedisModulesClusterReactiveCommands<K, V> getConnection(String nodeId);

    RedisModulesClusterReactiveCommands<K, V> getConnection(String host, int port);

    /**
     * @return the underlying connection.
     */
    StatefulRedisModulesClusterConnection<K, V> getStatefulConnection();
}
