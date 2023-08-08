import { useState } from 'react';
import { styled } from '@mui/material';
import SendRoundedIcon from '@mui/icons-material/SendRounded';
import { placeholderImage } from 'utils/utils';

const tempChats = Array.from({ length: 5 }, (_, i) => i);
function Chat() {
  const StyledSendRoundedIcon = styled(SendRoundedIcon, {
    name: 'StyledSendRoundedIcon',
    slot: 'Wrapper',
  })({
    color: '#A6A7AB',
    fontSize: 22,
    '&:hover': { color: '#1f90fe' },
  });

  const [chatTexts, setChatTexts] = useState('');

  const createChat = currentChatText => {
    console.log(currentChatText);
  };

  const handleChatChange = e => {
    setChatTexts(e.target.value);
  };

  const onSubmitNewChat = e => {
    console.log('내가 채팅 보냄');
    e.preventDefault();
    if (chatTexts) {
      createChat(chatTexts);
      setChatTexts('');
    }
  };

  return (
    <div className="basis-1/2 min-w-[400px] h-[870px] rounded-xl bg-white flex flex-col justify-between text-black font-pretendard">
      <div className="w-full h-full text-base flex flex-col justify-center gap-2">
        <div className="mt-4 mx-4 flex-none w-full px-3 flex flex-row items-center justify-start gap-4">
          <div className="">
            <img
              className="h-10 w-10 rounded-full overflow-hidden object-cover"
              alt=""
              src={placeholderImage(42)}
            />
          </div>
          <div className="text-2lg font-bold">하동혁</div>
        </div>
        <div className="mx-4 flex-none bg-slate-200 w-fill h-[1.5px]" />
        <div className="mx-4 grow flex flex-col w-fill my-2 overflow-scroll overflow-x-hidden">
          <div className="flex">
            <div className="px-[0.8rem] h-[2rem] w-[2rem] rounded-full overflow-hidden">
              <img
                src={placeholderImage(30)}
                className="h-full w-full rounded-full overflow-hidden"
                alt=""
              />
            </div>
            <div>
              {tempChats.map(ele => {
                return (
                  <div key={ele} className="flex items-end gap-2">
                    <div className="px-4 py-2 bg-gray2 w-fit rounded-2xl my-2">
                      <div className="break-all max-w-[11rem] w-fit text-sm">
                        메세지 하하gkkkkkkkkkkkkkgmkkkkkkkkksdfsdfsdfsdfsdf
                      </div>
                    </div>
                    <div className="text-xs text-slategray my-2">12:20</div>
                  </div>
                );
              })}
            </div>
          </div>
          <div className="flex justify-end w-full">
            <div>
              {tempChats.map(ele => {
                return (
                  <div key={ele} className="flex items-end gap-2">
                    <div className="text-xs text-slategray my-2">12:20</div>
                    <div className="px-4 py-2 bg-gray2 w-fit rounded-2xl my-2">
                      <div className="break-all max-w-[11rem] w-fit text-sm">
                        메세지 하하gkkkkkkkkkkkkkgmkkkkkkkkksdfsdfsdfsdfsdf
                      </div>
                    </div>
                  </div>
                );
              })}
            </div>
            <div className="px-[0.8rem] h-[2rem] w-[2rem] rounded-full overflow-hidden">
              <img
                src={placeholderImage(30)}
                className="h-full w-full rounded-full overflow-hidden"
                alt=""
              />
            </div>
          </div>
        </div>
        <div className="mb-4 mx-8 flex-none gap-[0.5rem] flex justify-end items-center h-fit w-fill">
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
              placeholder="메세지를 입력하세요"
              onChange={e => handleChatChange(e)}
              value={chatTexts}
            />
            <StyledSendRoundedIcon
              className="absolute right-0 px-[1rem]"
              onClick={e => onSubmitNewChat(e)}
            />
          </div>
        </div>
      </div>
    </div>
  );
}

export default Chat;
