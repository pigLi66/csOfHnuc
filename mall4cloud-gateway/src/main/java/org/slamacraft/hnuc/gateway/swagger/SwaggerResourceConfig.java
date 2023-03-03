package org.slamacraft.hnuc.gateway.swagger;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Primary
@Component
@RequiredArgsConstructor
public class SwaggerResourceConfig implements SwaggerResourcesProvider {
    public static final String API_URI = "/v2/api-docs";
    private final RouteLocator routeLocator;
    private final GatewayProperties properties;

    @Override
    public List<SwaggerResource> get() {
        List<String> routes = Lists.newArrayList();

        routeLocator.getRoutes().subscribe(it -> routes.add(it.getId()));

        return properties.getRoutes().stream()
                .filter(it -> routes.contains(it.getId()))
                .flatMap(it -> it.getPredicates().stream()
                        .filter(predicate -> "Path".equalsIgnoreCase(predicate.getName()))
                        .map(predicate -> swaggerResource(it.getId(),
                                predicate.getArgs()
                                        .get(NameUtils.GENERATED_NAME_PREFIX + "0")
                                        .replace("**", "v2/api-docs"))
                        )
                ).collect(Collectors.toList());
    }

    private SwaggerResource swaggerResource(String name, String location) {
        log.info("name:{}, location:{}", name, location);
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
