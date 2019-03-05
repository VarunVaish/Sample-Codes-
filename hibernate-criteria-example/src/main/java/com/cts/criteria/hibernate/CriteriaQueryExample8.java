package com.cts.criteria.hibernate;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.cts.criteria.hibernate.entity.Employee;

public class CriteriaQueryExample8 {

   public static void main(String[] args) {

      Transaction transaction = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         transaction = session.beginTransaction();

         CriteriaBuilder builder = session.getCriteriaBuilder();

         CriteriaQuery<Employee> criteriaQuery = builder.createQuery(Employee.class);
         Root<Employee> root = criteriaQuery.from(Employee.class);
         criteriaQuery.select(root);
         criteriaQuery.orderBy(builder.asc(root.get("salary")));
         Query<Employee> query = session.createQuery(criteriaQuery);
         List<Employee> list = query.getResultList();
         for (Employee employee : list) {
            System.out.println("EMP NAME="+employee.getName()+"\t SALARY="+employee.getSalary());
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
