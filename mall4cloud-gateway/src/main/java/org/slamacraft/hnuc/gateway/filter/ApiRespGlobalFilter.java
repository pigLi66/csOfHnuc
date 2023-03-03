package org.slamacraft.hnuc.gateway.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.slamacraft.hnuc.gateway.swagger.SwaggerResourceConfig;
import org.slamacraft.hnuc.gateway.util.ObjectMapperUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@AllArgsConstructor
@Component
public class ApiRespGlobalFilter implements GlobalFilter {

    private List<Function<String, Boolean>> excludePathList = List.of(
            path -> StringUtils.endsWithIgnoreCase(path, SwaggerResourceConfig.API_URI),
            path -> path.contains("websocket"),
            path -> path.contains("poserver.zz"),
            path -> path.contains("office")
    );

    static final String IS_CUSTOM_RESP = "custom-resp";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        var path = request.getURI().getPath();

        // 排除的路径
        if (excludePathList.stream().anyMatch(it -> it.apply(path))) {
            return chain.filter(exchange);
        }

        // 如果出错了，就原样返回
        ServerHttpResponse response = exchange.getResponse();
        if (response.getStatusCode() != HttpStatus.OK) {
            return chain.filter(exchange);
        }

        new ResponseDecorator(response);

        return null;
    }
}

@Slf4j
class ResponseDecorator extends ServerHttpResponseDecorator {

    public ResponseDecorator(ServerHttpResponse delegate) {
        super(delegate);
    }

    @Override
    public boolean setStatusCode(HttpStatus status) {
        return super.setStatusCode(HttpStatus.OK);
    }

    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
        ServerHttpResponse serverHttpResponse = this.getDelegate();
        MediaType contentType = serverHttpResponse.getHeaders().getContentType();

        // 如果没有指定类型，返回默认响应
        if (contentType == null) {
            return defaultResp(serverHttpResponse);
        }

        return super.writeWith(Flux.from(body).buffer().map(dataBuffers -> {
            String content = readBody(dataBuffers);
            log.debug("响应内容:{}", content);

            boolean isCustomResp = Optional.ofNullable(getDelegate().getHeaders().get(ApiRespGlobalFilter.IS_CUSTOM_RESP))
                    .flatMap(it -> it.stream().findFirst())
                    .map(it->it.equals("true"))
                    .orElse(false);

            if(isCustomResp){
                // 自定义响应，原样返回
                return wrapData(content.getBytes(getContentCharset(contentType)));
            } else if (contentType.equalsTypeAndSubtype(MediaType.TEXT_HTML)){
                // 重定向响应
                return redirectDataBuffer(content);
            }else if(contentType.includes(MediaType.APPLICATION_JSON) || contentType.includes(MediaType.APPLICATION_JSON_UTF8)){
                // 包装系统响应
                return wrapRespDataBuffer(content, contentType);
            }else {
                // 原样返回
                return wrapData(content.getBytes(getContentCharset(contentType)));
            }
        }));
    }

    private Mono<Void> defaultResp(ServerHttpResponse serverHttpResponse) {
        String resp = "{\"success\":true,\"code\":\"200\",\"message\":\"成功\"}";
        byte[] newResp = resp.getBytes(StandardCharsets.UTF_8);
        serverHttpResponse.setStatusCode(HttpStatus.OK);
        serverHttpResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        serverHttpResponse.getHeaders().setContentLength(newResp.length);
        return serverHttpResponse.writeWith(Flux.just(serverHttpResponse.bufferFactory().wrap(newResp)));
    }

    private String readBody(List<? extends DataBuffer> dataBuffers) {
        ServerHttpResponse delegate = getDelegate();
        DataBuffer join = delegate.bufferFactory().join(dataBuffers);
        byte[] content = new byte[join.readableByteCount()];
        join.read(content);
        DataBufferUtils.release(join); // 释放buffer

        MediaType contentType = delegate.getHeaders().getContentType();
        return new String(content, getContentCharset(contentType));
    }

    private DataBuffer redirectDataBuffer(String content){
        final String redirectCommand = "redirect:";
        int redirectCommandIndex = content.indexOf(redirectCommand);
        if (redirectCommandIndex >= 0) {
            String redirectUri = content.substring(redirectCommandIndex + redirectCommand.length()).trim();
            getDelegate().setStatusCode(HttpStatus.SEE_OTHER);
            getDelegate().getHeaders().set(HttpHeaders.LOCATION, redirectUri);
        }
        return wrapData(content.getBytes());
    }

    private DataBuffer wrapRespDataBuffer(String content, MediaType contentType){

        ObjectMapper jsonMapper = ObjectMapperUtil.objectMapper;

        ObjectNode warpNode = jsonMapper.createObjectNode();
        warpNode.put("success", true);
        warpNode.put("code", "200");
        warpNode.put("message", "成功");

        try{
            JsonNode respJson = jsonMapper.readTree(content);
            JsonNode statusNode = respJson.path("status");
            JsonNode messageNode = respJson.path("message");
            JsonNode errorNode = respJson.path("error");
            JsonNode pathNode = respJson.path("path");
            if (statusNode.isNumber()
                    && errorNode.isTextual()
                    && (messageNode.isMissingNode() || messageNode.isNull() || messageNode.isTextual())
                    && pathNode.isTextual()){
                // 如果是错误响应，返回错误消息
                warpNode.put("success", false);

                var errorCode = respJson.path("errorCode").asText(statusNode.asText("500"));
                warpNode.put("code", errorCode);

                var message = respJson.path("message").asText("系统开小差，请联系管理员！");
                warpNode.put("message", message);
                warpNode.set("data", respJson.path("data"));

                return wrapData(jsonMapper.writeValueAsBytes(warpNode));
            }

            warpNode.set("data", respJson);
            return wrapData(jsonMapper.writeValueAsBytes(warpNode));
        }catch (Exception e){
            log.error("Gateway包装response出错",e);
            String errResp = "{\"success\":false,\"code\":\"500\",\"message\":\"System Error\"}";
            byte[] newRs = errResp.getBytes(getContentCharset(contentType));
            return wrapData(newRs);
        }
    }

    private DataBuffer wrapData(byte[] bytes){
        ServerHttpResponse delegate = getDelegate();
        delegate.getHeaders().setContentLength(bytes.length);
        return delegate.bufferFactory().wrap(bytes);
    }

    private Charset getContentCharset(MediaType contentType){
        return Objects.nonNull(contentType)
                ? contentType.getCharset()
                : StandardCharsets.UTF_8;
    }
}