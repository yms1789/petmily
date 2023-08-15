import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { styled } from '@mui/material';
import SendRoundedIcon from '@mui/icons-material/SendRounded';
import CloseRoundedIcon from '@mui/icons-material/CloseRounded';

import { Stomp } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';

import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil';
import userAtom from 'states/users';
import chatAtom from 'states/chat';
import authAtom from 'states/auth';
import chatroomAtom from 'states/chatroom';
import chatmessagesAtom from 'states/chatmessages';
import useFetch from 'utils/fetch';
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
  const fetchData = useFetch();

  const [messageTexts, setMessageTexts] = useState('');
  const [stompClient, setStompClient] = useState('');

  const auth = useRecoilValue(authAtom);
  const chatId = useRecoilValue(chatAtom);
  const [userLogin, setUser] = useRecoilState(userAtom);
  const setChatRoom = useSetRecoilState(chatroomAtom);
  const [messages, setMessages] = useRecoilState(chatmessagesAtom);

  const readChatRoom = async () => {
    try {
      const response = await fetchData.get(`/chat/${userLogin.userEmail}`);
      console.log('채팅방 목록 조회', response);
      setChatRoom(response);
    } catch (error) {
      console.log('채팅방 목록 조회', error);
    }
  };

  const connectChat = async () => {
    const socket = new SockJS('http://i9d209.p.ssafy.io:8081/chatting');
    const client = Stomp.over(() => socket); // 연결이 끊어졌을 때 다시 연결하는 팩토리 함수

    client.onConnect = () => {
      readChatRoom();
      client.subscribe(`/sub/room/${chatId[1]}`, message => {
        console.log('채팅방 입장!');
        setMessages([]);
        const parsedMessage = JSON.parse(message.body);
        console.log(
          '새 메시지:',
          parsedMessage.message,
          '발신자:',
          parsedMessage.writer,
        );
        setMessages({
          id: chatId[1],
          writer: parsedMessage.writer,
          message: parsedMessage.message,
        });
      });
    };

    client.activate();
    setStompClient(client); // stompClient
  };

  useEffect(() => {
    if (!auth || !Object.keys(auth).length) {
      setUser(null);
      navigate('/login');
    }
    connectChat();
    console.log('여기는 챗의 메세지상태', messages);
    return () => {
      if (stompClient) {
        stompClient.deactivate();
      }
    };
  }, [chatId]);

  const handleMessageChange = e => {
    setMessageTexts(e.target.value);
  };

  const handleSendMessage = messageInput => {
    if (!messageInput.trim()) {
      return;
    }
    const sendBE = {
      roomId: chatId[1],
      writer: userLogin.userEmail,
      message: messageInput,
    };
    if (stompClient) {
      stompClient.send('/pub/message', {}, JSON.stringify(sendBE));
    }

    setMessageTexts('');
  };

  const handleCloseChat = () => {
    navigate('/social');
  };

  return (
    <div className="pb-6 pt-2 basis-1/2 min-w-[400px] h-[800px] rounded-xl bg-white flex flex-col justify-between text-black font-pretendard">
      <div className="w-full h-full text-base flex flex-col justify-center gap-4">
        <div className="mt-4 flex-none w-full flex flex-row items-center justify-between">
          <div className="ml-6 w-full flex items-center gap-4">
            <img
              className="h-10 w-10 rounded-full overflow-hidden object-cover"
              alt=""
              src={chatId[2]}
            />
            <div className="text-2lg font-bold">{chatId[0]}</div>
          </div>
          <StyledCloseRoundedIcon
            className="mr-6"
            role="presentation"
            onClick={handleCloseChat}
          />
        </div>
        <div className="my-2 mx-4 flex-none bg-slate-200 w-fill h-[1.5px]" />
        <div className="border-solid border-[1px] rounded-xl border-slate-300 mx-4 grow flex flex-col w-fill mb-2 overflow-scroll overflow-x-hidden">
          <div className="flex ">
            <div className="w-full ">
              <ChatMessage />
            </div>
          </div>
        </div>
        <div className="mb-2 mx-8 flex-none gap-[0.5rem] flex justify-end items-center h-fit w-fill">
          <div className="relative w-full border-solid border-[1px] border-slate-300 flex items-center justify-between rounded-11xl bg-white max-w-full h-[3rem]">
            <div className="absolute left-0 px-[0.6rem] h-[2rem] w-[2rem] rounded-full overflow-hidden">
              <img
                src={userLogin ? userLogin.userProfileImg : ''}
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
              onClick={() => handleSendMessage(messageTexts)}
            />
          </div>
        </div>
      </div>
    </div>
  );
}

export default Chat;
