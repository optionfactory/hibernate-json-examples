package net.optionfactory.hj.examples.smj;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.optionfactory.hj.JsonType;
import net.optionfactory.hj.spring.SpringDriverLocator;

@JsonType.Conf(
        type = JsonType.ColumnType.MysqlJson, 
        locator = SpringDriverLocator.class
)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MyJsonColumn {

}
