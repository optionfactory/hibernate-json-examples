package net.optionfactory.hj.examples.smj;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
import net.optionfactory.hj.JsonDriver;
import net.optionfactory.hj.jackson.JacksonJsonDriver;
import net.optionfactory.hj.mysql.MySQL57InnoDBDialectWithJson;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@EnableSpringConfigured
public class SpringConfiguration {

    @Bean
    public DataSource dataSource() throws SQLException {
        return new SimpleDriverDataSource(new com.mysql.jdbc.Driver(), "jdbc:mysql://localhost/test", "test", "test");
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(final DataSource dataSource) throws IOException, Exception {
        final Properties hp = new Properties();

        final LocalSessionFactoryBean factory = new LocalSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setPackagesToScan(SpringConfiguration.class.getPackage().getName());
        factory.setHibernateProperties(hp);
        hp.put("hibernate.dialect", MySQL57InnoDBDialectWithJson.class.getName());
        hp.put("hibernate.hbm2ddl.auto", "create-drop");
        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sessionFatory) {
        final HibernateTransactionManager txm = new HibernateTransactionManager();
        txm.setSessionFactory(sessionFatory);
        return txm;
    }

    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager ptm) {
        return new TransactionTemplate(ptm);
    }

    @Bean
    public JsonDriver jacksonDriver() {
        return new JacksonJsonDriver(new ObjectMapper());
    }

}
