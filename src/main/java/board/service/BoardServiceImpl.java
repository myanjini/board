package board.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.common.FileUtils;
import board.dto.BoardDto;
import board.dto.BoardFileDto;
import board.mapper.BoardMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
// @Transactional
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private FileUtils fileUtils;

	
	@Override
	public List<BoardDto> selectBoardList() throws Exception {
		return boardMapper.selectBoardList();
	}

	@Override
	public void insertBoard(BoardDto board, MultipartHttpServletRequest request) throws Exception {
		boardMapper.insertBoard(board);
		List<BoardFileDto> files = fileUtils.parseFileInfo(board.getBoardIdx(), request);
		if (!CollectionUtils.isEmpty(files)) {
			boardMapper.insertBoardFileList(files);
		}
	}

	@Override
	public BoardDto selectBoardDetail(int boardIdx) throws Exception {
		// AS-IS : 조회 회수를 증가하고 게시판 상세 정보를 조회해서 반환
//		boardMapper.updateHitCnt(boardIdx);
//		return boardMapper.selectBoardDetail(boardIdx);
		
		// TO-BE : 조회 회수를 증가하고 게시판 상세 정보와 첨부 파일 정보를 조회해서 해당 정보를 결합해서 반환
		//         게시판 정보를 담고 있는 BoardDto에 첨부 파일 정보를 담을 수 있도록 필드를 추가
		boardMapper.updateHitCnt(boardIdx);
		
		BoardDto board = boardMapper.selectBoardDetail(boardIdx);
		List<BoardFileDto> fileInfoList = boardMapper.selectBoardFileList(boardIdx);
		board.setFileInfoList(fileInfoList);
		
		return board;
	}

	@Override
	public void updateBoard(BoardDto board) throws Exception {
		boardMapper.updateBoard(board);
	}

	@Override
	public void deleteBoard(int boardIdx) throws Exception {
		boardMapper.deleteBoard(boardIdx);
	}

	@Override
	public BoardFileDto selectBoardFileInfo(int idx, int boardIdx) throws Exception {
		return boardMapper.selectBoardFileInfo(idx, boardIdx);
	}
}
