import DeleteForeverRoundedIcon from '@mui/icons-material/DeleteForeverRounded';
import { styled } from '@mui/material';
import { PropTypes, number, string } from 'prop-types';
import { useState } from 'react';
import { useRecoilState } from 'recoil';
import recommentAtom from 'states/recomment';
import { placeholderImage } from 'utils/utils';
import DeleteConfirmation from './DeleteConfirmation';
import SocialRecomment from './SocialRecomment';

function SocialComment({ post, comments, deleteComment }) {
  const StyledDeleteForeverRoundedIcon = styled(DeleteForeverRoundedIcon, {
    name: 'StyledDeleteForeverRoundedIcon',
    slot: 'Wrapper',
  })({
    color: '#A6A7AB',
    fontSize: 25,
    '&:hover': { color: '#f4245e' },
  });

  const [showDeleteConfirmation, setShowDeleteConfirmation] = useState(false);
  const [showRecommentInput, setShowRecommentInput] =
    useRecoilState(recommentAtom);

  const handleDelete = () => {
    setShowDeleteConfirmation(true);
  };

  const handleCancelDelete = () => {
    setShowDeleteConfirmation(false);
  };

  const handleConfirmDelete = () => {
    deleteComment(comments.id);
    setShowDeleteConfirmation(false);
  };

  const handleRecomment = (currentPostId, currentCommentId) => {
    setShowRecommentInput(prevState => {
      // 현재 게시글과 댓글 ID가 이미 존재하는지 확인합니다.
      const existingItemIndex = prevState.findIndex(
        item => item.pId === currentPostId && item.cId === currentCommentId,
      );

      // 이미 존재하는 경우, 해당 항목의 rData 값을 업데이트합니다.
      if (existingItemIndex !== -1) {
        const updatedItem = {
          ...prevState[existingItemIndex],
          rData: !prevState[existingItemIndex].rData,
        };
        const updatedState = [...prevState];
        updatedState[existingItemIndex] = updatedItem;
        return updatedState;
      }

      // 존재하지 않는 경우, 새로운 항목을 배열에 추가합니다.
      return [
        ...prevState,
        {
          pId: currentPostId,
          cId: currentCommentId,
          rData: true,
        },
      ];
    });
  };
  console.log(showRecommentInput);

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
            src={placeholderImage(90)}
          />
        </div>
        <div className="flex flex-col gap-[0.6rem] mx-4 w-full">
          <div className="flex items-center justify-between text-slategray">
            <div className="flex items-center gap-[0.3rem]">
              <b className="text-gray">Devon Lane</b>
              <div className="font-medium">@johndue</div>
            </div>
            <div className="flex items-center gap-[0.5rem]">
              <div className="text-slategray font-medium pt-[0.2rem]">{`23s `}</div>
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
              {comments.text}
            </div>
            <div
              role="presentation"
              className="cursor-pointer font-pretendard text-dodgerblue text-base font-semibold whitespace-nowrap"
              onClick={() => handleRecomment(post, comments.id)}
            >
              답글 달기
            </div>
          </div>
          <SocialRecomment />
        </div>
      </div>
    </div>
  );
}

SocialComment.propTypes = {
  post: number,
  comments: PropTypes.arrayOf(
    PropTypes.shape({
      text: string,
      id: number,
    }),
  ),
  deleteComment: PropTypes.func.isRequired,
};

export default SocialComment;
