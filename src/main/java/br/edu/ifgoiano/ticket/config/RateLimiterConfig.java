package br.edu.ifgoiano.ticket.config;

import br.edu.ifgoiano.ticket.controller.dto.request.MessageResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;

public class RateLimiterConfig  implements Filter {

    private final Bucket bucket;

    public RateLimiterConfig() {
        Bandwidth limit = Bandwidth.classic(10, Refill.greedy(5, Duration.ofMinutes(1)));
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (bucket.tryConsume(1)) {
            chain.doFilter(request, response);
        } else {
            int retryAfterSeconds = 60;
            httpResponse.setStatus(429);
            httpResponse.setContentType("application/json");
            httpResponse.setHeader("Retry-After", String.valueOf(retryAfterSeconds));

            MessageResponseDTO messageResponse = new MessageResponseDTO(
                    "Too Many Requests",
                    429,
                    "Muitas solicitações. Limite de taxa excedido."
            );

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(messageResponse);

            httpResponse.getWriter().write(jsonResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
