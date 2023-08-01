import { styled } from '@mui/material';
import AddCircleOutlineRoundedIcon from '@mui/icons-material/AddCircleOutlineRounded';
import { placeholderImage } from 'utils/utils';

function SocialCommentInput() {
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
  return (
    <div className="gap-[0.5rem] flex justify-start items-center h-full w-full">
      <div className="relative w-full border-solid border-[1px] border-gray2 flex items-center justify-between rounded-11xl bg-white max-w-full h-[3rem]">
        <div className="absolute left-0 px-[0.6rem] h-[2rem] w-[2rem] rounded-full overflow-hidden">
          <img
            src={placeholderImage}
            className="h-full w-full rounded-full overflow-hidden"
            alt=""
          />
        </div>
        <input
          className="focus:outline-none w-full h-auto py-[0.8rem] px-[3.5rem] focus:outline-dodgerblue focus:border-1.5 font-pretendard text-base
lex items-center font-medium rounded-full"
          placeholder="검색어를 입력하세요"
        />
        <StyledAddCircleOutlineRoundedIcon
          className="absolute right-0 px-[1rem]"
          onClick={() => {
            console.log('click');
          }}
        />
      </div>
    </div>
  );
}

export default SocialCommentInput;
