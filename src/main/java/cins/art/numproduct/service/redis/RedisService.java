package cins.art.numproduct.service.redis;
import cins.art.numproduct.enums.ResultEnum;
import cins.art.numproduct.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;//注入redis的模板

    @Autowired
    private RedisTemplate redisTemplate;

    public void setString(String key,String value,long time){
        setObject(key,value,time);
    }

    public void setList(String key,List value,long time){
        setObject(key,value,time);
    }



    public void setObject(String key,Object value,long time){
        //redis有几种 String List set zset hash
        if (StringUtils.isEmpty(key)||value==null){
            throw new MyException(ResultEnum.PARAM_ERROR);
        }
        //判断类型 存放String类型
        if(value instanceof String){
            log.info("value:{}",value);
            String strValue = (String)value;
            log.info("strValue:{}",strValue);
            stringRedisTemplate.opsForValue().set(key,strValue,time, TimeUnit.SECONDS);
            return;
        }

        //存放List类型
        if(value instanceof List){
            List<String> listValue = (List<String>) value;
            redisTemplate.opsForList().leftPush(key,value);
        }
    }

    public String getKey(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    public List<String> getList(String key){
        return (List<String>) redisTemplate.opsForList().leftPop(key);
    }

    //判断是否存在当前的key
    public boolean haskey(String key){
        return redisTemplate.hasKey(key);
    }
}

