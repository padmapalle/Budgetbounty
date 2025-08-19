import React from 'react';

// A simple component to show when the user is logged out
function LoggedOutMessage() {
    return (
        <div className="logged-out-container">
            <h2>You have been logged out.</h2>
            <p className="intro-text">Please refresh the page if you wish to log back in.</p>
        </div>
    );
}

function PrivateRoute({ isLoggedIn, children }) {
  if (!isLoggedIn) {
    return <LoggedOutMessage />;
  }
  return children;
}

export default PrivateRoute;