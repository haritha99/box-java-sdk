package com.box.sdk;

import com.eclipsesource.json.Json;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class SetupApiExtension implements BeforeEachCallback {
    @Override
    public void beforeEach(ExtensionContext context) throws IllegalAccessException {
        List<Object> allInstances = context.getRequiredTestInstances().getAllInstances();
        String jwtJavaEliteGovBase64 = System.getenv("JAVA_JWT_CONFIG");
        String jwtJavaEliteGov = String.valueOf(Base64.getDecoder().decode(jwtJavaEliteGovBase64));
        BoxAPIConnection api = new BoxAPIConnection(BoxConfig.readFrom(Json.parse(jwtJavaEliteGov).asObject()));

        allInstances.forEach(o -> {
            Arrays.stream(o.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(AdminApi.class))
                .forEach(f -> {
                    try {
                        f.set(o, api);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
        });


    }
}
