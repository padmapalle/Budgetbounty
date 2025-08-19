// import React, { useState } from 'react';
// import { Routes, Route, Navigate } from 'react-router-dom';

// // Import layout components
// import Navbar from './Navbar/Navbar';
// import Dashboard from './Dashboard/Dashboard';
// import PrivateRoute from './PrivateRoute';

// // Import page components
// import RewardsPage from './pages/RewardsPage';
// import TransactionsPage from './pages/TransactionsPage';
// import RedeemPage from './pages/RedeemPage';
// import ProfilePage from './pages/ProfilePage'; // <-- Import the new page

// function App() {
//   const [isLoggedIn, setIsLoggedIn] = useState(true);
//   const currentUserName = "Alex";

//   function handleLogout() {
//     setIsLoggedIn(false);
//   }

//   return (
//     <div>
//       <Navbar isLoggedIn={isLoggedIn} onLogout={handleLogout} />

//       <div className="content">
//         <Routes>
//           <Route path="/" element={<Navigate to="/dashboard" />} />
          
//           <Route 
//             path="/dashboard" 
//             element={
//               <PrivateRoute isLoggedIn={isLoggedIn}>
//                 <Dashboard userName={currentUserName} />
//               </PrivateRoute>
//             } 
//           />
          
//           <Route path="/user/rewards" element={<PrivateRoute isLoggedIn={isLoggedIn}><RewardsPage /></PrivateRoute>} />
//           <Route path="/user/transactions" element={<PrivateRoute isLoggedIn={isLoggedIn}><TransactionsPage /></PrivateRoute>} />
//           <Route path="/user/redeem" element={<PrivateRoute isLoggedIn={isLoggedIn}><RedeemPage /></PrivateRoute>} />
          
//           {/* Add the new route for the profile page */}
//           <Route path="/user/profile" element={<PrivateRoute isLoggedIn={isLoggedIn}><ProfilePage /></PrivateRoute>} />
//         </Routes>
//       </div>
//     </div>
//   );
// }

// export default App;

// src/App.js

import React, { useState, useEffect } from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import { getUser } from './lib/api'; // Import the getUser function

// Import layout and page components
import Navbar from './Navbar/Navbar';
import Dashboard from './Dashboard/Dashboard';
import PrivateRoute from './PrivateRoute';
import RewardsPage from './pages/RewardsPage';
import TransactionsPage from './pages/TransactionsPage';
import RedeemPage from './pages/RedeemPage';
import ProfilePage from './pages/ProfilePage';

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(true);
  const [user, setUser] = useState(null); // State to hold user data
  const userId = Number(process.env.REACT_APP_DEFAULT_USER_ID || 2);

  // Function to fetch/refresh the user data
  const fetchUser = async () => {
    try {
      const userData = await getUser(userId);
      setUser(userData);
    } catch (error) {
      console.error("Failed to fetch user:", error);
      setUser(null); // Set to null on failure
    }
  };

  // Fetch the user data when the app first loads
  useEffect(() => {
    if (isLoggedIn) {
      fetchUser();
    }
  }, [isLoggedIn, userId]);

  function handleLogout() {
    setIsLoggedIn(false);
    setUser(null);
  }

  return (
    <div>
      <Navbar isLoggedIn={isLoggedIn} onLogout={handleLogout} />

      <div className="content">
        <Routes>
          <Route path="/" element={<Navigate to="/dashboard" />} />
          
          <Route 
            path="/dashboard" 
            element={
              <PrivateRoute isLoggedIn={isLoggedIn}>
                {/* Pass the user object to the Dashboard */}
                <Dashboard user={user} />
              </PrivateRoute>
            } 
          />
          
          <Route path="/user/rewards" element={<PrivateRoute isLoggedIn={isLoggedIn}><RewardsPage /></PrivateRoute>} />
          <Route path="/user/transactions" element={<PrivateRoute isLoggedIn={isLoggedIn}><TransactionsPage /></PrivateRoute>} />
          
          {/* Pass user data and the fetchUser function to RedeemPage */}
          <Route 
            path="/user/redeem" 
            element={
              <PrivateRoute isLoggedIn={isLoggedIn}>
                <RedeemPage user={user} onRedemptionSuccess={fetchUser} />
              </PrivateRoute>
            } 
          />
          
          {/* Pass user data to ProfilePage */}
          <Route 
            path="/user/profile" 
            element={
              <PrivateRoute isLoggedIn={isLoggedIn}>
                <ProfilePage user={user} />
              </PrivateRoute>
            } 
          />
        </Routes>
      </div>
    </div>
  );
}

export default App;