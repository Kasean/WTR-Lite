package com.epolsoft.wtr.config;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Pageable;
import springfox.documentation.builders.AlternateTypeBuilder;
import springfox.documentation.builders.AlternateTypePropertyBuilder;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRuleConvention;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

@Configuration
@EnableSwagger2
@ComponentScan(basePackages = "com.epolsoft.wtr.controller")
public class SwaggerConfiguration {

    @Value("${app.version:0.1}")
    private String appVersion;


    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.epolsoft.wtr.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder().title("WTR Lite")
                .description("This is WTR Lite swagger API")
                .version(appVersion)
                .build();
    }

    @Bean
    public AlternateTypeRuleConvention pageableConvention(final TypeResolver resolver) {
        return new AlternateTypeRuleConvention() {

            @Override
            public int getOrder() {
                return Ordered.HIGHEST_PRECEDENCE;
            }

            @Override
            public List<AlternateTypeRule> rules() {
                return Collections.singletonList(
                        newRule(resolver.resolve(Pageable.class), resolver.resolve(pageableMixin())));
            }
        };
    }

    private Type pageableMixin() {
        return new AlternateTypeBuilder().fullyQualifiedClassName(String.format("%s.generated.%s",
                Pageable.class.getPackage()
                        .getName(), Pageable.class.getSimpleName()))
                .withProperties(Arrays.asList(property(Integer.class, "page"), property(Integer.class, "size"),
                        property(String.class, "sort")))
                .build();
    }

    private AlternateTypePropertyBuilder property(Class<?> type, String name) {
        return new AlternateTypePropertyBuilder().withName(name)
                .withType(type)
                .withCanRead(true)
                .withCanWrite(true);
    }
}
