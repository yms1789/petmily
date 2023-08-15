import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil';
import authAtom from 'states/auth';
import chatAtom from 'states/chat';
import chatroomAtom from 'states/chatroom';
import userAtom from 'states/users';
import useFetch from 'utils/fetch';

function ChatRoom() {
  const navigate = useNavigate();
  const fetchData = useFetch();

  const auth = useRecoilValue(authAtom);
  const [chatRoom, setChatRoom] = useState(chatroomAtom);
  const [userLogin, setUser] = useRecoilState(userAtom);
  const chatId = useRecoilValue(chatAtom);
  const setChatId = useSetRecoilState(chatAtom);

  const readChatRoom = async () => {
    try {
      const response = await fetchData.get(`/chat/${userLogin.userEmail}`);
      console.log('채팅방 목록 조회', response);
      setChatRoom(response);
    } catch (error) {
      console.log('채팅방 목록 조회', error);
    }
  };

  const handleOpenChat = (chatEmail, chatRoomId, chatProfile, e) => {
    e.preventDefault();
    if (chatRoom && chatRoom.length > 0) {
      setChatId([chatEmail, chatRoomId, chatProfile]);
    }
    navigate(`/social/chat/${chatRoomId}`);
  };

  useEffect(() => {
    if (!auth || !Object.keys(auth).length) {
      setUser(null);
      navigate('/login');
    }
    readChatRoom();
    console.log('클릭한 방', chatId);
  }, []);

  return (
    <div className="mx-4 basis-1/4 flex h-[800px] rounded-xl bg-white min-w-[20%] flex-col p-[1rem] items-start justify-start gap-[0.38rem] font-pretendard">
      <div className="flex w-full flex-col items-start justify-center gap-[1rem] text-[1.25rem]">
        <div className="ml-1 font-semibold">메세지 목록</div>
        <div className="bg-slate-200 w-full h-[1.5px]" />
      </div>
      {Array.isArray(chatRoom) &&
        chatRoom?.map(room => {
          return (
            <div
              role="presentation"
              key={room.participants[0].userId}
              className="self-stretch flex flex-col items-start justify-start gap-[0.63rem]"
              onClick={e => {
                handleOpenChat(
                  room.participants[0].userEmail,
                  room.roomId,
                  room.participants[0].userProfile,
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
                      src={room.participants[0].userProfile}
                    />
                  </div>
                  <div className="flex flex-col items-start justify-start gap-[0.3rem]">
                    <b className="">{room.participants[0].userNickname}</b>
                    <div className="text-[1rem] font-medium text-slategray">
                      {room.latestMessage ? room.latestMessage : null}
                    </div>
                  </div>
                </div>
                {room.unreadMessageCount ? (
                  <div className="rounded-full bg-dodgerblue h-8 w-8 overflow-hidden whitespace-nowrap flex flex-row box-border items-center justify-center text-center text-sm text-white">
                    <b className="">{room.unreadMessageCount}</b>
                  </div>
                ) : null}
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
