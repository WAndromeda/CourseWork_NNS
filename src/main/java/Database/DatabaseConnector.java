package Database;

import Entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class DatabaseConnector {

    static final String databaseName = "MySQL";
    static final String configPath = "./src/resources/hibernate.cfg.xml";
    private static SessionFactory sessionFactory;

    private  DatabaseConnector(){}

    public static SessionFactory createHibernateSessionFactory(){
        if (sessionFactory == null) {
            try {
                Configuration cfg = new Configuration().configure();  //Считывание файла конфигурации Hibernate
                System.out.println(cfg.getProperties());
                cfg.addAnnotatedClass(Client.class);
                cfg.addAnnotatedClass(Product.class);
                cfg.addAnnotatedClass(OrderShop.class);
                cfg.addAnnotatedClass(Basket.class);
                cfg.addAnnotatedClass(Favorite.class);
                cfg.addAnnotatedClass(Category.class);
                cfg.addAnnotatedClass(ProductsInOrder.class);
                cfg.addAnnotatedClass(PaymentMethod.class);
                cfg.addAnnotatedClass(ConfirmationKey.class);
                cfg.addAnnotatedClass(BlockSendingMail.class);
                System.out.println("CHECK 1 - All classes successfully added as 'Annotated class'");
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
                System.out.println("CHECK 2 - Properties successfully retrieved");
                sessionFactory = cfg.buildSessionFactory(builder.build());
                System.out.println("CHECK 3 - SessionFactory created");
            } catch (Exception e) {
                System.out.println("\n" + e + "\n");
            }
        }
        return sessionFactory;
    }

    public static Session createHibernateSession(){
        return createHibernateSessionFactory().openSession();
    }


}
