import { useEffect } from 'react';

function NaverLoginPage() {
  useEffect(() => {
    const naverLogin = new window.naver.LoginWithNaverId({
      clientId: `${process.env.REACT_APP_NAVER_CLIENT_ID}`,
      callbackUrl: `http://localhost:3000/auth/naver/callback`,
      isPopup: true,
      loginButton: { color: 'green', type: 1, height: '50' },
    });
    naverLogin.init();
    naverLogin.logout();
    try {
      naverLogin.getLoginStatus(status => {
        if (status) {
          console.log(naverLogin.user);
        }
      });
    } catch (err) {
      console.log(err);
    }
  }, []);

  return (
    <div className="connect">
      <div id="naverIdLogin" />
    </div>
  );
}

export default NaverLoginPage;
