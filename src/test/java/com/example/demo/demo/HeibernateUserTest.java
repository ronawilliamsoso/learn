package com.example.demo.demo;


import static org.junit.Assert.assertNotNull;

import com.example.demo.model.HeibernateUser;
import com.example.demo.service.HeibernateUserService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.HSQLDialect;
import org.hibernate.service.ServiceRegistry;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class HeibernateUserTest{

 @Autowired
 HeibernateUserService heibernateStatusUserService;


 static SessionFactory sessionFactory;

 Session session;

 @BeforeClass
 public static void setUpConnnection(){


  Configuration configuration = new Configuration().addAnnotatedClass(HeibernateUser.class)
                                                   .setProperty("hibernate.dialect", HSQLDialect.class.getName())
                                                   .setProperty("hibernate.connection.driver_class",org.hsqldb.jdbcDriver.class.getName())
                                                   .setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:test")
                                                   .setProperty("hibernate.connection.username", "sa")
                                                   .setProperty("hibernate.connection.password", "")
                                                   .setProperty("hibernate.hbm2ddl.auto", "update");
  ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
  sessionFactory = configuration.buildSessionFactory(serviceRegistry);

 }

 @Before
 public void initSession(){
  session = sessionFactory.openSession();
  session.beginTransaction();

 }


 @Test
 public void connectToH2test(){
	 HeibernateUser user = HeibernateUser.builder().name("Wei").company("zoro").department("IT").build();
	 session.persist(user);
	 session.getTransaction().commit();
	 session.close();

	 session = sessionFactory.openSession();
	 session.beginTransaction();

   assertNotNull(session.get(HeibernateUser.class, user.getName()));
 }
}
