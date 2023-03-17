package shop.mtcoding.hiberpc.model.user;

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

    @Test
    public void save_test() {
        // given
        User user = newUser("ssar");

        // when
        User userPS = userRepository.save(user);
        // then
        Assertions.assertThat(userPS.getId()).isEqualTo(1);
    }
}
