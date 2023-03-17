package shop.mtcoding.hiberpc.config.dummy;

import shop.mtcoding.hiberpc.model.board.Board;
import shop.mtcoding.hiberpc.model.user.User;

public class MyDummyEntity {

    protected User newUser(String username) {
        return User.builder()
                .username(username)
                .password("1234")
                .email(username + "@nate.com")
                .build();
    }
    // 가상 user aksemfdjtj
    // protected User newMockUser(int id,String username) {
    // return User.builder()
    // .id(id)
    // .build();
    // }

    protected Board newBoard(String title, User userPS) {
        if (userPS.getId() == null) {
            System.out.println("영속화해서 넣어");
            return null;
        }

        return Board.builder()
                .title(title)
                .content(title)
                .user(userPS)
                .build();
    }
}
