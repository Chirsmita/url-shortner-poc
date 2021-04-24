package com.context;

import com.repository.ShortCodeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@ConfigurationProperties
public class AppContext {
    @Autowired
    public ShortCodeHandler s;
    private static Long EXPIRYTIME;
    private static ShortCodeHandler handler;
    private static AppContext instance;

    @Value("${expiryTime:60}")
    public static Long getExpiryTime() {
        System.out.println("expiryTime"+EXPIRYTIME);
        return EXPIRYTIME;
    }

    public  void setExpiryTime(Long expiryTime) {
        EXPIRYTIME = expiryTime;
    }

    public static ShortCodeHandler getHandler() {
        return handler;
    }
    public static void setHandler(ShortCodeHandler handler) {
        AppContext.handler = handler;
    }

    @PostConstruct
    public void afterPropertiesSet() {
        handler = s;
    }

}
