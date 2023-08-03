package com.pjt.petmily.domain.sns.board;

import com.pjt.petmily.domain.sns.board.dto.BoardRequestDto;
import com.pjt.petmily.domain.sns.board.photo.Photo;
import com.pjt.petmily.domain.sns.board.photo.PhotoRepository;
import com.pjt.petmily.domain.user.User;
import com.pjt.petmily.domain.user.repository.UserRepository;
import com.pjt.petmily.global.awss3.service.S3Uploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private PhotoRepository photoRepository;

    private final com.pjt.petmily.global.awss3.service.S3Uploader s3Uploader;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository, PhotoRepository photoRepository, S3Uploader s3Uploader) {
        this.boardRepository = boardRepository;
        this.photoRepository = photoRepository;
        this.s3Uploader = s3Uploader;
    }

    @Override
    public void boardSave(BoardRequestDto boardRequestDto, List<MultipartFile> boardImgFiles) throws Exception {
        User user = userRepository.findByUserEmail(boardRequestDto.getUserEmail())
                .orElseThrow(() -> new UsernameNotFoundException("사용자가 존재하지 않습니다"));

        LocalDateTime currentTime = LocalDateTime.now();

        Board board = Board.builder()
                .user(user)
                .boardContent(boardRequestDto.getBoardContent())
                .boardUploadTime(currentTime)
                .build();

        Board savedBoard = boardRepository.save(board);

        for (MultipartFile imageFile : boardImgFiles) {
            String BoardImg = imageFile  == null? null : s3Uploader.uploadFile(imageFile, "sns");
            Photo photo = new Photo();
            photo.setPhotoUrl(BoardImg);
            photo.setBoard(savedBoard);
            photoRepository.save(photo);
        }
    }

    @Override
    public void boardUpdate(Long boardId, BoardRequestDto boardRequestDto, List<MultipartFile> boardImgFiles) throws Exception{
        Board board = boardRepository.findByBoardId(boardId)
                .orElseThrow(() -> new Exception("게시글" +boardId + "정보가 없습니다."));


    }

}
