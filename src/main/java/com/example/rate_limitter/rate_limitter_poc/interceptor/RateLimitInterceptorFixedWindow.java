package com.example.rate_limitter.rate_limitter_poc.interceptor;

import com.example.rate_limitter.rate_limitter_poc.models.WindowData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.ConcurrentHashMap;


@Component
public class RateLimitInterceptorFixedWindow implements HandlerInterceptor {
    private final int LIMIT = 5;
    private final long WINDOW_DURATION_MS = 60000;

    private final ConcurrentHashMap<String, WindowData> store = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clientIp = request.getRemoteAddr();
        long now = System.currentTimeMillis();

        if (!store.containsKey(clientIp)) {
            store.put(clientIp, new WindowData(now));
            return true;
        }

        WindowData windowData = store.get(clientIp);
        synchronized (windowData) {
            boolean isWindowExpired = now - store.get(clientIp).getWindowStartTime() > WINDOW_DURATION_MS;

            if (isWindowExpired) {
                windowData.reset(now);
                return true;
            }

            if (store.get(clientIp).getCount() > LIMIT) {
                response.setStatus(429);
                response.getWriter().write("Too Many Requests");
                return false;
            }
            windowData.incrementCount();
            return true;
        }
    }


}
