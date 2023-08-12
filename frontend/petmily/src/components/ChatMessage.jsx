import { useState, useEffect } from 'react';
import { Client } from '@stomp/stompjs';
import axios from 'axios';
import { string } from 'prop-types';
import { useRecoilValue } from 'recoil';
import userAtom from 'states/users';

function ChatMessage({ username }) {
  const userLogin = useRecoilValue(userAtom);
  const [messages, setMessages] = useState([]);
  const [stompClient, setStompClient] = useState(null);

  const readChatHistory = async () => {
    const sendBE = {
      sender: userLogin.userEmail,
      receiver: 'string',
    };
    try {
      const response = await axios.post('chat/history', sendBE);
      console.log('채팅 대화 조회', response);
      setMessages(response.data);
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    const client = new Client();
    client.configure({
      brokerURL: 'ws://3.36.117.233:8081/ws',
      onConnect: () => {
        client.subscribe(`/user/${username}/queue/messages`, message => {
          const parsedMessage = JSON.parse(message.body);
          setMessages(prevMessages => [...prevMessages, parsedMessage]);
        });
      },
    });

    client.activate();
    setStompClient(client);

    readChatHistory();
    return () => {
      if (stompClient) {
        stompClient.deactivate();
      }
    };
  }, [username, stompClient]);

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

ChatMessage.propTypes = {
  username: string,
};

export default ChatMessage;
