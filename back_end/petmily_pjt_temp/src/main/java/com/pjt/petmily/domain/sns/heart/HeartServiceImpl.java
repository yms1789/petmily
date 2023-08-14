package com.pjt.petmily.domain.sns.heart;

import com.pjt.petmily.domain.noti.entity.Noti;
import com.pjt.petmily.domain.noti.entity.NotiType;
import com.pjt.petmily.domain.noti.repository.NotiRepository;
import com.pjt.petmily.domain.sns.board.Board;
import com.pjt.petmily.domain.sns.board.BoardRepository;
import com.pjt.petmily.domain.user.User;
import com.pjt.petmily.domain.user.exception.UserNotFoundException;
import com.pjt.petmily.domain.sns.board.BoardException.BoardNotFoundException;
import com.pjt.petmily.domain.sns.heart.HeartException.HeartNotFoundException;
import com.pjt.petmily.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class HeartServiceImpl implements HeartService {

    private final HeartRepository heartRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final NotiRepository notiRepository;

    public HeartServiceImpl(HeartRepository heartRepository, UserRepository userRepository, BoardRepository boardRepository, NotiRepository notiRepository) {
        this.heartRepository = heartRepository;
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.notiRepository = notiRepository;
    }

    @Transactional
    public void insert(HeartRequestDto heartRequestDTO) throws Exception {

        User user = userRepository.findByUserEmail(heartRequestDTO.getUserEmail())
                .orElseThrow(() -> new UserNotFoundException("Could not found user with email : " + heartRequestDTO.getUserEmail()));

        Board board = boardRepository.findById(heartRequestDTO.getBoardId())
                .orElseThrow(() -> new BoardNotFoundException("Could not found board id : " + heartRequestDTO.getBoardId()));

        // 이미 좋아요되어있으면 에러 반환
        if (heartRepository.findByUserAndBoard(user, board).isPresent())
        {
            throw new Exception();
        }

        Heart heart = Heart.builder()
                .board(board)
                .user(user)
                .build();

        heartRepository.save(heart);
        boardRepository.addHeartCount(board);

        Noti noti = Noti.builder()
                .notiType(NotiType.LIKE)
                .fromUser(user)
                .toUser(board.getUser())
                .createDate(LocalDateTime.now())
                .isChecked(false)
                .build();

        notiRepository.save(noti);
    }

    @Transactional
    public void delete(HeartRequestDto heartRequestDTO) {

        User user = userRepository.findByUserEmail(heartRequestDTO.getUserEmail())
                .orElseThrow(() -> new UserNotFoundException("Could not found user with email : " + heartRequestDTO.getUserEmail()));

        Board board = boardRepository.findById(heartRequestDTO.getBoardId())
                .orElseThrow(() -> new BoardNotFoundException("Could not found board id : " + heartRequestDTO.getBoardId()));

        Heart heart = heartRepository.findByUserAndBoard(user, board)
                .orElseThrow(() -> new HeartNotFoundException("Could not found heart id"));

        heartRepository.delete(heart);
        boardRepository.subHeartCount(board);
    }
}
