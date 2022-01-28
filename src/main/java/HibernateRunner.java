import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import converter.BirthdayConverter;
import entity.Birthday;
import entity.Role;
import entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.concurrent.BlockingQueue;

public class HibernateRunner {
    public static void main(String[] args) throws SQLException {
//        BlockingDeque<Connection> pool = null;
//        Connection connection = pool.take();
//        //SessionFactory

//        Connection connection = DriverManager
//                .getConnection("db.url", "db.username", "db.password");
//        //Session

        Configuration configuration = new Configuration();
        configuration.addAttributeConverter(new BirthdayConverter());
        configuration.registerTypeOverride(new JsonBinaryType());
        configuration.configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            User user = User.builder()
                    .username("bob1@test.com")
                    .firstname("Bob")
                    .lastname("Bobov")
                    .info("""
                            {
                            "name" : "Fedor",
                            "id" : 34
                            }
                            """)
                    .birthDay(new Birthday(LocalDate.of(1994, 6, 14)))
                    .role(Role.ADMIN)
                    .build();

//            session.save(user);
//            session.update(user); //exception если юзера нет в базе
//            session.saveOrUpdate(user); // создает или обновляет юзера
//            session.delete(user); // если есть сущность, то удаляем, если нет, то ничего не происходит

            User user1 = session.get(User.class, "bob1@test.com");
            user1.setLastname("Petrov");
            System.out.println(session.isDirty());

//            session.evict(user1); //удаляем один объект в кэше
//            session.clear(); // полностью очищаем кэш
//            session.close(); // закрываем сессию

            session.getTransaction().commit();

            System.out.println("It's OK");
        }
    }
}
