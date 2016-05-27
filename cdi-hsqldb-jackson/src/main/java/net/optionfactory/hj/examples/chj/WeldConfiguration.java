package net.optionfactory.hj.examples.chj;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;
import net.optionfactory.hj.JsonDriver;
import net.optionfactory.hj.jackson.JacksonJsonDriver;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.HSQLDialect;
import org.hsqldb.jdbc.JDBCPool;

@ApplicationScoped
public class WeldConfiguration {

    @Produces
    public JDBCPool dataSource() {
        final JDBCPool pool = new JDBCPool();
        pool.setUrl("jdbc:hsqldb:mem:test");
        pool.setUser("sa");
        pool.setPassword("");
        return pool;
    }

    @Produces
    public SessionFactory sessionFactory(DataSource dataSource) throws IOException, Exception {
        Configuration c = new Configuration();
        final Properties hp = new Properties();
        hp.put("hibernate.dialect", HSQLDialect.class.getName());
        hp.put("hibernate.hbm2ddl.auto", "create-drop");
        hp.put(Environment.DATASOURCE, dataSource);
        c.setProperties(hp);
        c.addAnnotatedClass(Person.class);
        return c.buildSessionFactory();

    }

    @Produces
    public JsonDriver jsonDriver() {
        return new JacksonJsonDriver(new ObjectMapper());
    }

    public void closeSessionFactory(@Disposes SessionFactory hibernate) {
        hibernate.close();
    }

    public void closeDataSource(@Disposes JDBCPool ds) throws SQLException {
        ds.close(1);
    }

}
