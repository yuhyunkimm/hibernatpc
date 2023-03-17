package shop.mtcoding.hiberpc.model.board;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class BoardRepository {

    private final EntityManager em;

    public Board findById(int id) {
        return em.find(Board.class, id);
    }

    public List<Board> findAll() { // User Class아님 객체
        return em.createQuery("select b from Board b", Board.class).getResultList();
    }

    public Board save(Board board) { // 서비스에서 검증을 하던

        if (board.getId() == null) {
            em.persist(board);
        } else { // dirtychecking을 할 것이기 때문에 쓸일이 없다
            Board userPS = em.find(Board.class, board.getId());
            if (userPS == null) {
                em.merge(board);
            } else {
                System.out.println("잘못된 머지를 하셨습니다");
            }
        }
        return board;
    }

    public void delete(Board board) {
        em.remove(em);
    }

}
