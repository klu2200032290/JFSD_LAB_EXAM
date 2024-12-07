package com.klef.jfsd.exam;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

public class ClientDemo {
    public static void main(String[] args) {
        // Load configuration and build session factory
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            // Insert Operation
            insertDepartment(session, "Computer Science", "New York", "Dr. John Doe");
            insertDepartment(session, "Mechanical Engineering", "Los Angeles", "Dr. Jane Smith");

            // Delete Operation
            deleteDepartmentById(session, 1); // Deletes department with ID 1
        } finally {
            session.close();
            sessionFactory.close();
        }
    }

    public static void insertDepartment(Session session, String name, String location, String hodName) {
        Transaction transaction = session.beginTransaction();
        Department department = new Department(name, location, hodName);
        session.save(department);
        transaction.commit();
        System.out.println("Department inserted successfully: " + department.getDepartmentId());
    }

    public static void deleteDepartmentById(Session session, int departmentId) {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("DELETE FROM Department WHERE departmentId = :deptId");
        query.setParameter("deptId", departmentId);
        int result = query.executeUpdate();
        transaction.commit();
        System.out.println(result > 0 ? "Department deleted successfully." : "No department found with ID " + departmentId);
    }
}
