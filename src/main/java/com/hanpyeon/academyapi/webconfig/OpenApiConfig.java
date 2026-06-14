package com.hanpyeon.academyapi.webconfig;


import com.hanpyeon.academyapi.exception.ErrorCode;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "한편의 수학학원 API 문서"),
        servers = {
        @Server(description = "개발용 서버", url = "https://hanapi.hopto.org"),
        @Server(description = "로컬 서버", url = "http://localhost:8081"),
        }
)
public class OpenApiConfig {

    @Bean
    public OpenApiCustomizer errorCodeSchemaCustomiser() {
        return openApi -> {
            Schema<ErrorCode[]> errorCodeSchema = new Schema<>();
            Arrays.stream(ErrorCode.values()).forEach(errorCode -> {
                Schema<?> errorSchema = new ObjectSchema()
                        .addProperty("ERROR NAME", new StringSchema().example(errorCode))
                        .addProperty("httpStatus", new StringSchema().example(String.valueOf(errorCode.getHttpStatus().value())))
                        .addProperty("errorCode", new StringSchema().example(errorCode.getErrorCode()))
                        .addProperty("description", new StringSchema().example(errorCode.getErrorDescription()));
                errorCodeSchema.addProperty(errorCode.getErrorCode(), errorSchema);
            });
            openApi.getComponents().addSchemas("ErrorCode", errorCodeSchema);
        };
    }
}
