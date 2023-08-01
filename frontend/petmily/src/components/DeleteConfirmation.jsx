import React from 'react';
import { PropTypes, bool } from 'prop-types';

function DeleteConfirmation({ show, onCancel, onConfirm }) {
  if (!show) return null;

  return (
    <div className="rounded-lg absolute top-0 w-full h-[10rem] mt-1">
      <div className="bg-blue50 [backdrop-filter:blur(6px)] gap-3 flex flex-col justify-center items-center w-fill h-full m-2 rounded-xl">
        <div className="text-xl font-pretendard font-bold">
          이 게시물을 정말 삭제하시겠습니까?
        </div>
        <div className="flex gap-3">
          <button
            type="submit"
            onClick={onCancel}
            className="cursor-pointer rounded-full whitespace-nowrap w-[1.2rem] h-[0rem] text-dodgerblue bg-white border-solid border-[2px] border-dodgerblue hover:bg-dodgerblue hover:text-white flex py-[0.8rem] px-[1.4rem] items-center justify-center"
          >
            <div className="font-base font-pretendard font-bold">취소</div>
          </button>
          <button
            type="submit"
            onClick={onConfirm}
            className="cursor-pointer rounded-full whitespace-nowrap w-[1.2rem] h-[0rem] text-red bg-white border-solid border-[2px] border-red hover:bg-red hover:text-white flex py-[0.8rem] px-[1.4rem] items-center justify-center"
          >
            <div className="font-base font-pretendard font-bold">삭제</div>
          </button>
        </div>
      </div>
    </div>
  );
}

DeleteConfirmation.propTypes = {
  show: bool,
  onCancel: PropTypes.func.isRequired,
  onConfirm: PropTypes.func.isRequired,
};

export default DeleteConfirmation;