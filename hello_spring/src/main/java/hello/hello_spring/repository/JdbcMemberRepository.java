package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMemberRepository implements MemberRepository{

    //db에 접근하기 위해 데이터 소스가 필요함
    private final DataSource dataSource;

    //spring에게 주입받기 위해서 생성자를 만든다
    //application.properties에서 설정한 것들(spring.datasource)은 springBoot가 dataSource를 만든다.
    public JdbcMemberRepository(DataSource dataSource) { //spring을 통해 주입받는다
        this.dataSource = dataSource;
        //dataSource.getConnection(); //여기에 sql문을 넣어 db에 전달할 수 있음(db와 연결되는 문장) //이러면 계속 새로운 connection이 주어진다
    }

    @Override
    public Member save(Member member) {
        /*
        String sql = "insert into member(name) values(?)"; //(?): 파라미터로 받는 값
        Connection conn = dataSource.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, member.getName()); //1번째에는 이름을 넣어준다
        pstmt.executeUpdate();
        return null;*/

        String sql = "insert into member(name) values(?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); //RETURN_GENERATED_KEYS: 1, 2 등 값을 매기면 넣어봐야지 앎.

            pstmt.setString(1, member.getName()); //첫번재 (?)와 매칭됨

            pstmt.executeUpdate(); //db에 실제 쿼리가 날라감
            rs = pstmt.getGeneratedKeys(); //RETURN_GENERATED_KEYS와 매칭됨. rs에는 시퀀스로 들어간 id값을 꺼내온다.

            if (rs.next()) {
                member.setId(rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }
            return member;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from member where id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            rs = pstmt.executeQuery(); //조회는 executeUpdate() 대신 executeQuery() 를 사용한다.

            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            } else {
                return Optional.empty();
            }

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from member";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            List<Member> members = new ArrayList<>();
            while(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);
            }
            return members;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * from member where name = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    private Connection getConnection() {
        //spring framework를 쓸 때는 꼭 이렇게 받아와야함
        return DataSourceUtils.getConnection(dataSource); //DataSourceUtils를 통해 connection을 얻어야 동일한 database connection을 유지시켜준다
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) //파라미터로 들어간 값 역순으로 닫아줌
    {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void close(Connection conn) throws SQLException {
        //닫을 때도 DataSourceUtils를 통해 release를 해줘야함
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}