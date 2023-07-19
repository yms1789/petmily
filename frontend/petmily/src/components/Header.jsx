import React from 'react';
import { Link } from 'react-router-dom';

function Header() {
  return (
    <div
      style={{
        display: 'flex',

        flexDirection: 'column',
      }}
    >
      <ul>
        <li>
          <Link to="/">큐레이션 !</Link>
        </li>
        <li>
          <Link to="/Social">소통하기</Link>
        </li>
        <li>
          <Link to="/Product">인기용품 최저가</Link>
        </li>
      </ul>
    </div>
  );
}

export default Header;
