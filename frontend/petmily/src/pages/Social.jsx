import { string } from 'prop-types';
import { FollowRecommend, SocialFeed, Messages, Chat } from 'components';

function Social({ page }) {
  return (
    <div className="pb-5 min-w-[1340px] max-w-full w-full absolute top-[6.5rem] flex justify-between font-pretendard">
      <Messages />
      {page === 'feed' ? <SocialFeed /> : null}
      {page === 'chat' ? <Chat /> : null}
      <FollowRecommend />
    </div>
  );
}
Social.propTypes = {
  page: string,
};

export default Social;
