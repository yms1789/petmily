import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { styled } from '@mui/material';
import SendRoundedIcon from '@mui/icons-material/SendRounded';
import CloseRoundedIcon from '@mui/icons-material/CloseRounded';
// import * as StompJs from '@stomp/stompjs';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { useRecoilValue } from 'recoil';
import userAtom from 'states/users';
import chatAtom from 'states/chat';
import { placeholderImage } from 'utils/utils';
import ChatMessage from './ChatMessage';

function Chat() {
  const StyledSendRoundedIcon = styled(SendRoundedIcon, {
    name: 'StyledSendRoundedIcon',
    slot: 'Wrapper',
  })({
    color: '#A6A7AB',
    fontSize: 22,
    '&:hover': { color: '#1f90fe' },
  });
  const StyledCloseRoundedIcon = styled(CloseRoundedIcon, {
    name: 'StyledCloseRoundedIcon',
    slot: 'Wrapper',
  })({
    color: '#A6A7AB',
    fontSize: 22,
    '&:hover': { color: '#1f90fe' },
  });

  const navigate = useNavigate();
  const userLogin = useRecoilValue(userAtom);
  const chatId = useRecoilValue(chatAtom);
  const [messages, setMessages] = useState([]);
  const [messageTexts, setMessageTexts] = useState('');

  useEffect(() => {
    const socket = new SockJS('http://3.36.117.233:8081/sockjs-node'); // sockjs-client로 소켓 연결 생성
    const stompClient = new Client({
      brokerURL: socket, // sockjs-client로 생성한 소켓을 사용하여 Stomp 클라이언트 생성
      // ... 다른 Stomp 클라이언트 설정 ...
    });

    stompClient.onConnect = () => {
      stompClient.subscribe(`/chatting/pub/room/${chatId[1]}`, message => {
        const parsedMessage = JSON.parse(message.body);
        setMessages(prevMessages => [...prevMessages, parsedMessage]);
        console.log('sender가 보내는', messages);
      });
    };

    stompClient.activate();

    return () => {
      if (stompClient) {
        stompClient.deactivate(); // Stomp 클라이언트 비활성화
      }
    };
  }, []);

  const handleCloseChat = () => {
    navigate('/social');
  };

  const handleMessageChange = e => {
    setMessageTexts(e.target.value);
  };

  const handleSendMessage = (messageInput, username) => {
    if (!messageTexts.trim()) {
      return;
    }

    const sendBE = {
      roomId: chatId[1],
      writer: username,
      message: messageInput,
    };
    stompClient.publish({
      destination: '/chatting/pub/message',
      body: JSON.stringify(sendBE),
    });

    setMessageTexts('');
  };

  return (
    <div className="basis-1/2 min-w-[400px] h-[800px] rounded-xl bg-white flex flex-col justify-between text-black font-pretendard">
      <div className="w-full h-full text-base flex flex-col justify-center gap-4">
        <div className="mt-4 flex-none w-full flex flex-row items-center justify-between">
          <div className="ml-6 w-full flex items-center gap-4">
            <img
              className="h-10 w-10 rounded-full overflow-hidden object-cover"
              alt=""
              src={placeholderImage(42)}
            />
            <div className="text-2lg font-bold">{chatId[0]}</div>
          </div>
          <StyledCloseRoundedIcon
            className="mr-6"
            role="presentation"
            onClick={handleCloseChat}
          />
        </div>
        <div className="mx-4 flex-none bg-slate-200 w-fill h-[1.5px]" />
        <div className="mx-4 grow flex flex-col w-fill my-2 overflow-scroll overflow-x-hidden">
          <div className="flex">
            <div className="px-[0.8rem] h-[2rem] w-[2rem] rounded-full overflow-hidden">
              <img
                src={userLogin.userProfileImg}
                className="h-full w-full rounded-full overflow-hidden"
                alt=""
              />
            </div>
            <div>
              <ChatMessage />
            </div>
          </div>
          <div className="flex justify-end w-full">
            {/* <div>
              <ChatMessage username={receiver} />
            </div> */}
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
              onChange={e => handleMessageChange(e)}
              value={messageTexts}
            />
            <StyledSendRoundedIcon
              className="absolute right-0 px-[1rem]"
              onClick={e => handleSendMessage(e)}
            />
          </div>
        </div>
      </div>
    </div>
  );
}

export default Chat;
