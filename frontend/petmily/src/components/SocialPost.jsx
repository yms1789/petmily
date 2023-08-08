import { useEffect, useState } from 'react';
import Carousel from 'react-material-ui-carousel';
import { Paper, styled } from '@mui/material';
import ArrowCircleUpRoundedIcon from '@mui/icons-material/ArrowCircleUpRounded';
import CheckCircleOutlineRoundedIcon from '@mui/icons-material/CheckCircleOutlineRounded';
import DeleteForeverRoundedIcon from '@mui/icons-material/DeleteForeverRounded';
import DriveFileRenameOutlineRoundedIcon from '@mui/icons-material/DriveFileRenameOutlineRounded';
import EditNoteRoundedIcon from '@mui/icons-material/EditNoteRounded';
import FavoriteRoundedIcon from '@mui/icons-material/FavoriteRounded';

import { v4 as uuidv4 } from 'uuid';
import { PropTypes, number, string, bool } from 'prop-types';
import { useRecoilState, useSetRecoilState } from 'recoil';
import userAtom from 'states/users';
import updateimageAtom from 'states/updateimage';
import recommentAtom from 'states/recomment';
import inputAtom from 'states/input';
import parentAtom from 'states/parent';
import boardAtom from 'states/board';
import updatepreviewAtom from 'states/updatepreview';
import { placeholderImage, formatDate } from 'utils/utils';

import useFetch from 'utils/fetch';
import UploadImage from './UploadImage';
import DeleteConfirmation from './DeleteConfirmation';
import SocialComment from './SocialComment';
import SocialCommentInput from './SocialCommentInput';

function SocialPost({ post, readPosts, updatePost, deletePost }) {
  const [like, setLike] = useState(post.heartCount);
  const [actionLike, setActionLike] = useState(null);
  const StyledFavoriteRoundedIcon = styled(FavoriteRoundedIcon, {
    name: 'StyledFavoriteRoundedIcon',
    slot: 'Wrapper',
  })({
    color: actionLike ? '#f4245e' : '#A6A7AB',
    fontSize: 28,
    '&:hover': { color: '#f4245e' },
  });
  const StyledEditNoteRoundedIcon = styled(EditNoteRoundedIcon, {
    name: 'StyledEditNoteRoundedIcon',
    slot: 'Wrapper',
  })({
    color: '#A6A7AB',
    fontSize: 30,
    '&:hover': { color: '#1f90fe' },
  });
  const StyledDeleteForeverRoundedIcon = styled(DeleteForeverRoundedIcon, {
    name: 'StyledDeleteForeverRoundedIcon',
    slot: 'Wrapper',
  })({
    color: '#A6A7AB',
    fontSize: 26,
    '&:hover': { color: '#f4245e' },
  });
  const StyledArrowCircleUpRoundedIcon = styled(ArrowCircleUpRoundedIcon, {
    name: 'StyledArrowCircleUpRoundedIcon',
    slot: 'Wrapper',
  })({
    color: '#A6A7AB',
    fontSize: 26,
    transform: 'rotate(-90deg)',
    '&:hover': { color: '#f4245e' },
  });
  const StyledDriveFileRenameOutlineRoundedIcon = styled(
    DriveFileRenameOutlineRoundedIcon,
    {
      name: 'StyledDriveFileRenameOutlineRoundedIcon',
      slot: 'Wrapper',
    },
  )({
    color: '#A6A7AB',
    fontSize: 26,
    '&:hover': { color: '#1f90fe' },
  });
  const StyledCheckCircleOutlineRoundedIcon = styled(
    CheckCircleOutlineRoundedIcon,
    {
      name: 'StyledCheckCircleOutlineRoundedIcon',
      slot: 'Wrapper',
    },
  )({
    color: '#A6A7AB',
    fontSize: 26,
    '&:hover': { color: '#1f90fe' },
  });
  const userLogin = useRecoilState(userAtom);
  const { userEmail } = userLogin[0];

  const [editMode, setEditMode] = useState(false);
  const [editedText, setEditedText] = useState(post.boardContent);
  const [showDeleteConfirmation, setShowDeleteConfirmation] = useState(false);
  const [updateUploadedImage, setUpdateUploadedImage] =
    useRecoilState(updateimageAtom);
  const setUpdateFilePreview = useSetRecoilState(updatepreviewAtom);
  const setNickNameRecomment = useSetRecoilState(recommentAtom);
  const [boardIdRecomment, setBoardIdRecomment] = useRecoilState(boardAtom);
  const [parentIdRecomment, setParentIdRecomment] = useRecoilState(parentAtom);
  const setShowRecommentInput = useSetRecoilState(inputAtom);
  const [isHovered, setIsHovered] = useState(false);
  const showNextButton = post.photoUrls.length >= 2;
  const fetchSocialPost = useFetch();

  const parendIdRecomment = parentIdRecomment;
  const postIdRecomment = boardIdRecomment;

  const toggleEditMode = () => {
    setUpdateFilePreview([]);
    setUpdateUploadedImage([...updateUploadedImage]);
    setEditedText(post.boardContent);
    setEditMode(prevEditMode => !prevEditMode);
  };

  const handleTextChange = e => {
    setEditedText(e.target.value);
  };

  const handleUpdate = () => {
    updatePost(post, editedText);
    setEditMode(false);
  };

  const handleCancel = () => {
    setEditMode(false);
  };

  const handleDelete = () => {
    setShowDeleteConfirmation(true);
  };

  const handleCancelDelete = () => {
    setShowDeleteConfirmation(false);
  };

  const handleConfirmDelete = () => {
    deletePost(post.boardId);
    setShowDeleteConfirmation(false);
  };

  const [comments, setComments] = useState(post.comments);

  const readComments = async boardId => {
    try {
      const response = await fetchSocialPost.get(
        `board/${boardId}?currentUserEmail=${userEmail}`,
      );
      setComments(response.comments);
      console.log(
        '여기는 댓글 읽기인데 바로 업데이트는 안돼서 삭제하고 쓰임',
        comments,
      );
    } catch (error) {
      console.log(error);
    }
  };

  const createComment = async createCommentText => {
    const sendBE = {
      userEmail,
      boardId: postIdRecomment || post.boardId,
      commentContent: createCommentText,
      parentId: parendIdRecomment || null,
    };
    try {
      const response = await fetchSocialPost.post('comment/save', sendBE);
      console.log('여기댓글생성응답', response);
      setNickNameRecomment('');
      setShowRecommentInput(false);
      setBoardIdRecomment(0);
      setParentIdRecomment(0);
      readComments(postIdRecomment || post.boardId);
    } catch (error) {
      console.log(error);
    }
  };

  const deleteComment = async currentCommentId => {
    const response = await fetchSocialPost.delete(
      `comment/${currentCommentId}`,
    );
    console.log('댓글 삭제', response);
    readComments(post.boardId);
  };

  const [heartCount, setHeartCount] = useState();
  const [commentsCount, setCommentsCount] = useState();

  const handleLike = async () => {
    const sendBE = {
      userEmail,
      boardId: post.boardId,
    };

    if (actionLike === null) {
      try {
        const response = await fetchSocialPost.post('board/heart', sendBE);
        console.log('좋아요 응답 성공', response);
        setActionLike('liked');
        setLike(prev => prev + 1);
      } catch (error) {
        console.log(error);
      }
    } else if (actionLike === 'liked') {
      try {
        const response = await fetchSocialPost.delete('board/heart', sendBE);
        console.log('좋아요 취소 응답 성공', response);
        setActionLike(null);
        setLike(prev => prev - 1);
      } catch (error) {
        console.log(error);
      }
    }
  };

  useEffect(() => {
    if (like === 0) {
      setHeartCount('Likes');
    } else {
      setHeartCount(like);
    }
    if (post.comments.length === 0) {
      setCommentsCount('Comments');
    } else {
      setCommentsCount(post.comments.length);
    }
    readPosts();
  }, [post.comments.length, like, comments]);

  return (
    <div className="relative">
      <span className="mb-3 h-[0.06rem] w-full bg-gray2 inline-block" />
      <DeleteConfirmation
        show={showDeleteConfirmation}
        onCancel={handleCancelDelete}
        onConfirm={handleConfirmDelete}
      />
      <div className="flex flex-col px-[1rem] items-between justify-between">
        <div className="flex items-start">
          <div className="rounded-full overflow-hidden pr-2">
            <img
              className="rounded-full w-[3rem] h-[3rem] overflow-hidden object-cover"
              alt=""
              src={placeholderImage(1)}
            />
          </div>
          <div className="flex flex-col w-full gap-[0.5rem] mx-4">
            <div className="flex items-center justify-between text-slategray">
              <div className="flex gap-[0.5rem] items-center justify-between">
                <b className="text-gray text-lg">{post.userNickname}</b>
                <div className="font-medium text-sm">
                  {` · `}
                  {formatDate(post.boardUploadTime)}
                </div>
              </div>
              <div className="flex items-center justify-center">
                {editMode ? (
                  <>
                    <div
                      role="presentation"
                      className="gap-[0.5rem] rounded-full text-[1rem] w-fill h-[0.5rem] text-black flex p-[0.5rem] items-center justify-center"
                      onClick={handleCancel}
                    >
                      <StyledArrowCircleUpRoundedIcon className="mt-0.5" />
                    </div>
                    <div
                      type="submit"
                      role="presentation"
                      className="gap-[0.5rem] rounded-full text-[1rem] w-fill h-[0.5rem] text-black flex p-[0.5rem] items-center justify-center"
                      onClick={handleUpdate}
                    >
                      <StyledCheckCircleOutlineRoundedIcon className="mt-0.5" />
                    </div>
                  </>
                ) : (
                  <>
                    <div
                      role="presentation"
                      className="gap-[0.5rem] rounded-full text-[1rem] w-fill h-[0.5rem] text-black flex p-[0.5rem] items-center justify-center"
                      onClick={toggleEditMode}
                    >
                      <StyledDriveFileRenameOutlineRoundedIcon className="mt-0.5" />
                    </div>
                    <div
                      role="presentation"
                      className="gap-[0.5rem] rounded-full text-[1rem] w-fill h-[0.5rem] text-black flex p-[0.5rem] items-center justify-center"
                      onClick={handleDelete}
                    >
                      <StyledDeleteForeverRoundedIcon className="mt-0.5" />
                    </div>
                  </>
                )}
              </div>
            </div>
            {editMode ? (
              <div className="h-full w-fill">
                <div className="w-fill mr-[2.3rem]">
                  <textarea
                    rows="5"
                    value={editedText}
                    onChange={handleTextChange}
                    className="resize-none mt-2 w-full h-full text-black rounded-xl p-4 border-solid border-[2px] border-dodgerblue focus:outline-none font-pretendard text-base"
                  />
                </div>
                <UploadImage page="소통하기수정" />
              </div>
            ) : (
              post.boardContent && (
                <div className="break-all font-pretendard text-base mt-2 font-base w-fill text-black rounded-xl p-4 border-solid border-[2px] border-gray2 focus:outline-none focus:border-dodgerblue">
                  {post.boardContent}
                </div>
              )
            )}
            {post.photoUrls.length > 0 ? (
              <Carousel
                className={`z-0 w-full h-[40rem] ${
                  post.boardContent ? 'mt-3' : 'mt-0'
                } rounded-xl overflow-hidden object-cover`}
                autoPlay={false}
                animation="slide"
                fullHeightHover
                indicatorContainerProps={{
                  style: {
                    zIndex: 1,
                    marginTop: '-2rem',
                    position: 'relative',
                    opacity: post.photoUrls?.length === 1 ? 0 : 0.3,
                  },
                }}
                navButtonsAlwaysVisible={showNextButton}
                navButtonsAlwaysInvisible={!showNextButton}
                navButtonsProps={{
                  style: {
                    opacity: isHovered ? 1 : 0.3,
                  },
                }}
                cycleNavigation={false}
                onMouseEnter={() => {
                  setIsHovered(true);
                }}
                onMouseLeave={() => {
                  setIsHovered(false);
                }}
              >
                {post.photoUrls?.map(photos => {
                  return (
                    <Paper key={photos}>
                      <div className="w-full h-[40rem] flex rounded-xl overflow-hidden object-cover justify-center items-center">
                        <img
                          src={photos}
                          className="w-full h-[40rem] rounded-xl overflow-hidden object-cover"
                          alt=""
                        />
                      </div>
                    </Paper>
                  );
                })}
              </Carousel>
            ) : null}
            <div className="flex justify-start h-full mt-2 gap-[0.2rem]">
              <div
                role="presentation"
                className="gap-[0.5rem] rounded-full text-sm font-semibold w-fill h-[0.5rem] text-black flex p-[0.5rem] items-center justify-center"
                onClick={handleLike}
              >
                <StyledFavoriteRoundedIcon className="" />
                <div>{heartCount}</div>
              </div>
              <div
                role="presentation"
                className="gap-[0.5rem] rounded-full text-sm font-semibold w-fill h-[0.5rem] text-black flex p-[0.5rem] items-center justify-center"
              >
                <StyledEditNoteRoundedIcon className="" />
                <div>{commentsCount}</div>
              </div>
            </div>
            {post.comments?.map(c => {
              return (
                <div key={uuidv4()}>
                  <SocialComment
                    key={uuidv4()}
                    comments={c}
                    deleteComment={deleteComment}
                  />
                </div>
              );
            })}
            <span className="m-3 h-[0.02rem] w-fill bg-gray2 inline-block" />
            <SocialCommentInput
              createComment={createComment}
              boardId={post.boardId}
            />
          </div>
        </div>
      </div>
    </div>
  );
}

SocialPost.propTypes = {
  post: PropTypes.shape({
    boardContent: string,
    boardId: number,
    boardUploadTime: string,
    comments: PropTypes.arrayOf(
      PropTypes.shape({
        boardId: number,
        commentContent: string,
        commentId: number,
        commentTime: string,
        parentId: number,
        userEmail: string,
      }),
    ).isRequired,
    hashTags: PropTypes.arrayOf(string).isRequired,
    heartCount: string,
    likedByCurrentUser: bool,
    photoUrls: PropTypes.arrayOf(string).isRequired,
    userEmail: string,
    userNickname: string,
    userProfileImageUrl: string,
    // modifyState: bool,
  }).isRequired,
  readPosts: PropTypes.func.isRequired,
  updatePost: PropTypes.func.isRequired,
  deletePost: PropTypes.func.isRequired,
};

export default SocialPost;
