package board.common;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.dto.BoardFileDto;
import board.entity.BoardFileEntity;

@Component
public class FileUtils {
	
	// 요청에 포함된 파일을 추출해서 저장하고 해당 파일의 정보를 반환
	// insertBoard 서비스에서 호출
	public List<BoardFileDto> parseFileInfo(int boardIdx, MultipartHttpServletRequest request) throws Exception {
		
		if (ObjectUtils.isEmpty(request)) {
			return null;
		}
		
		// 파일 정보
		List<BoardFileDto> fileInfoList = new ArrayList();
		
		// 파일 저장 경로 생성
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd"); 
		ZonedDateTime now = ZonedDateTime.now();
		String storedDir = "images/" + now.format(dtf);	// images/20200421
		File fileDir = new File(storedDir);
		if (!fileDir.exists()) {
			fileDir.mkdir();
		}
		
		// 요청에 포함된 파일을 추출해서 저장 경로에 저장하고 파일 정보를 fileInfoList에 추가
		Iterator<String> fileTagNames = request.getFileNames(); // <input type="file" name="????" .../>
		while (fileTagNames.hasNext()) {
			String originalFileExtension;
			
			// 같은 <input type="file" name="???"> 태그 이름의 파일을 추출
			List<MultipartFile> files = request.getFiles(fileTagNames.next());
			for (MultipartFile file : files) {
				if (!file.isEmpty() ) {
					String contentType = file.getContentType();
					if (ObjectUtils.isEmpty(contentType)) {
						break;
					} else {
						// ContentType에 맞춰서 파일의 확장자를 제한
						if (contentType.contains("image/jpeg")) {
							originalFileExtension = ".jpg";
						} else if (contentType.contains("image/png")) {
							originalFileExtension = ".png";
						} else if (contentType.contains("image/gif")) {
							originalFileExtension = ".gif";
						} else {
							break;
						}
					}
					
					// 저장 파일명을 생성 : 저장 파일명 = 나노시간 + 파일확장자
					String storedFileName = Long.toString(System.nanoTime()) + originalFileExtension;
					
					// 파일 정보를 fileInfoList에 추가
					BoardFileDto boardFile = new BoardFileDto();
					boardFile.setBoardIdx(boardIdx);
					boardFile.setFileSize(file.getSize());
					boardFile.setOriginalFileName(file.getOriginalFilename());
					boardFile.setStoredFilePath(storedDir + "/" + storedFileName);
					fileInfoList.add(boardFile);					
					
					// 파일을 저장
					fileDir = new File(storedDir + "/" + storedFileName);
					file.transferTo(fileDir);
				}
			}
		}		
		return fileInfoList;
	}
	
	//	파일 정보를 BoardFileEntity 객체에 담아서 반환하는 메소드
	public List<BoardFileEntity> parseFileInfo2(int boardIdx, MultipartHttpServletRequest request) throws Exception {
		
		if (ObjectUtils.isEmpty(request)) {
			return null;
		}
		
		// 파일 정보
		List<BoardFileEntity> fileInfoList = new ArrayList();
		
		// 파일 저장 경로 생성
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd"); 
		ZonedDateTime now = ZonedDateTime.now();
		String storedDir = "images/" + now.format(dtf);	// images/20200421
		File fileDir = new File(storedDir);
		if (!fileDir.exists()) {
			fileDir.mkdir();
		}
		
		// 요청에 포함된 파일을 추출해서 저장 경로에 저장하고 파일 정보를 fileInfoList에 추가
		Iterator<String> fileTagNames = request.getFileNames(); // <input type="file" name="????" .../>
		while (fileTagNames.hasNext()) {
			String originalFileExtension;
			
			// 같은 <input type="file" name="???"> 태그 이름의 파일을 추출
			List<MultipartFile> files = request.getFiles(fileTagNames.next());
			for (MultipartFile file : files) {
				if (!file.isEmpty() ) {
					String contentType = file.getContentType();
					if (ObjectUtils.isEmpty(contentType)) {
						break;
					} else {
						// ContentType에 맞춰서 파일의 확장자를 제한
						if (contentType.contains("image/jpeg")) {
							originalFileExtension = ".jpg";
						} else if (contentType.contains("image/png")) {
							originalFileExtension = ".png";
						} else if (contentType.contains("image/gif")) {
							originalFileExtension = ".gif";
						} else {
							break;
						}
					}
					
					// 저장 파일명을 생성 : 저장 파일명 = 나노시간 + 파일확장자
					String storedFileName = Long.toString(System.nanoTime()) + originalFileExtension;
					
					// 파일 정보를 fileInfoList에 추가
					BoardFileEntity boardFile = new BoardFileEntity();
					// boardFile.setBoardIdx(boardIdx);
					boardFile.setFileSize(file.getSize());
					boardFile.setOriginalFileName(file.getOriginalFilename());
					boardFile.setStoredFilePath(storedDir + "/" + storedFileName);
					boardFile.setCreatorId("admin");
					fileInfoList.add(boardFile);					
					
					// 파일을 저장
					fileDir = new File(storedDir + "/" + storedFileName);
					file.transferTo(fileDir);
				}
			}
		}		
		return fileInfoList;
	}
}
