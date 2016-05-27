package net.optionfactory.hj.examples.spj;

import java.io.Serializable;
import java.util.Arrays;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.support.TransactionTemplate;

public class Example {

    public static void main(String[] args) {
        try(AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfiguration.class)){
            final TransactionTemplate tt = ctx.getBean(TransactionTemplate.class);
            final SessionFactory hibernate = ctx.getBean(SessionFactory.class);


            final Serializable id = tt.execute(status -> {
                final Person p = new Person();
                p.setName("John");
                p.setHobbies(Arrays.asList("golf", "chess"));
                return hibernate.getCurrentSession().save(p);
            }); 

            tt.execute(status -> {
                final Person loaded = (Person) hibernate.getCurrentSession().get(Person.class, id);
                System.out.format("%s's hobbies: %s%n", loaded.getName(), loaded.getHobbies());
                return null;
            });
        }
    }
}
