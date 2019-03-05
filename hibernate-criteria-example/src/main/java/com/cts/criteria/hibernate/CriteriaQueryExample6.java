package com.cts.criteria.hibernate;

import java.util.List;

import javax.persistence.criteria.*;

import org.hibernate.*;
import org.hibernate.query.Query;

import com.cts.criteria.hibernate.entity.Department;
import com.cts.criteria.hibernate.entity.Employee;

public class CriteriaQueryExample6 {

   public static void main(String[] args) {

      Transaction transaction = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         transaction = session.beginTransaction();

         CriteriaBuilder builder = session.getCriteriaBuilder();

         // Using FROM and JOIN
         CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
         Root<Employee> empRoot = criteriaQuery.from(Employee.class);
         Root<Department> deptRoot = criteriaQuery.from(Department.class);
         criteriaQuery.multiselect(empRoot, deptRoot);
         criteriaQuery.where(builder.equal(empRoot.get("department"), deptRoot.get("id")));

         Query<Object[]> query=session.createQuery(criteriaQuery);
         List<Object[]> list=query.getResultList();
         for (Object[] objects : list) {
            Employee employee=(Employee)objects[0];
            Department department=(Department)objects[1];
            System.out.println("EMP NAME="+employee.getName()+"\t DEPT NAME="+department.getName());
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
