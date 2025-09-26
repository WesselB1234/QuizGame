package Models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME, // use "type" field to identify subclass
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Page.class, name = "page"),
        @JsonSubTypes.Type(value = BooleanElement.class, name = "boolean"),
        @JsonSubTypes.Type(value = RadioGroupElement.class, name = "radiogroup"),
})

public class Page {

    public Integer timeLimit;
    public PageElement pageElement;
}
