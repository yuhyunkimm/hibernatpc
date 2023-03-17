package shop.mtcoding.hiberpc.config.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyBlackListFilter implements Filter {

    @Override // spring 컨테이너에서 돌린다
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // 전역적으로 걸러 버리면 안된다(쓰지말자 interceptor에서 하면된다)
        // BufferedReader br = request.getReader();
        // while (true) {
        // String v = br.readLine();
        // if (v == null) break;
        // }
        String value = request.getParameter("value");

        if (value == null) {
            response.setContentType("text/plain; charset=utf-8");
            response.getWriter().println("value 파라메터를 전송해주세요.");
            return;
        }

        // 주의 : 버퍼를 비우면, 컨트롤러에서 버퍼를 읽지 못한다
        // 데이터 타입 : x-www-form-urlencoded
        if (value.equals("babo")) {
            response.setContentType("text/plain; charset=utf-8");
            response.getWriter().println("당신은 블랙리스트가 되었습니다.");
            return;
        }
        chain.doFilter(request, response);

    }

}
