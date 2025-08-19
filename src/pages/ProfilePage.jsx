import React, { useState, useEffect } from 'react';
import { Bar } from 'react-chartjs-2';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';
import './ProfilePage.css';
import { getUser, getRewards } from '../lib/api'; // Import API functions

// Register Chart.js components
ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);

// Helper function to process rewards data for the chart
const processRewardsForChart = (rewards) => {
  const monthlyData = {};
  const monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];

  // Initialize the last 6 months
  for (let i = 5; i >= 0; i--) {
    const d = new Date();
    d.setMonth(d.getMonth() - i);
    const monthKey = `${d.getFullYear()}-${d.getMonth()}`;
    const monthName = monthNames[d.getMonth()];
    if (!monthlyData[monthKey]) {
      monthlyData[monthKey] = { points: 0, label: monthName, sort: d.getTime() };
    }
  }
  
  rewards.forEach(reward => {
    const date = new Date(reward.earnedAt);
    const monthKey = `${date.getFullYear()}-${date.getMonth()}`;
    if (monthlyData[monthKey]) {
      monthlyData[monthKey].points += reward.points;
    }
  });

  const sortedMonths = Object.values(monthlyData).sort((a, b) => a.sort - b.sort);
  
  return {
    labels: sortedMonths.map(m => m.label),
    pointsEarned: sortedMonths.map(m => m.points),
  };
};


function ProfilePage() {
  const userId = Number(process.env.REACT_APP_DEFAULT_USER_ID || 2);
  const [user, setUser] = useState(null);
  const [chartData, setChartData] = useState({ labels: [], datasets: [] });
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [userData, rewardsData] = await Promise.all([
          getUser(userId),
          getRewards(userId)
        ]);
        setUser(userData);

        // Process rewards to generate chart data
        const activity = processRewardsForChart(rewardsData);
        setChartData({
          labels: activity.labels,
          datasets: [
            {
              label: 'Points Earned',
              data: activity.pointsEarned,
              backgroundColor: 'rgba(168, 85, 247, 0.7)',
            },
          ],
        });

      } catch (error) {
        console.error("Failed to fetch profile data:", error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchData();
  }, [userId]);

  const chartOptions = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
      title: {
        display: true,
        text: 'Monthly Points Earned',
      },
    },
  };

  if (isLoading) {
    return <div>Loading profile...</div>;
  }
  
  if (!user) {
    return <div>Could not load user profile.</div>;
  }

  // Use the part of the email before the '@' as a display name
  const displayName = user.email.split('@')[0];

  return (
    <div className="profile-page">
      <h1>Welcome, {displayName}!</h1>
      
      <div className="points-summary">
        <div className="points-label">Your Current Balance</div>
        <div className="points-total">{user.points.toLocaleString()} pts</div>
      </div>

      <div className="chart-container">
        <Bar options={chartOptions} data={chartData} />
      </div>
    </div>
  );
}

export default ProfilePage;
