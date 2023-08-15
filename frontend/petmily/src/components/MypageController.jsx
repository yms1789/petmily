import { arrayOf, string } from 'prop-types';
import RenderBookmarks from './RenderBookmarks';
import RenderPosts from './RenderPosts';

const showItems = (contents, category) => {
  switch (category) {
    case '게시글':
      return <RenderPosts page="my" />;
    case '북마크':
      return <RenderBookmarks />;

    case '좋아요':
      return <RenderPosts page="like" />;
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
