package shop.mtcoding.hiberpc.model.user;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import shop.mtcoding.hiberpc.config.dummy.MyDummyEntity;

@Import(UserRepository.class)
@DataJpaTest // 이걸 띄우면 repository가 뜨지 않아서 Import도 같이 띄워줘야 한다
public class UserRepositoryTest extends MyDummyEntity {
    @Autowired // test는 무조건 autowird로
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void save_test() {
        // given
        User user = newUser("ssar");

        // when
        User userPS = userRepository.save(user);

        // then
        Assertions.assertThat(userPS.getId()).isEqualTo(1);
    }

    @Test
    public void update_test() {
        // given1 - DB에 영속화
        User user = newUser("ssar");
        User userPS = userRepository.save(user);

        // given2 - request 데이터
        String password = "5678";
        String email = "ssar@gamil.com";

        // when
        userPS.update(password, email);
        User updateUserPS = userRepository.save(userPS);

        // then
        Assertions.assertThat(updateUserPS.getPassword()).isEqualTo("5678");
        Assertions.assertThat(updateUserPS.getEmail()).isEqualTo("ssar@gamil.com");
    }

    @Test
    public void update_dutty_checking_test() { // flush test
        // given1 - DB에 영속화
        User user = newUser("ssar");
        User userPS = userRepository.save(user);

        // given2 - request 데이터
        String password = "5678";
        String email = "ssar@gamil.com";

        // when
        userPS.update(password, email);
        em.flush();

        // then
        User updateUserPS = userRepository.findById(1);
        Assertions.assertThat(updateUserPS.getPassword()).isEqualTo("5678");
    }
}
