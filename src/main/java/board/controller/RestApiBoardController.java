package board.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import board.dto.BoardDto;
import board.service.BoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description = "게시판 REST API")
@RestController
public class RestApiBoardController {
	
	@Autowired
	private BoardService boardService;

	@ApiOperation(value = "게시판 목록 조회")
	@RequestMapping(value = "/api/board", method = RequestMethod.GET)
	public List<BoardDto> openBoardList() throws Exception {
		return boardService.selectBoardList();
	}

	@ApiOperation(value = "게시글 작성")
	@RequestMapping(value = "/api/board/write", method = RequestMethod.POST)
	public void insertBoard(@RequestBody BoardDto board) throws Exception {
		boardService.insertBoard(board, null);
	}

	@ApiOperation(value = "게시글 상세 조회")
	@RequestMapping(value = "/api/board/{boardIdx}", method = RequestMethod.GET)
	public BoardDto openBoardDetail(@PathVariable("boardIdx") @ApiParam(value = "게시글 번호") int boardIdx) throws Exception {
		return boardService.selectBoardDetail(boardIdx);
	}

	@RequestMapping(value = "/api/board/{boardIdx}", method = RequestMethod.PUT)
	public BoardDto updateBoard(@RequestBody BoardDto board) throws Exception {
		boardService.updateBoard(board);
		int boardIdx = board.getBoardIdx();
		return boardService.selectBoardDetail(boardIdx);
	}

	@RequestMapping(value = "/api/board/{boardIdx}", method = RequestMethod.DELETE)
	public HashMap deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception {
		HashMap hm = new HashMap();
		try {
			boardService.deleteBoard(boardIdx);
			hm.put("result", "success");
		} catch (Exception e) {
			hm.put("reusult", "fail");
			throw e;
		}
		return hm;
	}
}
