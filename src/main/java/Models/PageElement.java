package Models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME, // use "type" field to identify subclass
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BooleanElement.class, name = "boolean"),
        @JsonSubTypes.Type(value = RadioGroupElement.class, name = "radiogroup"),
})

public abstract class PageElement {

    public String title;
}
