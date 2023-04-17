package board.dao;

import static common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import board.vo.Board;
 

public class BoardDAO {
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
	Properties prop;
	
	public BoardDAO() {
		try {
			prop = new Properties();
			String filePath = BoardDAO.class.getResource("/board/sql/board-sql.xml").getPath();
			prop.loadFromXML(new FileInputStream(filePath));
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	public int insert(Connection conn, Board board) throws Exception {
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("insert");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setString(3, board.getWriter());
			


			result = pstmt.executeUpdate();
			
			
		} finally {
			close(pstmt);
		}
		return result;
	}
	 
	
	
	public List<Board> selectAll(Connection conn) throws Exception {
		
		List<Board> list = new ArrayList<Board>();
		
		try {
			String sql = prop.getProperty("selectAll");
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				Board board = new Board();
				
				board.setNum(rs.getInt(1));
				board.setTitle(rs.getString(2));
				board.setWriter(rs.getString(3));
				board.setRegdate(rs.getDate(4));
				board.setCnt(rs.getInt(5));
				board.setContent(rs.getString(6));
				
				list.add(board);
				
			}
			
			
		} finally { 
			close(rs);
			close(stmt);
		}
		return list;
	}
	
	public Board selectOne(Connection conn, String inputSearch) throws Exception {
		Board board = null;
		
		try {
			String sql = prop.getProperty("selectOne");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, inputSearch);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				board = new Board();
				
				board.setNum(rs.getInt(1));
				board.setTitle(rs.getString(2));
				board.setWriter(rs.getString(3));
				board.setRegdate(rs.getDate(4));
				board.setCnt(rs.getInt(5));
				board.setContent(rs.getString(6)); 
			}
			
		} finally {
			close(rs);
			close(pstmt);
			
		}
		return board;
	}
	
	
 
	 

}
