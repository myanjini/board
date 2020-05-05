package board.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.common.FileUtils;
import board.entity.BoardEntity;
import board.entity.BoardFileEntity;
import board.repository.JpaBoardRepository;

@Service
public class JpaBoardServiceImpl implements JpaBoardService {
	
	@Autowired
	JpaBoardRepository jpaBoardRepository;

	@Autowired 
	FileUtils fileUtils;
	
	@Override
	public List<BoardEntity> selectFavorite() throws Exception {
		return jpaBoardRepository.findAllByHitCntGreaterThanEqual(5);
	}	
	
	@Override
	public List<BoardEntity> selectBoardList() throws Exception {
		return jpaBoardRepository.findAllByOrderByBoardIdxDesc();
	}

	// 	save = insert, update
	@Override
	public void saveBoard(BoardEntity board, MultipartHttpServletRequest request) throws Exception {
		// 필수 항목(not null)인 작성자 ID를 설정
		board.setCreatorId("admin");
		
		// 첨부 파일 처리
		List<BoardFileEntity> files = fileUtils.parseFileInfo2(board.getBoardIdx(), request);
		if (!CollectionUtils.isEmpty(files)) {
			board.setFileInfoList(files);
		}
		
		jpaBoardRepository.save(board);		
	}

	@Override
	public BoardEntity selectBoardDetail(int boardIdx) throws Exception {
		Optional<BoardEntity> optional = jpaBoardRepository.findById(boardIdx);
		if (optional.isPresent()) {
			BoardEntity board = optional.get();
			
			//	게시판 상세 정보에서 조회 회수를 1 증가해서 업데이트
			board.setHitCnt(board.getHitCnt() + 1);
			jpaBoardRepository.save(board);
			
			return board;			
		} else {
			throw new NullPointerException();
		}
	}

	@Override
	public void deleteBoard(int boardIdx) throws Exception {
		jpaBoardRepository.deleteById(boardIdx);
	}

	@Override
	public BoardFileEntity selectBoardFileInfo(int idx, int boardIdx) throws Exception {
		BoardFileEntity boardFile = jpaBoardRepository.findBoardFile(boardIdx, idx);
		return boardFile;
	}


}
