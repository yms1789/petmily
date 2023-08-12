import axios from 'axios';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import userAtom from 'states/users';

function ChatRoom() {
  const navigate = useNavigate();
  const userLogin = useRecoilValue(userAtom);
  const [chatRoom, setChatRoom] = useState([]);

  const readChatRoom = async () => {
    try {
      const response = await axios.get(`chat/${userLogin.userEmail}`);
      console.log('채팅방 목록 조회', response);
      setChatRoom(response.data);
    } catch (error) {
      console.log(error);
    }
  };

  const handleOpenChat = (receiver, chatRoomId, e) => {
    e.preventDefault();
    navigate(`/social/chat/${receiver}/${chatRoomId}`);
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
      {chatRoom?.map(room => {
        return (
          <div
            role="presentation"
            key={room.participants?.userId}
            className="self-stretch flex flex-col items-start justify-start gap-[0.63rem]"
            onClick={e => {
              handleOpenChat(
                room.participants.userNickname,
                room.participants.chatRooms,
                e,
              );
            }}
          >
            <div className="w-full flex flex-row py-[0.75rem] px-[1rem] box-border items-center justify-between">
              <div className="w-fill gap-4 flex flex-row items-center justify-between">
                <div className="h-11 w-11 rounded-full overflow-hidden">
                  <img
                    className="h-11 w-11 overflow-hidden object-cover"
                    alt=""
                    src={room.participants.userProfileImg}
                  />
                </div>
                <div className="flex flex-col items-start justify-start gap-[0.3rem]">
                  <b className="">{room.userNickname}</b>
                  <div className="text-[1rem] font-medium text-slategray">
                    {room.latestMessage?.messages}
                  </div>
                </div>
              </div>
              <div className="rounded-full bg-dodgerblue h-8 w-8 overflow-hidden whitespace-nowrap flex flex-row box-border items-center justify-center text-center text-sm text-white">
                <b className="">{room.latestMessage?.length || null}</b>
              </div>
            </div>
            {room < chatRoom.length - 1 ? (
              <div className="bg-slate-200 w-full h-[1px]" />
            ) : null}
          </div>
        );
      })}
    </div>
  );
}

export default ChatRoom;
