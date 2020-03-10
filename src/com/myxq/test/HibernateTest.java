package com.myxq.test;

import com.myxq.domain.Customer;
import com.myxq.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * @author clown
 */
public class HibernateTest {
    @Test
    public void test1(){
//        加载配置文件
        Configuration conf = new Configuration().configure();
//        创建sessionFactory 相当于jdbc链接
        SessionFactory factory = conf.buildSessionFactory();
//        获取session对象-->jdbc的connection链接对象
        Session session = factory.openSession();
        Customer customer = new Customer();
        customer.setCust_name("myxq");
        customer.setCust_level("2");
//        保存
        session.save(customer);
//        释放资源
        session.close();
        factory.close();
    }
    @Test
    public void test2(){
        // 1.加载Hibernate的核心配置文件
        Configuration configuration = new Configuration().configure();
        // 2.创建一个SessionFactory对象：类似于JDBC中连接池
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        // 3.通过SessionFactory获取到Session对象：类似于JDBC中Connection
        Session session = sessionFactory.openSession();
        // 5.编写代码
        Customer customer = new Customer();
        customer.setCust_name("Myxq2");
        customer.setCust_level("12");
        session.save(customer);
        // 6.资源释放
        session.close();
        sessionFactory.close();
    }
    @Test
    public void test3(){
        Session session = HibernateUtil.openSession();
        Customer customer = new Customer();
        customer.setCust_name("Myxq2");
        customer.setCust_level("12");
        session.save(customer);
        session.close();
        HibernateUtil.sessionFactory.close();
    }

    @Test
    public void test4(){
        Session session = HibernateUtil.openSession();
        /**
         * 查询一条记录
         */
        Customer customer = session.get(Customer.class, 1L);
        //重写Customer的toString()方法
        System.out.println(customer);
        //释放资源
        session.close();
    }
    @Test
    public void test5(){
        Session session = HibernateUtil.openSession();
        //开启事务
        Transaction transaction = session.beginTransaction();
        /**
         * 更新操作
         * 如果没有指定其他的字段，会把其他的字段设置为null
         */
        Customer customer = new Customer();
        customer.setCust_id(1L);
        customer.setCust_name("myxq_update");
        //更新操作
        session.update(customer);
        //提交事务
        transaction.commit();
        //释放资源
        session.close();
    }
    @Test
    public void test6(){
        Session session = HibernateUtil.openSession();
        //开启事务
        Transaction transaction = session.beginTransaction();
        /**
         * 查询之后再修改
         */
        //查询一条记录
        Customer customer = session.get(Customer.class, 1L);
        customer.setCust_name("myxq666");
        //更新操作
        session.update(customer);
        //提交事务
        transaction.commit();
        //释放资源
        session.close();
    }
    @Test
    public void test7(){
        Session session = HibernateUtil.openSession();
        //开启事务
        Transaction transaction = session.beginTransaction();
        /**
         * 直接删除记录
         */
        Customer customer = new Customer();
        customer.setCust_id(1L);
        session.delete(customer);
        //提交事务
        transaction.commit();
        //释放资源
        session.close();
    }
    @Test
    public void test8(){
        Session session = HibernateUtil.openSession();
        //开启事务
        Transaction transaction = session.beginTransaction();
        /**
         * 先查询,后删除
         */
        //查询
        Customer customer = session.get(Customer.class, 2L);
        //删除
        session.delete(customer);
        //事务提交
        transaction.commit();
        //释放资源
        session.close();
    }
    @Test
    public void test9(){
        Session session = HibernateUtil.openSession();
        //开启事务
        Transaction transaction = session.beginTransaction();
        /**
         * 保存或更新操作
         * 未设置ID,则为保存操作(insert)
         * 设置id,则为更新操作(update)
         */
        Customer customer = new Customer();
        customer.setCust_id(6L);
        customer.setCust_name("chensiqi2222---->");
        customer.setCust_level("3");
        session.saveOrUpdate(customer);
        System.out.println("aa"+customer);
        customer.setCust_name("uidhujunjiao");
        //提交事务
        transaction.commit();
        //释放资源
        session.close();
        System.out.println("bbb"+customer);
    }
    @Test
    public void test10(){
        Session session = HibernateUtil.openSession();
        //开启事务
        Transaction transaction = session.beginTransaction();
        /**
         * 使用HQL查询所有记录,不推荐使用
         */
        Query query = session.createQuery("from com.myxq.domain.Customer");
        List<Customer> list = query.list();
        for (Customer customer : list) {
            System.out.println(customer);
        }
        //提交事务
        transaction.commit();
        //释放资源
        session.close();
    }
    @Test
    public void test11(){
        Session session = HibernateUtil.openSession();
        Transaction transaction = session.beginTransaction();
        /**
         *  使用原生SQL查询所有记录
         */
        NativeQuery query = session.createSQLQuery("select * from customer");
        List<Object[]> list = query.list();
        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));
        }
        transaction.commit();
        session.close();
    }
}
