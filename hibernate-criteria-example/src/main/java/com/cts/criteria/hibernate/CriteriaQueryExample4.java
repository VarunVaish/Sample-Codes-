package com.cts.criteria.hibernate;

import java.util.List;
import javax.persistence.criteria.*;
import org.hibernate.*;
import org.hibernate.query.Query;

import com.cts.criteria.hibernate.entity.Employee;

public class CriteriaQueryExample4 {

   public static void main(String[] args) {

      Transaction transaction = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         transaction = session.beginTransaction();

         CriteriaBuilder builder = session.getCriteriaBuilder();
         CriteriaQuery<String> query = builder.createQuery(String.class);
         Root<Employee> root = query.from(Employee.class);
         query.select(root.get("name"));
         Query<String> q=session.createQuery(query);
         List<String> list=q.getResultList();
         for (String name : list) {
            System.out.println(name);
         }

         transaction.commit();
      } catch (Exception e) {
         e.printStackTrace();
         if (transaction != null) {
            transaction.rollback();
         }
      }
   }
}
