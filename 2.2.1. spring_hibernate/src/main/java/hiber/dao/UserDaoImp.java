package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public List<User> getUsersByModelAndSeries(Car car) {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("SELECT u FROM User u JOIN FETCH Car c ON u.id = c.cardId WHERE c.model=: carModel AND c.series=: cSeria", User.class);
        query.setParameter("carModel", car.getModel());
        query.setParameter("cSeria", car.getSeries());
        List<User> list = query.getResultList();
        return list;
    }

}
