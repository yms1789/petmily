import { useState, useEffect } from 'react';
import { useRecoilValue } from 'recoil';

import userAtom from 'states/users';
import chatAtom from 'states/chat';
import useFetch from 'utils/fetch';

function ChatMessage() {
  const fetchChatMessage = useFetch();

  const userLogin = useRecoilValue(userAtom);
  const chatId = useRecoilValue(chatAtom);
  const [messages, setMessages] = useState([]);

  const readChatHistory = async () => {
    const sendBE = {
      sender: userLogin.userEmail,
      receiver: chatId[0],
    };
    try {
      const response = await fetchChatMessage.post('/chat/history', sendBE);
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
      {messages.map(msg => {
        return (
          <div key={msg} className="flex items-end gap-2">
            <div className="text-xs text-slategray my-2">12:20</div>
            <div className="px-4 py-2 bg-gray2 w-fit rounded-2xl my-2">
              <div className="break-all max-w-[11rem] w-fit text-sm">
                {msg.content}
              </div>
            </div>
            <div className="text-xs text-slategray my-2">12:20</div>
          </div>
        );
      })}
    </div>
  );
}

export default ChatMessage;
