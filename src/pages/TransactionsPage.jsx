import React, { useState, useEffect } from 'react';
import './TransactionsPage.css';
import { getActivities, getRewards } from '../lib/api'; // Import API functions

// Helper function to format the ActivityType enum into a readable string
const formatActivityType = (type) => {
  if (!type) return "Reward";
  return type.replace(/_/g, ' ').replace(/\w\S*/g, (txt) => txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase());
};

function TransactionsPage() {
  const userId = Number(process.env.REACT_APP_DEFAULT_USER_ID || 2);
  const [transactions, setTransactions] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchTransactions = async () => {
      try {
        // Fetch both rewards (for points) and activities (for descriptions)
        const [rewardsData, activitiesData] = await Promise.all([
          getRewards(userId),
          getActivities(userId)
        ]);

        // Create a map for quick lookup of activity descriptions
        const activityMap = new Map(activitiesData.map(act => [act.activityId, act.activityType]));
        
        // Combine rewards with their activity descriptions
        const earnedTransactions = rewardsData.map(reward => ({
          id: reward.rewardId,
          date: reward.earnedAt,
          description: formatActivityType(activityMap.get(reward.activityId)),
          points: reward.points
        }));

        // NOTE: To show 'spent' points, an endpoint to fetch redemptions is needed.
        // For now, we will only display the history of earned points.
        
        const allTransactions = [...earnedTransactions];

        // Sort all transactions by date, most recent first
        allTransactions.sort((a, b) => new Date(b.date) - new Date(a.date));
        
        setTransactions(allTransactions);

      } catch (error) {
        console.error("Failed to fetch transaction data:", error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchTransactions();
  }, [userId]);

  if (isLoading) {
    return <div>Loading transaction history...</div>;
  }

  return (
    <div className="transactions-page">
      <h2>Your Points History</h2>
      <table className="transaction-table">
        <thead>
          <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Points</th>
          </tr>
        </thead>
        <tbody>
          {transactions.map(tx => (
            <tr key={tx.id}>
              <td>{new Date(tx.date).toLocaleDateString()}</td>
              <td>{tx.description}</td>
              <td className={tx.points > 0 ? 'points-earned' : 'points-spent'}>
                {tx.points > 0 ? '+' : ''}{tx.points.toLocaleString()}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default TransactionsPage;
