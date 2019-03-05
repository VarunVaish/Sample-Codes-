package com.cts.criteria.hibernate;

import java.util.List;
import javax.persistence.criteria.*;
import org.hibernate.*;
import org.hibernate.query.Query;

import com.cts.criteria.hibernate.entity.Employee;

public class CriteriaQueryExample3 {

   public static void main(String[] args) {

      Transaction transaction = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         transaction = session.beginTransaction();

         CriteriaBuilder builder = session.getCriteriaBuilder();
         CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
         Root<Employee> root = query.from(Employee.class);
         query.multiselect(root.get("name"),root.get("designation"));
         Query<Object[]> q=session.createQuery(query);
         List<Object[]> list=q.getResultList();
         for (Object[] objects : list) {
            System.out.println("Name : "+objects[0]);
            System.out.println("Designation : "+objects[1]);
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
