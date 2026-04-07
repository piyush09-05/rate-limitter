package com.example.rate_limitter.rate_limitter_poc.interceptor;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
 public class RateLimitInterceptorSlidingWindow implements HandlerInterceptor {
    private final int LIMIT = 5;
    private final long WINDOW_SIZE = 60000;
    private final ConcurrentHashMap<String, Deque<Long>> store = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws  Exception{
        String clientIp = request.getRemoteAddr();
        long now = System.currentTimeMillis();

        if(!store.containsKey(clientIp)){
            store.put(clientIp, new ArrayDeque<>(List.of(now)));
            return true;
        }
        Deque<Long> timeStamps = store.get(clientIp);

        synchronized(timeStamps){

            while(!timeStamps.isEmpty() && now - timeStamps.peek() > WINDOW_SIZE){
                timeStamps.poll();
            }

            if(timeStamps.size() >= LIMIT){
                response.setStatus(429);
                response.getWriter().write("Too Many Requests");
                return false;
            }
            timeStamps.add(now);
            return true;
        }
    }
}
