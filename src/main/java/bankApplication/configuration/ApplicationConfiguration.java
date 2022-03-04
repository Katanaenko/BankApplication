package bankApplication.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * ApplicationContext Java Configuration.
 */
@Configuration
@ComponentScan(basePackages = "bankApplication")
@EnableWebMvc
@EnableTransactionManagement
@EnableKafka
@PropertySource("classpath:application.properties")
public class ApplicationConfiguration {

    private final Environment env;

    public ApplicationConfiguration(Environment env) {
        this.env = env;
    }

    //Configure DTO validation beans
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    //Configure Hibernate beans
    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(env.getProperty("datasource.driverclass"));
            dataSource.setJdbcUrl(env.getProperty("datasource.jdbcurl"));
            dataSource.setUser(env.getProperty("datasource.user"));
            dataSource.setPassword(env.getProperty("datasource.password"));
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("bankApplication.entity");
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        hibernateProperties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    //Configure liquibase beans
    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:db/changelog/changelog.yaml");
        liquibase.setDataSource(dataSource());
        return liquibase;
    }

    //Configure Kafka beans
    @Bean
    ConcurrentKafkaListenerContainerFactory<String, String>
    kafkaListenerContainerFactory(ConsumerFactory<String, String> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(3);
        return factory;
    }

    @Bean
    public ConsumerFactory<String , String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerProps());
    }

    private Map<String, Object> consumerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("kafka.bootstrapservers"));
        props.put(ConsumerConfig.GROUP_ID_CONFIG, env.getProperty("kafka.groupid"));
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, env.getProperty("kafka.autoiffsetresetconfig"));
        return props;
    }
}
