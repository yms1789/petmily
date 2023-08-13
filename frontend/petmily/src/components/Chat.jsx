import { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { styled } from '@mui/material';
import SendRoundedIcon from '@mui/icons-material/SendRounded';
import CloseRoundedIcon from '@mui/icons-material/CloseRounded';
// import * as StompJs from '@stomp/stompjs';
import { Client } from '@stomp/stompjs';
import { useRecoilValue } from 'recoil';
import userAtom from 'states/users';
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
  const { receiver, chatRoomId } = useParams(); // URL에서 채팅방 ID 가져오기
  const userLogin = useRecoilValue(userAtom);
  const [messages, setMessages] = useState([]);
  const [stompClient, setStompClient] = useState(null);
  const [messageTexts, setMessageTexts] = useState('');

  useEffect(() => {
    const client = new Client();
    client.configure({
      brokerURL: 'ws://3.36.117.233:8081/ws',
      onConnect: () => {
        client.subscribe(`/chatting/pub/room/${chatRoomId}`, message => {
          const parsedMessage = JSON.parse(message.body);
          setMessages(prevMessages => [...prevMessages, parsedMessage]);
          console.log('sender가 보내는', messages);
        });
      },
    });

    client.activate();
    setStompClient(client);

    return () => {
      if (stompClient) {
        stompClient.deactivate();
      }
    };
  }, [stompClient]);

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
      roomId: chatRoomId, // 생성 시 받은 채팅방 id
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
            <div className="text-2lg font-bold">{receiver}</div>
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
              <ChatMessage username={userLogin.userEmail} />
            </div>
          </div>
          <div className="flex justify-end w-full">
            <div>
              <ChatMessage username={receiver} />
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
