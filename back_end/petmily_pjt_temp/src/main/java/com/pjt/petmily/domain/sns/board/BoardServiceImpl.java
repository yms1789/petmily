package com.pjt.petmily.domain.sns.board;

import com.pjt.petmily.domain.sns.board.dto.*;
import com.pjt.petmily.domain.sns.board.hashtag.HashTag;
import com.pjt.petmily.domain.sns.board.hashtag.HashTagRepository;
import com.pjt.petmily.domain.sns.board.hashtag.HashTagRequestDto;
import com.pjt.petmily.domain.sns.board.photo.Photo;
import com.pjt.petmily.domain.sns.board.photo.PhotoRepository;
import com.pjt.petmily.domain.user.User;
import com.pjt.petmily.domain.user.repository.UserRepository;
import com.pjt.petmily.global.awss3.service.S3Uploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private HashTagRepository hashTagRepository;

    private final com.pjt.petmily.global.awss3.service.S3Uploader s3Uploader;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository,
                            PhotoRepository photoRepository,
                            HashTagRepository hashTagRepository,
                            S3Uploader s3Uploader) {
        this.boardRepository = boardRepository;
        this.photoRepository = photoRepository;
        this.hashTagRepository = hashTagRepository;
        this.s3Uploader = s3Uploader;
    }

    @Override
    public Board boardSave(BoardRequestDto boardRequestDto, List<MultipartFile> boardImgFiles, HashTagRequestDto hashTagRequestDto) throws Exception {
        User user = userRepository.findByUserEmail(boardRequestDto.getUserEmail())
                .orElseThrow(() -> new UsernameNotFoundException("사용자가 존재하지 않습니다"));

        LocalDateTime currentTime = LocalDateTime.now();

        Board board = Board.builder()
                .user(user)
                .boardContent(boardRequestDto.getBoardContent())
                .boardUploadTime(currentTime)
                .build();

        Board savedBoard = boardRepository.save(board);
        if (boardImgFiles != null && !boardImgFiles.isEmpty()) {
            for (MultipartFile imageFile : boardImgFiles) {
                Optional<String> BoardImg = imageFile == null? Optional.empty() : s3Uploader.uploadFile(imageFile, "sns");
                if (BoardImg.isPresent()) {
                    Photo photo = new Photo();
                    photo.setPhotoUrl(BoardImg.get()); // Optional에서 값을 꺼낼 때는 get() 메서드를 사용합니다.
                    photo.setBoard(savedBoard);
                    photoRepository.save(photo);
                }
            }
        }
        // 해시태그 저장 코드
        if (hashTagRequestDto != null && hashTagRequestDto.getHashTagNames() != null) {
            for (String tag : hashTagRequestDto.getHashTagNames()) {
                HashTag hashTag = new HashTag();
                hashTag.setHashTagName(tag);
                hashTag.setBoard(savedBoard);
                hashTagRepository.save(hashTag);
            }
        }
        return board;
    }

    @Override
    public void boardUpdate(Long boardId, BoardRequestDto boardRequestDto, List<MultipartFile> boardImgFiles, HashTagRequestDto hashTagRequestDto) throws Exception {
        Board board = boardRepository.findByBoardId(boardId)
                .orElseThrow(() -> new Exception("게시글" +boardId + "정보가 없습니다."));

        // 게시글 작성자와 요청한 사용자가 일치하는지 확인
        if (!board.getUser().getUserEmail().equals(boardRequestDto.getUserEmail())) {
            throw new Exception("게시글 수정 권한이 없습니다.");
        }

        board.setBoardContent(boardRequestDto.getBoardContent());

        board.getPhotoList().clear();

        // 새로운 사진 업로드 및 저장
        if (boardImgFiles != null && !boardImgFiles.isEmpty()) {
            for (MultipartFile imageFile : boardImgFiles) {
                Optional<String> BoardImg = imageFile  == null? null : s3Uploader.uploadFile(imageFile, "sns");
                Photo photo = new Photo();
                if (BoardImg.isPresent()) { // BoardImg가 비어있는지 확인
                    photo.setPhotoUrl(BoardImg.get());
                    photo.setBoard(board);
                    board.getPhotoList().add(photo);
                    photoRepository.save(photo);
                }
            }
        }

        // 기존 해시태그 삭제
        List<HashTag> oldHashTags = hashTagRepository.findByBoard(board);
        hashTagRepository.deleteAll(oldHashTags);

        if (hashTagRequestDto != null && hashTagRequestDto.getHashTagNames() != null) {
            for (String tag : hashTagRequestDto.getHashTagNames()) {
                HashTag hashTag = new HashTag();
                hashTag.setHashTagName(tag);
                hashTag.setBoard(board);
                hashTagRepository.save(hashTag);
            }
        }

        boardRepository.save(board);
    }



    @Override
    public void boardDelete(Long boardId, BoardDeleteDto boardDeleteDto) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardException.BoardNotFoundException("게시글이 존재하지 않음" + boardId));

        if (!board.getUser().getUserEmail().equals(boardDeleteDto.getUserEmail())) {
            throw new BoardException.UnauthorizedException("게시글 삭제 권한이 없습니다.");
        }

        try {
            boardRepository.delete(board);
        } catch (Exception e) {
            throw new BoardException.BoardDeletionException("게시글 삭제 실패 " + boardId);
        }
    }


    @Override
    @Transactional
    public List<ResponseBoardAllDto> getAllBoard(String currentUserEmail){
        List<Board> boards = boardRepository.findAll();
        return boards.stream()
                .map(board -> ResponseBoardAllDto.fromBoardEntity(board, currentUserEmail))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PagedResponseBoardDto getAllBoardPagesBy(Long lastPostId, int size, String currentUserEmail){
        PageRequest pageRequest = PageRequest.of(0, size);
        Page<Board> entityPage = boardRepository.findByPostIdLessThanOrderByPostIdDesc(lastPostId, pageRequest);
        List<Board> entityList = entityPage.getContent();
        List<ResponseBoardAllDto> responseBoards = entityList.stream()
                .map(board -> ResponseBoardAllDto.fromBoardEntity(board, currentUserEmail))
                .collect(Collectors.toList());

        boolean isLast = entityList.size() < size;

        return new PagedResponseBoardDto(responseBoards, isLast);
    }


    @Override
    @Transactional
    public ResponseBoardAllDto getOneBoard(Long boardId, String currentUserEmail){
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardException.BoardNotFoundException("게시글" +boardId + "정보가 없습니다."));
        return ResponseBoardAllDto.fromBoardEntity(board, currentUserEmail);
    }

    public List<BoardHashtagDto> getBoardsByHashTag(String hashTagName) {
        List<Board> boards = boardRepository.findByHashTagList_HashTagName(hashTagName);
        return boards.stream().map(BoardHashtagDto::fromBoardEntity).collect(Collectors.toList());
    }
}
