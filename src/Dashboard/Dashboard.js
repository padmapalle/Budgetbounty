// src/Dashboard/Dashboard.js

// import React, { useEffect, useState } from "react";
// import "./Dashboard.css";
// import { getUser, getActivities, getRewards, getGoals } from "../lib/api";
// // Import icons from the library you just installed
// import { FaStar, FaCheckCircle, FaListAlt } from 'react-icons/fa';

// export default function Dashboard() {
//   const userId = Number(process.env.REACT_APP_DEFAULT_USER_ID || 2);
//   const [user, setUser] = useState(null);
//   const [activities, setActivities] = useState([]);
//   const [goals, setGoals] = useState([]);
//   const [isLoading, setIsLoading] = useState(true);

//   useEffect(() => {
//     // This is an Immediately Invoked Function Expression (IIFE)
//     // to allow using async/await directly inside useEffect.
//     (async () => {
//       try {
//         // Fetch all data in parallel for better performance
//         const [userData, activitiesData, rewardsData, goalsData] = await Promise.all([
//           getUser(userId),
//           getActivities(userId),
//           getRewards(userId), 
//           getGoals(userId),
//         ]);
//         setUser(userData);
//         setActivities(activitiesData);
//         setGoals(goalsData);
//       } catch (e) {
//         console.error("Dashboard load failed", e);
//       } finally {
//         setIsLoading(false);
//       }
//     })(); // The typo was in this area
//   }, [userId]);

//   if (isLoading) {
//     return <div className="loading-message">Loading Dashboard...</div>;
//   }

//   const points = user?.points ?? 0;
//   const completedGoals = goals.filter(g => g.isAchieved).length;
//   const displayName = user?.email.split('@')[0] || 'User';

//   return (
//     <div className="dashboard-container">
//       <h2 className="welcome-header">Welcome back, {displayName}!</h2>
//       <p className="intro-text">Here is a summary of your account activity.</p>
      
//       <div className="dashboard-grid">
//         <div className="summary-card">
//           <FaStar className="card-icon" color="#f59e0b" />
//           <div className="card-content">
//             <h3>Total Points</h3>
//             <p className="big-number">{points.toLocaleString()}</p>
//           </div>
//         </div>
//         <div className="summary-card">
//           <FaCheckCircle className="card-icon" color="#22c55e" />
//           <div className="card-content">
//             <h3>Completed Goals</h3>
//             <p className="big-number">{completedGoals}</p>
//           </div>
//         </div>
//         <div className="summary-card">
//           <FaListAlt className="card-icon" color="#3b82f6" />
//           <div className="card-content">
//             <h3>Activities</h3>
//             <p className="big-number">{activities.length}</p>
//           </div>
//         </div>
//       </div>
//     </div>
//   );
// }

// src/Dashboard/Dashboard.js

// import React, { useEffect, useState } from "react";
// import { Link } from 'react-router-dom'; // Import Link for navigation
// import "./Dashboard.css";
// import { getUser, getActivities, getRewards, getGoals } from "../lib/api";
// import { FaStar, FaCheckCircle, FaListAlt } from 'react-icons/fa';

// export default function Dashboard() {
//   const userId = Number(process.env.REACT_APP_DEFAULT_USER_ID || 2);
//   const [user, setUser] = useState(null);
//   const [activities, setActivities] = useState([]);
//   const [goals, setGoals] = useState([]);
//   const [isLoading, setIsLoading] = useState(true);

//   useEffect(() => {
//     (async () => {
//       try {
//         const [userData, activitiesData, rewardsData, goalsData] = await Promise.all([
//           getUser(userId),
//           getActivities(userId),
//           getRewards(userId), 
//           getGoals(userId),
//         ]);
//         setUser(userData);
//         setActivities(activitiesData);
//         setGoals(goalsData);
//       } catch (e) {
//         console.error("Dashboard load failed", e);
//       } finally {
//         setIsLoading(false);
//       }
//     })();
//   }, [userId]);

//   if (isLoading) {
//     return <div className="loading-message">Loading Dashboard...</div>;
//   }

//   const points = user?.points ?? 0;
//   const completedGoals = goals.filter(g => g.isAchieved).length;
//   const displayName = user?.email.split('@')[0] || 'User';

//   return (
//     <div className="dashboard-container">
//       <h2 className="welcome-header">Welcome back, {displayName}!</h2>
//       <p className="intro-text">Here is a summary of your account activity.</p>
      
//       <div className="dashboard-grid">
//         {/* Wrap card in a Link component to make it clickable */}
//         <Link to="/user/redeem" className="summary-card-link">
//           <div className="summary-card">
//             <FaStar className="card-icon" color="#f59e0b" />
//             <div className="card-content">
//               <h3>Total Points</h3>
//               <p className="big-number">{points.toLocaleString()}</p>
//             </div>
//           </div>
//         </Link>
        
//         {/* This card can link to a future goals page */}
//         <div className="summary-card">
//           <FaCheckCircle className="card-icon" color="#22c55e" />
//           <div className="card-content">
//             <h3>Completed Goals</h3>
//             <p className="big-number">{completedGoals}</p>
//           </div>
//         </div>
        
//         {/* Wrap card in a Link component to make it clickable */}
//         <Link to="/user/transactions" className="summary-card-link">
//           <div className="summary-card">
//             <FaListAlt className="card-icon" color="#3b82f6" />
//             <div className="card-content">
//               <h3>Activities</h3>
//               <p className="big-number">{activities.length}</p>
//             </div>
//           </div>
//         </Link>
//       </div>
//     </div>
//   );
// }

// src/Dashboard/Dashboard.js

import React, { useEffect, useState } from "react";
import { Link } from 'react-router-dom';
import "./Dashboard.css";
import { getActivities, getGoals } from "../lib/api"; // <-- Removed getUser and getRewards
import { FaStar, FaCheckCircle, FaListAlt } from 'react-icons/fa';

// Receive user as a prop
export default function Dashboard({ user }) {
  const [activities, setActivities] = useState([]);
  const [goals, setGoals] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const userId = user?.userId;

  useEffect(() => {
    // Don't run the effect if the user object isn't loaded yet
    if (!userId) {
        setIsLoading(user === null); // If user is null, we are loading. If not, it might be an error.
        return;
    }

    const fetchData = async () => {
      try {
        // Only fetch data specific to this component
        const [activitiesData, goalsData] = await Promise.all([
          getActivities(userId),
          getGoals(userId),
        ]);
        setActivities(activitiesData);
        setGoals(goalsData);
      } catch (e) {
        console.error("Dashboard data load failed", e);
      } finally {
        setIsLoading(false);
      }
    };
    
    fetchData();
  }, [userId, user]); // Depend on user object

  if (isLoading) {
    return <div className="loading-message">Loading Dashboard...</div>;
  }

  // Use the user prop directly
  const points = user?.points ?? 0;
  const completedGoals = goals.filter(g => g.isAchieved).length;
  const displayName = user?.email.split('@')[0] || 'User';

  return (
    <div className="dashboard-container">
      <h2 className="welcome-header">Welcome back, {displayName}!</h2>
      <p className="intro-text">Here is a summary of your account activity.</p>
      
      <div className="dashboard-grid">
        <Link to="/user/redeem" className="summary-card-link">
          <div className="summary-card">
            <FaStar className="card-icon" color="#f59e0b" />
            <div className="card-content">
              <h3>Total Points</h3>
              <p className="big-number">{points.toLocaleString()}</p>
            </div>
          </div>
        </Link>
        
        <div className="summary-card">
          <FaCheckCircle className="card-icon" color="#22c55e" />
          <div className="card-content">
            <h3>Completed Goals</h3>
            <p className="big-number">{completedGoals}</p>
          </div>
        </div>
        
        <Link to="/user/transactions" className="summary-card-link">
          <div className="summary-card">
            <FaListAlt className="card-icon" color="#3b82f6" />
            <div className="card-content">
              <h3>Activities</h3>
              <p className="big-number">{activities.length}</p>
            </div>
          </div>
        </Link>
      </div>
    </div>
  );
}