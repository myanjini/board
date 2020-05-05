package board.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "BoardDto: 게시글 내용", description = "게시글 내용")
@Data
public class BoardDto {
	@ApiModelProperty(value = "게시글 번호")
	private int boardIdx;
	@ApiModelProperty(value = "게시글 제목")
	private String title;
	@ApiModelProperty(value = "게시글 내용")
	private String contents;
	private int hitCnt; 		// hit_cnt		
	private String creatorId;	// creator_id	
	private String createdDatetime;
	private String updaterId;	
	private String updatedDatetime;
	
	// 첨부 파일 정보를 담고 있는 목록
	private List<BoardFileDto> fileInfoList;
}
