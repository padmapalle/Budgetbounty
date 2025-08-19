import React, { useState, useEffect } from 'react';
import './RedeemPage.css';
import { getCatalog, redeemItem } from '../lib/api';

// Receive user and onRedemptionSuccess as props
function RedeemPage({ user, onRedemptionSuccess }) {
  const [catalog, setCatalog] = useState([]);
  const [selectedReward, setSelectedReward] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  const [isRedeeming, setIsRedeeming] = useState(false);
  const userId = user?.userId;

  useEffect(() => {
    // Only needs to fetch the catalog now
    getCatalog()
      .then(catalogData => {
        setCatalog(catalogData.filter(item => item.active));
      })
      .catch(error => {
        console.error("Failed to fetch catalog:", error);
      })
      .finally(() => {
        setIsLoading(false);
      });
  }, []);

  const handleSelectReward = (reward) => {
    if (user.points < reward.pointsRequired) {
      alert("You don't have enough points for this reward.");
      return;
    }
    setSelectedReward(reward);
  };
  
  const handleGoBack = () => {
    setSelectedReward(null);
  };

  const handleConfirmRedemption = async () => {
    if (!selectedReward || !userId) return;

    setIsRedeeming(true);
    try {
      await redeemItem({ userId, catalogItemId: selectedReward.catalogItemId });
      alert(`Successfully redeemed "${selectedReward.name}"!`);
      
      // Call the function from App.js to refresh the central user data
      await onRedemptionSuccess(); 
      
      setSelectedReward(null);
    } catch (error) {
      console.error("Redemption failed:", error);
      alert("Redemption failed. Please try again.");
    } finally {
      setIsRedeeming(false);
    }
  };

  if (isLoading) {
    return <div>Loading rewards...</div>;
  }
  
  if (!user) {
    return <div>Could not load user data. Please check the API connection and try again.</div>;
  }
  
  // The rest of your component remains largely the same...
  // It now uses the 'user' prop for all point calculations.
  if (selectedReward) {
    const remainingPoints = user.points - selectedReward.pointsRequired;
    return (
      <div className="redeem-page">
        <h2>Confirm Your Redemption</h2>
        <div className="redeem-container">
          <div className="redeem-summary">
            <h3>{selectedReward.rewardType}</h3>
            <p>{selectedReward.name}</p>
            <div className="redeem-points-cost">{selectedReward.pointsRequired.toLocaleString()} pts</div>
          </div>
          <div className="points-calculation">
            <div className="balance-row">
              <span>Your Current Balance:</span>
              <span>{user.points.toLocaleString()} pts</span>
            </div>
            <div className="balance-row cost">
              <span>Cost:</span>
              <span>-{selectedReward.pointsRequired.toLocaleString()} pts</span>
            </div>
            <div className="balance-row total">
              <span>Remaining Balance:</span>
              <span>{remainingPoints.toLocaleString()} pts</span>
            </div>
          </div>
          <button className="btn confirm-btn" onClick={handleConfirmRedemption} disabled={isRedeeming}>
            {isRedeeming ? 'Processing...' : 'Confirm Redemption'}
          </button>
          <button className="btn back-btn" onClick={handleGoBack}>Back to Catalog</button>
        </div>
      </div>
    );
  }

  return (
    <div className="redeem-page">
      <h2>Redeem Your Points</h2>
      <p className="intro-text">Use your points balance of <strong>{user.points.toLocaleString()} pts</strong> to claim a reward.</p>
      <div className="rewards-grid">
        {catalog.map(reward => (
          <div key={reward.catalogItemId} className="reward-card">
            <h3>{reward.rewardType}</h3>
            <p>{reward.name}</p>
            <div className="reward-points">{reward.pointsRequired.toLocaleString()} pts</div>
            <button className="btn" onClick={() => handleSelectReward(reward)}>Redeem</button>
          </div>
        ))}
      </div>
    </div>
  );
}

export default RedeemPage;
