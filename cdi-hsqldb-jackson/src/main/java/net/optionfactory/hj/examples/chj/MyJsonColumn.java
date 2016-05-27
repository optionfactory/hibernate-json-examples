package net.optionfactory.hj.examples.chj;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.optionfactory.hj.JsonType;
import net.optionfactory.hj.cdi.CdiDriverLocator;


@JsonType.Conf(
    locator = CdiDriverLocator.class,
    type = JsonType.ColumnType.Text
)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MyJsonColumn {

}
