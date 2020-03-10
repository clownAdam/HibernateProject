package com.myxq;

import com.myxq.domain.Customer;
import com.myxq.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.io.Serializable;

/**
 * @author clown
 */
public class Hibernate2Test{
    @Test
    public void test(){
        Session session = HibernateUtil.openSession();
        Session session2 = HibernateUtil.openSession();
        System.out.println(session);
        System.out.println(session2);
        Transaction transaction = session.beginTransaction();

        Customer customer = session.get(Customer.class, 3L);
        //会自动把数据存放到一级缓存中
        System.out.println(customer);
        customer.setCust_name("5555555----------++++++");
        //先从一级缓存中查看有没有数据，如果有则直接使用一级缓存中的数据。
        Customer customer2 = session.get(Customer.class, 3L);
        System.out.println(customer2);

        System.out.println(customer == customer2);

        transaction.commit();
        session.close();
    }

    @Test
    public void test2(){
        Session session = HibernateUtil.openSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = new Customer();
        customer.setCust_name("myxq000");

        Serializable id = session.save(customer);
        System.out.println(id);

        Customer customer1 = session.get(Customer.class, id);

        System.out.println(customer == customer1);

        transaction.commit();
        session.close();
    }
    @Test
    public void test3(){
        Session session = HibernateUtil.getCurrenSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = new Customer();
        customer.setCust_name("myxq000");

        Serializable id = session.save(customer);
        System.out.println(id);

        Customer customer1 = session.get(Customer.class, id);

        System.out.println(customer == customer1);

        transaction.commit();
    }
}
