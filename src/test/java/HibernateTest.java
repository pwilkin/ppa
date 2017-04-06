import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.ilintar.study.users.Answer;
import org.ilintar.study.users.Group;
import org.ilintar.study.users.Participant;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by pwilkin on 30-Mar-17.
 */
public class HibernateTest {

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }


    @Test
    public void createParticipant() {
        SessionFactory sf = buildSessionFactory();
        EntityManager em = sf.createEntityManager();
        em.getTransaction().begin();
        Participant part = new Participant();
        part.setGender("M");
        part.setEducation("podstawowe");
        part.setAge(24);
        //part.setLiving("miejscowość do 100 tys. mieszkańców");
        em.persist(part);
        em.getTransaction().commit();
        em.close();
        sf.close();
    }

    @Test
    public void retrieveParticipant() {
        SessionFactory sf = buildSessionFactory();
        EntityManager em = sf.createEntityManager();
        em.getTransaction().begin();
        List<Participant> parts = em.createQuery("from Participant").getResultList();
        for (Participant part : parts) {
            System.out.println(part);
            Answer answer = new Answer();
            answer.setAnswer("bla");
            answer.setQuestionCode("bla?");
            answer.setParticipant(part);
            part.getAnswers().add(answer);
            Group group = new Group();
            group.setName("BLA!");
            part.getGroups().add(group);
            em.persist(part);
        }
        em.getTransaction().commit();
        em.close();
        sf.close();
    }

    @Test
    public void deleteParticipant() {
        SessionFactory sf = buildSessionFactory();
        EntityManager em = sf.createEntityManager();
        em.getTransaction().begin();
        List<Participant> parts = em.createNamedQuery("participantByLiving").
            setParameter("living", "nieznane").getResultList();
        for (Participant part : parts) {
            em.remove(part);
        }
        em.getTransaction().commit();
        em.close();
        sf.close();
    }
}
