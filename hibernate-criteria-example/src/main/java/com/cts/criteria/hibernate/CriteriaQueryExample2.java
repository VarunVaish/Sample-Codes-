package com.cts.criteria.hibernate;

import javax.persistence.criteria.*;
import org.hibernate.*;
import org.hibernate.query.Query;

import com.cts.criteria.hibernate.entity.Department;

public class CriteriaQueryExample2 {

   public static void main(String[] args) {

      Transaction transaction = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         transaction = session.beginTransaction();

         CriteriaBuilder builder = session.getCriteriaBuilder();
         CriteriaQuery<Department> query = builder.createQuery(Department.class);
         Root<Department> root = query.from(Department.class);
         query.select(root).where(builder.equal(root.get("id"), 1l));
         Query<Department> q=session.createQuery(query);
         Department department=q.getSingleResult();
         System.out.println(department.getName());
         
         transaction.commit();
      } catch (Exception e) {
         e.printStackTrace();
         if (transaction != null) {
            transaction.rollback();
         }
      }
   }
}
