import { useState, useEffect } from 'react';
import { useRecoilValue } from 'recoil';

import userAtom from 'states/users';
import chatAtom from 'states/chat';
import useFetch from 'utils/fetch';
import { formatDate } from 'utils/utils';

function ChatMessage() {
  const fetchData = useFetch();

  const userLogin = useRecoilValue(userAtom);
  const chatId = useRecoilValue(chatAtom);
  const [messages, setMessages] = useState([]);

  const readChatHistory = async () => {
    const sendBE = {
      sender: userLogin.userEmail,
      receiver: chatId[0],
    };
    try {
      const response = await fetchData.post('/chat/history', sendBE);
      console.log('채팅 대화 조회', response);
      setMessages(response);
    } catch (error) {
      console.log('채팅 대화 조회', error);
    }
  };

  useEffect(() => {
    readChatHistory();
  }, []);

  return (
    <div>
      <div className="grow flex flex-col w-full my-2 overflow-scrolljustify-between overflow-x-hidden">
        <div className="flex flex-col w-full justify-between">
          {messages?.map(msg => {
            return (
              <div className="w-full flex flex-col">
                {msg.writer !== userLogin.userEmail && (
                  <div key={msg} className="flex items-end gap-2">
                    <div className="flex items-center">
                      <div className="px-[0.8rem] h-[2rem] w-[2rem] rounded-full overflow-hidden">
                        <img
                          src={chatId[2]}
                          className="h-full w-full rounded-full overflow-hidden"
                          alt=""
                        />
                      </div>
                      <div className="px-4 py-2 bg-gray2 w-fit rounded-2xl my-2">
                        <div className="break-all max-w-[11rem] w-fit text-sm text-black">
                          {msg.message}
                        </div>
                      </div>
                    </div>
                    <div className="text-xs text-slategray my-2">
                      {formatDate(msg.createdAt)}
                    </div>
                  </div>
                )}
                {msg.writer === userLogin.userEmail && (
                  <div key={msg} className="flex justify-end items-end gap-2">
                    <div className="text-xs text-slategray my-2">
                      {formatDate(msg.createdAt)}
                    </div>
                    <div className="flex items-center">
                      <div className="px-4 py-2 bg-gray2 w-fit rounded-2xl my-2">
                        <div className="break-all max-w-[11rem] w-fit text-sm text-black">
                          {msg.message}
                        </div>
                      </div>
                      <div className="px-[0.8rem] h-[2rem] w-[2rem] rounded-full overflow-hidden">
                        <img
                          src={userLogin.userProfileImage}
                          className="h-full w-full rounded-full overflow-hidden"
                          alt=""
                        />
                      </div>
                    </div>
                  </div>
                )}
              </div>
            );
          })}
        </div>
      </div>
    </div>
  );
}

export default ChatMessage;
