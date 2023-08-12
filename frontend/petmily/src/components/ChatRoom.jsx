import axios from 'axios';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import userAtom from 'states/users';
import { placeholderImage } from 'utils/utils';

// const tempMessages = Array.from({ length: 5 }, (_, i) => i);

function ChatRoom() {
  const navigate = useNavigate();
  const userLogin = useRecoilValue(userAtom);
  const [chatRoom, setChatRoom] = useState([]);

  const handleOpenChat = () => {
    navigate('/social/chat');
  };

  const readChatRoom = async () => {
    const sendBE = {
      sender: userLogin.userNickname,
      receiver: 'string',
    };
    try {
      const response = await axios.post('chat/history', sendBE);
      console.log('채팅방 조회', response);
      setChatRoom(response);
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    readChatRoom();
  }, []);

  return (
    <div className="mx-4 basis-1/4 flex h-[800px] rounded-xl bg-white min-w-[20%] flex-col p-[1rem] items-start justify-start gap-[0.38rem] font-pretendard">
      <div className="flex w-full flex-col items-start justify-center gap-[1rem] text-[1.25rem]">
        <div className="ml-1 font-semibold">메세지 목록</div>
        <div className="bg-slate-200 w-full h-[1.5px]" />
      </div>
      {chatRoom.map(ele => {
        return (
          <div
            role="presentation"
            key={ele}
            className="self-stretch flex flex-col items-start justify-start gap-[0.63rem]"
            onClick={handleOpenChat}
          >
            <div className="w-full flex flex-row py-[0.75rem] px-[1rem] box-border items-center justify-between">
              <div className="w-fill gap-4 flex flex-row items-center justify-between">
                <div className="h-11 w-11 rounded-full overflow-hidden">
                  <img
                    className="h-11 w-11 overflow-hidden object-cover"
                    alt=""
                    src={placeholderImage(35)}
                  />
                </div>
                <div className="flex flex-col items-start justify-start gap-[0.3rem]">
                  <b className="">Bessie Cooper</b>
                  <div className="text-[1rem] font-medium text-slategray">
                    blahblah
                  </div>
                </div>
              </div>
              <div className="rounded-full bg-dodgerblue h-8 w-8 overflow-hidden whitespace-nowrap flex flex-row box-border items-center justify-center text-center text-sm text-white">
                <b className="">{ele}</b>
              </div>
            </div>
            {ele < chatRoom.length - 1 ? (
              <div className="bg-slate-200 w-full h-[1px]" />
            ) : null}
          </div>
        );
      })}
    </div>
  );
}

export default ChatRoom;
