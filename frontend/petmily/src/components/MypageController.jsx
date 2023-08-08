import { arrayOf, string } from 'prop-types';
import RenderBookmarks from './RenderBookmarks';

const showItems = (contents, category) => {
  switch (category) {
    case '게시글':
      return <>1</>;
    case '북마크':
      return <RenderBookmarks renderData={contents} />;

    case '좋아요':
      return 1;
    default:
      return null;
  }
};

function MypageController({ contents, category }) {
  return <>{showItems(contents, category)}</>;
}
MypageController.propTypes = {
  contents: arrayOf(),
  category: string,
};
export default MypageController;
