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
        Bandwidth limit = Bandwidth.classic(100, Refill.greedy(5, Duration.ofMinutes(1)));
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
//
//    private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();
//    private final Bandwidth limit = Bandwidth.classic(60, Refill.greedy(5, Duration.ofMinutes(1)));
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//
//        String userId = getUserIdFromRequest(request);
//
//        if (userId == null) {
//            HttpServletResponse httpResponse = (HttpServletResponse) response;
//            httpResponse.setStatus(400); // Bad Request
//            httpResponse.setContentType("application/json");
//            MessageResponseDTO messageResponse = new MessageResponseDTO(
//                    "Bad Request",
//                    400,
//                    "ID do usuário não fornecido."
//            );
//            ObjectMapper objectMapper = new ObjectMapper();
//            String jsonResponse = objectMapper.writeValueAsString(messageResponse);
//            httpResponse.getWriter().write(jsonResponse);
//            return; // Interrompe o processamento
//        }
//
//        Bucket userBucket = buckets.computeIfAbsent(userId, k -> Bucket.builder().addLimit(limit).build());
//
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//        if (userBucket.tryConsume(1)) {
//            chain.doFilter(request, response);
//        } else {
//            int retryAfterSeconds = 60;
//            httpResponse.setStatus(429);
//            httpResponse.setContentType("application/json");
//            httpResponse.setHeader("Retry-After", String.valueOf(retryAfterSeconds));
//
//            MessageResponseDTO messageResponse = new MessageResponseDTO(
//                    "Too Many Requests",
//                    429,
//                    "Muitas solicitações. Limite de taxa excedido."
//            );
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            String jsonResponse = objectMapper.writeValueAsString(messageResponse);
//
//            httpResponse.getWriter().write(jsonResponse);
//        }
//    }
//
//    @Override
//    public void destroy() {
//    }
//
//    private String getUserIdFromRequest(ServletRequest request) {
//        // Exemplo: obter o ID do usuário de um cabeçalho ou parâmetro de consulta
//        // Ajuste conforme necessário
//        return request.getParameter("userId"); // Exemplo simplificado
//    }
}
