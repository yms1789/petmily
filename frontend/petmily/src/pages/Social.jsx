import { useState, useEffect } from 'react';

import CheckRoundedIcon from '@mui/icons-material/CheckRounded';
import RefreshRoundedIcon from '@mui/icons-material/RefreshRounded';
import { styled } from '@mui/material';
import {
  FollowRecommend,
  SearchBar,
  SocialPost,
  UploadProfileImage,
  Messages,
} from 'components';
import { placeholderImage } from 'utils/utils';
import useFetch from 'utils/fetch';

function Social() {
  const StyledRefreshRoundedIcon = styled(RefreshRoundedIcon, {
    name: 'StyledRefreshRoundedIcon',
    slot: 'Wrapper',
  })({
    color: '#0F1419',
    fontSize: 26,
    '&:hover': { color: '#1f90fe' },
  });
  const [posts, setPosts] = useState([]);
  const [uploadedImage, setUploadedImage] = useState([]);
  const [postText, setPostText] = useState('');
  // const [clickCreatePost, setClickCreatePost] = useState(false);
  // const [postId, setPostId] = useState(0);
  const fetchSocial = useFetch();

  const onPostTextChange = e => {
    setPostText(e.currentTarget.value);
  };
  const loadPosts = async () => {
    try {
      const response = await fetchSocial.get('board/all');
      setPosts(response.data);
    } catch (error) {
      console.log(error);
    }
  };

  const createPost = async createPostText => {
    const boardRequestDto = {
      userEmail: 'yms1789@naver.com',
      boardContent: createPostText,
    };

    const formData = new FormData();

    formData.append(
      'boardRequestDto',
      new Blob([JSON.stringify(boardRequestDto)], {
        type: 'application/json',
      }),
    );

    if (uploadedImage && uploadedImage.length > 0) {
      formData.append('file', ...uploadedImage);
    } else {
      formData.append('file', null);
    }

    try {
      const response = await fetchSocial.post('board/save', formData, 'image');
      console.log(response);
      setPostText('');
      loadPosts();
    } catch (error) {
      console.log(error);
    }

    // setPostId(postId + 1);
    // const newPost = {
    //   postId,
    //   postText: createPostText,
    // };
    // setPost([...post, newPost]);
  };

  useEffect(() => {
    loadPosts();
  }, []);

  const updatePost = (currentPostId, updatePostText) => {
    const updatedPost = posts.map(p =>
      p.id === currentPostId ? { ...p, text: updatePostText } : p,
    );
    setPosts(updatedPost);
  };

  const deletePost = currentPostId => {
    setPosts(posts.filter(p => p.id !== currentPostId));
  };

  const onSubmitNewPost = e => {
    e.preventDefault();
    createPost(postText);
  };

  return (
    <div className="pb-[10rem] min-w-[1340px] max-w-full w-full absolute top-[6.5rem] flex justify-between">
      <Messages />
      <div className="basis-1/2 min-w-[400px] rounded-lg flex flex-col gap-4">
        <SearchBar page="소통하기" />
        <div className="rounded-xl bg-white w-full h-fill flex flex-col items-center justify-center text-[1rem] text-black">
          <div className="flex flex-col gap-5 w-full my-4">
            <div className="flex justify-between w-full">
              <div className="font-semibold text-[1.25rem] mx-6">뉴 피드</div>
              <div className="mx-6">
                <StyledRefreshRoundedIcon />
              </div>
            </div>
            <span className="h-[0.06rem] w-full bg-gray2 inline-block" />
            <form
              encType="multipart/form-data"
              onSubmit={onSubmitNewPost}
              className="relative flex pb-12 flex-col px-[1rem] items-between justify-between"
            >
              <div className="flex items-start">
                <div className="w-[3rem] h-[3rem] pr-4 overflow-hidden">
                  <img
                    className="rounded-full w-[3rem] h-[3rem] overflow-hidden object-cover"
                    alt=""
                    src={placeholderImage(70)}
                  />
                </div>
                <textarea
                  onChange={onPostTextChange}
                  value={postText}
                  name="post"
                  cols="80"
                  rows="5"
                  placeholder="자유롭게 이야기 해보세요!"
                  className="resize-none font-medium w-full text-black mx-4 rounded-xl p-4 border-solid border-[2px] border-gray2 focus:outline-none focus:border-dodgerblue font-pretendard text-base"
                />
              </div>
              <UploadProfileImage
                page="소통하기"
                uploadedImage={uploadedImage}
                setUploadedImage={setUploadedImage}
              />
              <button
                type="submit"
                className="absolute right-4 bottom-0 cursor-pointer rounded-full text-[1rem] w-[1.2rem] h-[0rem] text-white bg-dodgerblue border-solid border-[2px] border-dodgerblue flex py-[1rem] px-[1.4rem] ml-[0.4rem] mr-[1rem] items-center justify-center opacity-[1]"
              >
                <CheckRoundedIcon />
              </button>
            </form>
            {posts?.map(p => {
              return (
                <div key={p.boardId}>
                  <SocialPost
                    post={p}
                    updatePost={updatePost}
                    deletePost={deletePost}
                  />
                </div>
              );
            })}
          </div>
        </div>
      </div>
      <FollowRecommend />
    </div>
  );
}

export default Social;
