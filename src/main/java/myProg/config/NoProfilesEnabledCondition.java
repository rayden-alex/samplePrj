package myProg.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.lang.NonNull;
import org.springframework.util.MultiValueMap;

public class NoProfilesEnabledCondition implements Condition {
    @Override
    public boolean matches(@NonNull ConditionContext context, @NonNull AnnotatedTypeMetadata metadata) {
        MultiValueMap<String, Object> attrs = metadata.getAllAnnotationAttributes(NoProfilesEnabled.class.getName());

        //todo: replace by Stream API
        //attrs.entrySet().stream().anyMatch()
        if (attrs != null) {
            for (Object value : attrs.get("value")) {
                if (context.getEnvironment().acceptsProfiles((String[]) value)) {
                    return false;
                }
            }
        }
        return true;
    }
}
