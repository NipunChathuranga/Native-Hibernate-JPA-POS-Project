package lk.ijse.dep.pos.db;


import javax.persistence.EntityManagerFactory;

public class JPAUtilTest {

    public static void main(String[] args) {
        EntityManagerFactory emf  = JPAUtil.getEntityManagerFactory();
        System.out.println(emf);
    }
}
