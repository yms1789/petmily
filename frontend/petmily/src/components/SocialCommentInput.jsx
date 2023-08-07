import { useState, useEffect } from 'react';

import AddCircleOutlineRoundedIcon from '@mui/icons-material/AddCircleOutlineRounded';
import { styled } from '@mui/material';
import { func, string } from 'prop-types';
import { useRecoilState } from 'recoil';
import recommentAtom from 'states/recomment';
import { placeholderImage } from 'utils/utils';

function SocialCommentInput({ createComment, boardId }) {
  const StyledAddCircleOutlineRoundedIcon = styled(
    AddCircleOutlineRoundedIcon,
    {
      name: 'StyledAddCircleOutlineRoundedIcon',
      slot: 'Wrapper',
    },
  )({
    color: '#A6A7AB',
    fontSize: 26,
    '&:hover': { color: '#1f90fe' },
  });

  const [commentTexts, setCommentTexts] = useState('');
  const [showRecommentInput] = useRecoilState(recommentAtom);

  const handleCommentChange = e => {
    setCommentTexts(e.target.value);
  };

  const onSubmitNewComment = e => {
    e.preventDefault();
    createComment(commentTexts);
    setCommentTexts('');
  };

  const [checkShow, setCheckShow] = useState(false);

  useEffect(() => {
    if (showRecommentInput[0] && boardId === showRecommentInput[3]) {
      setCheckShow(!checkShow);
      console.log(boardId);
    }
  }, [boardId, showRecommentInput]);
  console.log('여기는 소셜 코멘트 인풋', checkShow, showRecommentInput);

  return checkShow ? (
    <div className="gap-[0.5rem] flex justify-start items-center h-full w-full">
      <div className="relative w-full border-solid border-[1px] border-gray2 flex items-center justify-between rounded-11xl bg-white max-w-full h-[3rem]">
        <div className="absolute left-0 px-[0.6rem] flex gap-3 justify-center items-center">
          <div className="h-[2rem] w-[2rem] rounded-full overflow-hidden">
            <img
              src={placeholderImage(30)}
              className="h-full w-full rounded-full overflow-hidden"
              alt=""
            />
          </div>
          <div className="whitespace-nowrap w-[4.5rem] text-sm font-pretendard bg-lightblue text-dodgerblue font-bold flex justify-center items-center h-[1.5rem] px-2 rounded-full">
            {`@ ${showRecommentInput[1]}`}
          </div>
        </div>
        <input
          className="focus:outline-none w-full h-auto py-[0.8rem] pl-[9.3rem] pr-[3.5rem] focus:outline-dodgerblue focus:border-1.5 font-pretendard text-base
lex items-center font-medium rounded-full"
          placeholder="답글을 입력하세요"
          onChange={e => handleCommentChange(e)}
          value={commentTexts}
        />
        <StyledAddCircleOutlineRoundedIcon
          className="absolute right-0 px-[1rem]"
          onClick={onSubmitNewComment}
        />
      </div>
    </div>
  ) : (
    <div className="gap-[0.5rem] flex justify-start items-center h-full w-full">
      <div className="relative w-full border-solid border-[1px] border-gray2 flex items-center justify-between rounded-11xl bg-white max-w-full h-[3rem]">
        <div className="absolute left-0 px-[0.6rem] h-[2rem] w-[2rem] rounded-full overflow-hidden">
          <img
            src={placeholderImage(30)}
            className="h-full w-full rounded-full overflow-hidden"
            alt=""
          />
        </div>
        <input
          className="focus:outline-none w-full h-auto py-[0.8rem] px-[3.5rem] focus:outline-dodgerblue focus:border-1.5 font-pretendard text-base
  lex items-center font-medium rounded-full"
          placeholder="댓글을 입력하세요"
          onChange={e => handleCommentChange(e)}
          value={commentTexts}
        />
        <StyledAddCircleOutlineRoundedIcon
          className="absolute right-0 px-[1rem]"
          onClick={onSubmitNewComment}
        />
      </div>
    </div>
  );
}

SocialCommentInput.propTypes = {
  createComment: func.isRequired,
  boardId: string,
};

export default SocialCommentInput;
