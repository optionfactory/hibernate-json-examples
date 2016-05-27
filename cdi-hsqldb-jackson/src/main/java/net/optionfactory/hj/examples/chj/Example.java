package net.optionfactory.hj.examples.chj;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.function.Function;
import net.optionfactory.hj.JsonDriver;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class Example {

    public static void main(String[] args) throws SQLException {
        try (WeldContainer weld = new Weld().initialize()) {
            final SessionFactory hibernate = weld.instance().select(SessionFactory.class).get();

            final Serializable id = withSessionAndTransaction(hibernate, session -> {
                final Person p = new Person();
                p.setName("John");
                p.setHobbies(Arrays.asList("golf", "chess"));
                return session.save(p);
            });

            withSessionAndTransaction(hibernate, session -> {
                final Person loaded = (Person) session.get(Person.class, id);
                System.out.format("%s's hobbies: %s%n", loaded.getName(), loaded.getHobbies());
                return null;
            });

        }

    }

    private static <T> T withSessionAndTransaction(SessionFactory hibernate, Function<Session, T> fn) {
        try (Session session = hibernate.openSession()) {
            final Transaction tx = session.beginTransaction();
            try {
                T res = fn.apply(session);
                tx.commit();
                return res;
            } catch (RuntimeException ex) {
                tx.rollback();
                throw ex;
            }
        }
    }
}
