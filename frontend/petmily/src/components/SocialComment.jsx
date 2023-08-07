/* eslint-disable react/prop-types */
import { useState } from 'react';

import DeleteForeverRoundedIcon from '@mui/icons-material/DeleteForeverRounded';
import { styled } from '@mui/material';
import { PropTypes, number, string } from 'prop-types';
import { useRecoilState, useSetRecoilState } from 'recoil';
import recommentAtom from 'states/recomment';
import inputAtom from 'states/input';
import parentAtom from 'states/parent';
import boardAtom from 'states/board';
import { placeholderImage, formatDate } from 'utils/utils';
import DeleteConfirmation from './DeleteConfirmation';

function SocialComment({ comments, deleteComment }) {
  const StyledDeleteForeverRoundedIcon = styled(DeleteForeverRoundedIcon, {
    name: 'StyledDeleteForeverRoundedIcon',
    slot: 'Wrapper',
  })({
    color: '#A6A7AB',
    fontSize: 22,
    '&:hover': { color: '#f4245e' },
  });

  const [showDeleteConfirmation, setShowDeleteConfirmation] = useState();
  const setNickNameRecomment = useSetRecoilState(recommentAtom);
  const setBoardIdRecomment = useSetRecoilState(boardAtom);
  const setParentIdRecomment = useSetRecoilState(parentAtom);
  const [showRecommentInput, setShowRecommentInput] = useRecoilState(inputAtom);

  const handleDelete = () => {
    setShowDeleteConfirmation(true);
  };

  const handleCancelDelete = () => {
    setShowDeleteConfirmation(false);
  };

  const handleConfirmDelete = () => {
    deleteComment(comments.commentId);
    setShowDeleteConfirmation(false);
  };

  const handleRecomment = comment => {
    setShowRecommentInput(!showRecommentInput);
    setNickNameRecomment(comment.userNickname);
    setBoardIdRecomment(comment.boardId);
    setParentIdRecomment(comment.commentId);
  };

  return (
    <div>
      <span className="h-[0.06rem] w-full bg-gray2 inline-block m-0 p-0" />

      <div className="relative flex items-start mt-3">
        <DeleteConfirmation
          page="댓글"
          show={showDeleteConfirmation}
          onCancel={handleCancelDelete}
          onConfirm={handleConfirmDelete}
        />
        <div className="w-[2.5rem] h-[2.5rem]">
          <img
            className="w-[2.5rem] h-[2.5rem] object-cover rounded-full overflow-hidden"
            alt=""
            src={placeholderImage(5)}
          />
        </div>
        <div className="flex flex-col gap-[0.6rem] mx-4 w-full">
          <div className="flex items-center justify-between text-slategray">
            <div className="flex items-center gap-[0.5rem]">
              <b className="text-gray text-semibold text-lg">
                {comments.userNickname}
              </b>
              <div className="font-medium text-sm">
                {`· `}
                {formatDate(comments.commentTime)}
              </div>
              {/* <div className="font-medium">{comments.user.userLikePet}</div> */}
            </div>
            <div className="flex items-center gap-[0.5rem]">
              <div
                role="presentation"
                className="gap-[0.5rem] rounded-full text-[1rem] w-fill h-[0.5rem] text-black flex items-center justify-center cursor-pointer"
                onClick={handleDelete}
              >
                <StyledDeleteForeverRoundedIcon />
              </div>
            </div>
          </div>
          <div className="w-full flex justify-between items-end">
            <div className="break-all mr-[4.5rem] font-pretendard text-gray">
              {comments.commentContent}
            </div>
            <div
              role="presentation"
              className="cursor-pointer font-pretendard text-dodgerblue text-sm font-semibold whitespace-nowrap"
              onClick={() => handleRecomment(comments)}
            >
              답글 달기
            </div>
          </div>
          {/* <SocialRecomment /> */}
        </div>
      </div>
    </div>
  );
}

SocialComment.propTypes = {
  comments: PropTypes.shape({
    boardId: number,
    commentContent: string,
    commentTime: string,
    parentId: number,
    replies: null,
    userEmail: string,
  }).isRequired,
  deleteComment: PropTypes.func.isRequired,
};

export default SocialComment;
