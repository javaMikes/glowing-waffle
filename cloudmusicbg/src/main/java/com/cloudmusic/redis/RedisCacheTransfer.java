package com.cloudmusic.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/****

@����: ��̬ע���м���
 * @��Ȩ: Copyright (c) 2016
 * @����: xiad
 * @�汾: 1.0
 * @��������: 2016��3��2��
 * @����ʱ��: ����8:02:57
 */
 public class RedisCacheTransfer {

 @Autowired
 public void setJedisConnectionFactory(JedisConnectionFactory
 jedisConnectionFactory) {
 RedisCache.setJedisConnectionFactory(jedisConnectionFactory);
 }

 }
