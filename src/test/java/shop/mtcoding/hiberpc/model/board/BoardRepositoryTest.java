package shop.mtcoding.hiberpc.model.board;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import shop.mtcoding.hiberpc.config.dummy.MyDummyEntity;
import shop.mtcoding.hiberpc.model.user.User;
import shop.mtcoding.hiberpc.model.user.UserRepository;

@Import({ BoardRepository.class, UserRepository.class })
@DataJpaTest // 이걸 띄우면 repository가 뜨지 않아서 Import도 같이 띄워줘야 한다
public class BoardRepositoryTest extends MyDummyEntity {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE board_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
    }

    @Test
    public void save_test() {
        // given1
        User user = newUser("ssar");
        User userPS = userRepository.save(user);

        // given2
        Board board = newBoard("제목1", userPS);

        // when
        Board boardPS = boardRepository.save(board);
        // System.out.println("테스트 : "+new ObjectMapper().writeValueAsString(boardPS));
        System.out.println("테스트 : " + boardPS);

        // then
        assertThat(boardPS.getId()).isEqualTo(1);
        assertThat(boardPS.getUser().getId()).isEqualTo(1);
    }

    @Test
    public void update_test() {
        // given1 - DB에 영속화
        User user = newUser("ssar");
        User userPS = userRepository.save(user);
        Board board = newBoard("제목1", userPS);
        Board boardPS = boardRepository.save(board);

        // given2 - request 데이터
        String title = "제목12";
        String content = "내용12";

        // when
        boardPS.update(title, content);
        em.flush(); // 트랜젝션 종료시 자동 발동

        // then
        Board findBoardPS = boardRepository.findById(1);
        Assertions.assertThat(findBoardPS.getTitle()).isEqualTo("제목12");
        Assertions.assertThat(findBoardPS.getContent()).isEqualTo("내용12");
    }

    @Test
    public void delete_test() { // flush test
        // given1 - DB에 영속화
        User user = newUser("ssar");
        User userPS = userRepository.save(user);
        Board board = newBoard("제목1", userPS);
        Board boardPS = boardRepository.save(board);

        // given2 - request 데이터
        // em.clear(); 비워지고 보드만 셀렉트 된다 => 테스트 빼곤 사용할 일이 없다
        // @ManyToOne(fetch = FetchType.LAZY) 전략을 바꾸고
        int id = 1;
        Board findBoardPS = boardRepository.findById(id); // 캐싱

        // when
        boardRepository.delete(findBoardPS);

        // then
        Board deleteBoardPS = boardRepository.findById(1);
        Assertions.assertThat(deleteBoardPS).isNull();
    }

    @Test
    public void findById_test() {
        // given1 - DB에 영속화
        User user = newUser("ssar");
        User userPS = userRepository.save(user);
        Board board = newBoard("제목1", userPS);
        boardRepository.save(board);

        // given2
        int id = 1;

        // when
        Board findBoardPS = boardRepository.findById(id);

        // then
        Assertions.assertThat(findBoardPS.getUser().getUsername()).isEqualTo("ssar");
        Assertions.assertThat(findBoardPS.getTitle()).isEqualTo("제목1");

    }

    @Test
    public void findAll_test() {
        // given
        User user = newUser("ssar");
        User userPS = userRepository.save(user);
        List<Board> boardList = Arrays.asList(newBoard("ssar", userPS), newBoard("cos", userPS));
        boardList.stream().forEach((board) -> {
            boardRepository.save(board);
        });

        // when
        List<Board> boardListPS = boardRepository.findAll();

        // then
        Assertions.assertThat(boardListPS.size()).isEqualTo(2);

    }
}
