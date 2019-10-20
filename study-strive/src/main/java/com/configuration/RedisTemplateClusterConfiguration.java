package com.configuration;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;



//@Configuration
//@ConditionalOnClass(JedisCluster.class)
public class RedisTemplateClusterConfiguration {
	private Logger logger = LoggerFactory.getLogger(RedisTemplateClusterConfiguration.class);
	
	private Pattern p = Pattern.compile("^.+[:]\\d{1,5}\\s*$");
	
	@Value("${redis.timeout}")
	private int timeout;
	
	@Value("${redis.maxTotal}")
	private int maxTotal;
	
	@Value("${redis.minIdle}")
	private int minIdle;
	
	@Value("${redis.maxIdle}")
	private int maxIdle;
	
	@Value("${redis.maxWaitMillis}")
	private int maxWaitMillis;
	
	@Value("${redis.maxRedirections}")
	private int maxRedirections;
	
	@Value("${redis.testOnBorrow}")
	private boolean testOnBorrow;
	
	@Value("${redis.password}")
	private String password;
	/**
     * 配置 Redis 连接池信息
     */
	@Bean
	public JedisPoolConfig getPoolConfig(){
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(this.maxTotal);
		jedisPoolConfig.setMinIdle(this.minIdle);
		jedisPoolConfig.setMaxIdle(this.maxIdle);
		jedisPoolConfig.setMaxWaitMillis(this.maxWaitMillis);
		jedisPoolConfig.setTestOnBorrow(this.testOnBorrow);
		return jedisPoolConfig;
	}

    /**
     * 配置 Redis Cluster 信息
     * @throws IOException 
     */
    @Bean
    public RedisClusterConfiguration getJedisCluster() throws IOException {
    	ResourceLoader loader = new DefaultResourceLoader();
        InputStream inputStream = loader.getResource("classpath:application-dev.properties")
            .getInputStream();
        Properties properties = new Properties();
        properties.load(inputStream);
        Set<Object> keys = properties.keySet();
        Set<RedisNode> nodes = new HashSet<RedisNode>();
        try {
			for (Object key : keys) {
			    if (String.valueOf(key).startsWith("address")) {
			    	String ipPort = String.valueOf(properties.get(key));
			    	boolean isIpPort = this.p.matcher(ipPort).matches();
					if (!isIpPort) {
						throw new IllegalArgumentException("ip or port error!!");
					}
					String[] ipAndPort = ipPort.split(":");
					logger.info("redis "+ipAndPort[0]+":"+ipAndPort[1]);
					nodes.add(new RedisNode(ipAndPort[0],Integer.parseInt(ipAndPort[1])));
			    }
			}
		} catch (NumberFormatException e) {
			throw e;
		}
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        redisClusterConfiguration.setMaxRedirects(this.maxRedirections);
        redisClusterConfiguration.setClusterNodes(nodes);
        //redisClusterConfiguration.setPassword(this.password);
        redisClusterConfiguration.setPassword("");
        return redisClusterConfiguration;
    }

    /**
     * 配置 Redis 连接工厂
     */
    @Bean
    public JedisConnectionFactory getJedisConnectionFactory(RedisClusterConfiguration redisClusterConfiguration, JedisPoolConfig jedisPoolConfig) {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration, jedisPoolConfig);
        return jedisConnectionFactory;
    }

    /**
     * 设置数据存入redis 的序列化方式
     *  redisTemplate序列化默认使用的jdkSerializeable
     *  存储二进制字节码，导致key会出现乱码，所以自定义序列化类
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        @SuppressWarnings({ "rawtypes", "unchecked" })
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        return redisTemplate;
    }
	
	
}
