package etc;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE) //TYPE_PARAMETER 범위 포함
@Repeatable(CoffeeContainer.class)
public @interface Coffee {
    String value();
}
