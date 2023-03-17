package shop.mtcoding.hiberpc.model.board;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.hiberpc.model.user.User;

@NoArgsConstructor // 없으면 하이버네이트가 뉴를 못함
@Getter
@Setter
@Entity
@Table(name = "board_tb")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 포린키는 n에 붙혀준다
    // private Integer userId; // ORM
    @ManyToOne // 앞에는 클래스 / Many = Board , One = User
    private User user; // 모순 되는것을 하이버네이트가 해결해 준다
    private String title;
    private String content;
    @CreationTimestamp
    private Timestamp createdAt;

}
