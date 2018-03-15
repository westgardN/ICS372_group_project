package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog;

import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import edu.metrostate.ics372.thatgroup.clinicaltrial.Clinic;
import edu.metrostate.ics372.thatgroup.clinicaltrial.Trial;
import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.dao.ClinicDao;
import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.dao.ClinicDaoImpl;
import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.dao.PatientDao;
import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.dao.PatientDaoImpl;
import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.dao.ReadingDao;
import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.dao.ReadingDaoImpl;
import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.dao.TrialDao;
import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.dao.TrialDaoImpl;
import edu.metrostate.ics372.thatgroup.clinicaltrial.patient.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.reading.Reading;

@Configuration 
@EnableTransactionManagement
public class ClinicalTrialCatalogHibernateConfiguration {
	@Bean  
    public TrialDao trialDao() {  
        return new TrialDaoImpl();
    }
	
	@Bean  
    public ClinicDao clinicDao() {  
        return new ClinicDaoImpl(); 
    }
	
	@Bean  
    public PatientDao patientDao() {  
        return new PatientDaoImpl();  
    }
	
	@Bean  
    public ReadingDao readingDao() {  
        return new ReadingDaoImpl();  
    }
	
	@Bean
	public HibernateTemplate hibernateTemplate() {
		return new HibernateTemplate(sessionFactory());
	}
	
	@Bean
	public SessionFactory sessionFactory() {
		return new LocalSessionFactoryBuilder(getDataSource())
		   .addAnnotatedClasses(Trial.class, Clinic.class, Patient.class, Reading.class)
		   .buildSessionFactory();
	}
	
	@Bean
	public DataSource getDataSource() {
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
		final String CONNECTOR_PREFIX = "jdbc:sqlite:";
		final String CATALOG_EXTENSION = ".db";
		String storagePath = ClinicalTrialCatalogUtilIty.getEnvironmentSpecificStoragePath();
		String currentCatalog = ClinicalTrialCatalogUtilIty.currentCatalogName;
        dataSourceBuilder.driverClassName("org.sqlite.JDBC");
        dataSourceBuilder.url(CONNECTOR_PREFIX + storagePath + currentCatalog.concat(CATALOG_EXTENSION));
        return dataSourceBuilder.build();   
	}
	
	@Bean
	public HibernateTransactionManager hibTransMan(){
		return new HibernateTransactionManager(sessionFactory());
	}
}
