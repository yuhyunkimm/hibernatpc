package shop.mtcoding.hiberpc.model.user;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UserRepository {

    private final EntityManager em;

    public User findById(int id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() { // User Class아님 객체
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    public User save(User user) { // 서비스에서 검증을 하던

        if (user.getId() == null) {
            em.persist(user);
        } else { // dirtychecking을 할 것이기 때문에 쓸일이 없다
            User userPS = em.find(User.class, user.getId());
            if (userPS == null) {
                em.merge(user);
            } else {
                System.out.println("잘못된 머지를 하셨습니다");
            }
        }
        return user;
    }

    public void delete(User user) {
        em.remove(em);
    }

}
