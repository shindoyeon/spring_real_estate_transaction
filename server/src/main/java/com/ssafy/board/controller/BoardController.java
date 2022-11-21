package com.ssafy.board.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.service.BoardService;
import com.ssafy.util.*;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/board")
@CrossOrigin("*")
public class BoardController {

	private final Logger logger = LoggerFactory.getLogger(BoardController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private final BoardService boardService;
	
	@Autowired
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}

	@ApiOperation(value = "limit offset 에 해당하는 게시글의 정보를 반환한다.  ", response = List.class)
	@GetMapping(value = "/list")
	public ResponseEntity<List<BoardDto>> selectBoardLimitOffset(@RequestParam Map<String, String> map) throws Exception {
		logger.debug("selectBoardLimitOffset - 호출 {}",map);

		int limit = Integer.parseInt(map.get("limit"));
		int offset = Integer.parseInt(map.get("offset"));
		String key = map.get("key");
		String word = map.get("word");
		return new ResponseEntity<List<BoardDto>>(boardService.selectBoardLimitOffset(limit,offset,key,word), HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시글의 전체 건수를 반환한다.  ", response = List.class)
	@GetMapping(value = "/pagelink/count")
	public ResponseEntity<Integer> selectBoardTotalCount() throws Exception {
		logger.debug("selectBoardTotalCount - 호출");
		return new ResponseEntity<Integer>(boardService.selectBoardTotalCount(), HttpStatus.OK);
	}
	@GetMapping("/mvwrite")
	private String mvWrite() {
		return "board/write";
	}
	
	@PostMapping("/write")
	public ResponseEntity<?> write(@RequestBody BoardDto boardDto) throws Exception {
		logger.debug("이인재 BoardController board/update {}", boardDto);
		try {
			
			boardService.writeArticle(boardDto);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@GetMapping("/detail/{articleNo}")
	public ResponseEntity<?> view(@PathVariable int articleNo) throws Exception {
		logger.debug("detail {}", articleNo);
		boardService.updateHit(articleNo);
		BoardDto boardDto = boardService.getArticle(articleNo);

		ResponseEntity<BoardDto> result = ResponseEntity.ok().body(boardDto);

		return result;
	}

	@GetMapping("/mvmodify")
	private String mvModify(@RequestParam Map<String, String> map, HttpSession session, Model model) {
		try {
			BoardDto boardDto = boardService.getArticle(Integer.parseInt(map.get("articleno")));
			model.addAttribute("article", boardDto);
			System.out.println(map);
			return "/board/modify";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "글내용 얻는 중 문제 발생!!!");
			return "/error/error";
		}
	}

	@PutMapping("/modify")
	private ResponseEntity<?> modify(@RequestBody BoardDto boardDto, HttpSession session, Model model,
			RedirectAttributes redirectAttributes) {
		logger.debug("BoardController board/update {}", boardDto);
		try {
			System.out.println("boardDto content: " + boardDto.getContent());
			boardService.modifyArticle(boardDto);
			BoardDto board = boardService.getArticle(boardDto.getArticleNo());
			if (board != null) {
				return new ResponseEntity<BoardDto>(board, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}

	}
	
	@DeleteMapping(value = "/delete/{articleNo}")
	private ResponseEntity<String> delete(@PathVariable int articleNo, HttpSession session, Model model,
			RedirectAttributes redirectAttributes) {
		logger.debug("BoardController delete {}.",articleNo);
		try {
			boardService.deleteArticle(articleNo);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
