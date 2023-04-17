package board.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import board.dao.BoardDAO;
import board.vo.Board;

public class BoardService {
	private BoardDAO dao = new BoardDAO();
	
	public int insert(Board board) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.insert(conn, board);
		
		if(result > 0) commit(conn);
		else		rollback(conn);
		
		close(conn);
		
		return result;
	}
	
	public List<Board> selectAll() throws Exception {
		Connection conn = getConnection();
		
		List<Board> list = dao.selectAll(conn);
		
		close(conn);
		
		return list;
		
		
	}
	
	public Board selectOne(String inputSearch) throws Exception {
		Connection conn = getConnection();
		
		Board board = dao.selectOne(conn, inputSearch);
		
		close(conn);
		
		return board;
		
	}
 
	
	
}
