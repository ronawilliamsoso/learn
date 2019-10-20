package com.example.demo.demo;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.demo.model.HibernateStatus;
import javax.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.TransientObjectException;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.HSQLDialect;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;


public class HibernateStatusTest{


  static SessionFactory sessionFactory;

  Session session;

  @BeforeClass
  public static void setUpConnnection(){

    Configuration configuration = new Configuration().addAnnotatedClass(HibernateStatus.class)
                                                     .setProperty("hibernate.dialect",HSQLDialect.class.getName())
                                                     .setProperty("hibernate.connection.driver_class",org.hsqldb.jdbcDriver.class.getName())
                                                     .setProperty("hibernate.connection.url","jdbc:hsqldb:mem:test")
                                                     .setProperty("hibernate.connection.username","sa")
                                                     .setProperty("hibernate.connection.password","").setProperty("hibernate.hbm2ddl.auto","update");
    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
    sessionFactory = configuration.buildSessionFactory(serviceRegistry);

  }

  @Before
  public void initSession(){
    session = sessionFactory.openSession();
    session.beginTransaction();

  }

/*
  在Hibernate中，持久性上下文由org.hibernate.Session实例表示,在JPA中，它是javax.persistence.EntityManager。
   持久性上下文可以看作是会话期间加载或保存到数据库的所有对象的容器或一级缓存,这些对象有三种状态：
  transient -新创建一个对象，还没有保存到数据库
  persistent -该实例与唯一的Session对象相关联；并保证在数据库中有一条相应一致的记录;  相当于 online。 当一个对象处于 online 状态，当这个实体的属性被改变，无需用 save，update，persist，saveorupdate 等方法保存实体，
     只需 1.commit 一个事物 2.flush 3.关闭当前 session
  detached —该实例曾经附加到会话（处于持久状态），但现在却没有；如果您从上下文中退出实例，清除或关闭会话或将实例通过序列化/反序列化过程放置，则实例将进入此状态。 相当于 offline，实体的改变不会被追踪
*/

  @Test
  @DisplayName("persist只能将临时对象转为持久状态")
  public void persistTest(){

    // transient
    HibernateStatus status = HibernateStatus.builder().userName("Wei").build();

/*
    persist 是符合 JPA 规范的保存，它的定义:
    1.将一个临时的实例变为持久状态,并且级联操作其所有设置为 cascade=PERSIST or cascade=ALL 的对象，
    2.如果实例已经是持久性的，则此调用对该特定实例无效（但仍会级联操作为其与cascade = PERSIST或cascade = ALL的关系），
    3.如果实例是分离的，则报错，通常提示需要 commit或刷新session
*/

    session.persist(status);//数据提交到缓存，同时产生主键 id 的值
    session.getTransaction().commit();//commit 导致数据正式插入（产生insert 语句）到数据库
    session.close(); // 释放JDBC connection

    session = sessionFactory.openSession();
    session.beginTransaction();

    assertNotNull(session.get(HibernateStatus.class,status.getUserId()));
  }

  @Test
  @DisplayName("创建新对象：persist一个游离对象会报错")
  public void persistADetachedEntityException(){
    // transient
    HibernateStatus status = HibernateStatus.builder().userName("Wang").build();

/*
    如果persist 一个游离的实体，则报错，
    javax.persistence.PersistenceException: org.hibernate.PersistentObjectException: detached entity passed to persist
*/
    session.persist(status);//数据提交到缓存，同时产生主键 id 的值
    session.evict(status);//
    assertThrows(PersistenceException.class,() -> session.persist(status));

  }

  @Test
  @DisplayName("创建新对象：对游离对象 save 会产生重复对象和重复数据")
  public void saveTest(){

    HibernateStatus status = HibernateStatus.builder().userName("Wang").build();
    /*
     *save 是不符合 JPA 规范的 hibernate 函数，它跟 persist 的保存效果一样，不一样的地方:
     * 1.是会返回主键，persist 返回空
     * 2.对一个游离的实体进行 save 操作，不仅不会报错，还会产生一个新的实体，session 关闭或者 flash 之后，数据库里有两条重复的数据（只是主键不一样）
   */
    Long userId1 = (Long) session.save(status);
    session.evict(status);
    Long userId2 = (Long) session.save(status);

    assertNotEquals(userId1, userId2);
  }

  @Test
  @DisplayName("创建或者修改对象：如果已经存在则将当前对象的属性复制到已存在的对象身上")
  public void mergeTest(){

   /* merge 用于游离的实体字段值更新持久化实体的字段：
    1. 通过从传递的对象(hibernateStatus_old)中获取的ID查找一个实体实例(hibernateStatus_new)（从持久性上下文中检索一个现有的实体实例，或者从数据库中加载一个新的实例）；
    2.将字段从传递的对象(hibernateStatus_old)复制到刚查找出的此实例(hibernateStatus_new)；
    3.返回新更新的实例(hibernateStatus_new)。

    它对数据的操作方法：
    1.如果实体是游离的，则将其复制到现有的持久性实体上（update）；
    2.如果实体是临时的，则将其复制到新创建的持久性实体上（create）,会创建新的主键 id；
    3.对于（cascade=MERGE or cascade=ALL）的所有关系，级联操作。
    4.如果实体是持久性的，则此方法调用没有效果（但仍会进行级联）
    */

    HibernateStatus hibernateStatus_old = HibernateStatus.builder().userName("Wang").build();
    session.save(hibernateStatus_old);
    session.evict(hibernateStatus_old);

    hibernateStatus_old.setUserName("Anna");// 对游离态的实体进行更新

    HibernateStatus hibernateStatus_new = (HibernateStatus) session.merge(hibernateStatus_old);

    assertEquals("Anna", hibernateStatus_new.getUserName());
    assertNotEquals(hibernateStatus_old.getUserId(), hibernateStatus_new.getUserId());


  }


  @Test
  @DisplayName("修改对象：严格不能新建对象")
  public void updateTest(){
  /* update 是原生的 hibernate 函数，它：
    1.将游离对象转为持久化对象（所以从始至终，都只有一个对象存在，merge 则有两个）
    2.对临时对象报错，这一点跟 merge 不同
    3.返回为空

    它对数据的操作过程是：
    1.如果对象是游离的，那么让它重新上线（online），并更新
    2.如果对象是临时的，则报错（因为想要被更新的对象不存在），并不会直接创建新的实体对象
  */

    HibernateStatus status1 = HibernateStatus.builder().userName("Wang").build();

    session.persist(status1);
    session.evict(status1);
    status1.setUserName("Anna");

    session.update(status1);

    HibernateStatus status2  = session.get(HibernateStatus.class, status1.getUserId());
    assertEquals(status1.getUserId(), status2.getUserId()); //status1 和 status2 其实是同一个对象


    HibernateStatus status3 = HibernateStatus.builder().userName("Kamitz").build();
     //TransientObjectException: The given object has a null identifier
    assertThrows(TransientObjectException.class, ()->session.update(status3));

  }

  @Test
  @DisplayName("讲对象上线的一种通用工具：将临时对象或者游离对象转为持久状态，从不产生新对象")
  public void saveOrUpdateTest(){

   /*    hibernate 的原生函数
     一种将将临时对象或者游离对象转为持久状态的通用工具
     如果对象是临时的，则持久之（create）
    如果对象是游离的，则重新上线（还是同一个对象）
    */

    HibernateStatus status1 = HibernateStatus.builder().userName("Wang").build();
    session.saveOrUpdate(status1);
    assertNotNull(session.get(HibernateStatus.class, status1.getUserId()));

    session.evict(status1);

    status1.setUserName("Anna");
    session.saveOrUpdate(status1);

    assertEquals("Anna", session.get(HibernateStatus.class, status1.getUserId()).getUserName());

  }




  @After
  public void tearDown() {
   // session.getTransaction().commit();
    session.close();
  }

  @AfterClass
  public static void afterTests() {
    sessionFactory.close();
  }



}
