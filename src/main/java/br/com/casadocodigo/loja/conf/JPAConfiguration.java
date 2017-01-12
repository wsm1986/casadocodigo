package br.com.casadocodigo.loja.conf;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
public class JPAConfiguration {

	
	
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.setDataSource(dataSource);

        Properties props = additionalProperties();

        factoryBean.setJpaProperties(props);
        factoryBean.setPackagesToScan("br.com.casadocodigo.loja.models");

        return factoryBean;
    }
	
	@Bean
	public DataSource dataSource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUsername("wmoreira");
		dataSource.setPassword("well1986");
		dataSource.setUrl("jdbc:mysql://localhost:3306/casadocodigo");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	    return dataSource;
	}
	
	@Bean
	@Profile("dev")
	public Properties additionalProperties() {
	    Properties props = new Properties();
	    props.setProperty("hibernate.dialect","org.hibernate.dialect.MySQL5Dialect");
	    props.setProperty("hibernate.show_sql", "true");
	    props.setProperty("hibernate.hbm2ddl.auto", "update");
	    return props;
	}
	/*@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

		JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();

		factoryBean.setJpaVendorAdapter(jpaVendorAdapter);

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUsername("wmoreira");
		dataSource.setPassword("well1986");
		dataSource.setUrl("jdbc:mysql://localhost:3306/casadocodigo");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");

		factoryBean.setDataSource(dataSource);

		Properties props = new Properties();
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		props.setProperty("hibernate.show_sql", "true");
		props.setProperty("hibernate.hbm2ddl.auto", "update");

		factoryBean.setJpaProperties(props);

		factoryBean.setPackagesToScan("br.com.casadocodigo.loja.models");

		return factoryBean;

	}*/

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}
}