package com.netty.serializer;

import com.alibaba.fastjson.JSON;

import java.io.IOException;

/**
 * @author mohuangNPC
 * @version 1.0
 * @date 2020/7/30 10:53
 */
public class JSONSerializer implements Serializer {

    public byte[] serialize(Object object) throws IOException {
        return JSON.toJSONBytes(object);
    }

    public <T> T deserialize(Class<T> clazz, byte[] bytes) throws IOException {
        return JSON.parseObject(bytes, clazz);
    }
}
