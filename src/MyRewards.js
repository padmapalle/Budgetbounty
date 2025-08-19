// import React from "react";

// function MyRewards() {
// 	const myRewards = [
// 		{ id: 1, name: "Amazon Gift Card", status: "Redeemed" },
// 		{ id: 2, name: "Coffee Mug", status: "Available" },
// 	];

// 	return (
// 		<div>
// 			<h2>My Rewards</h2>
// 			<ul>
// 				{myRewards.map((r) => (
// 					<li key={r.id}>
// 						{r.name} - {r.status}
// 					</li>
// 				))}
// 			</ul>
// 		</div>
// 	);
// }

// export default MyRewards;
// src/MyRewards.js
import React, { useEffect, useState } from "react";
import "./MyRewards.css";
import { getRewards } from "./lib/api";

export default function MyRewards() {
  const userId = Number(process.env.REACT_APP_DEFAULT_USER_ID || 2);
  const [items, setItems] = useState([]);

  useEffect(() => {
    getRewards(userId).then(setItems).catch(console.error);
  }, [userId]);

  if (!items.length) return <div className="empty-state">No rewards yet.</div>;

  return (
    <div className="rewards-grid">
      {items.map(r => (
        <div key={r.rewardId} className="reward-card">
          <div className="reward-title">+{r.points} pts</div>
          <div className="reward-meta">
            {r.catalogItemName ?? "Reward"} • {new Date(r.earnedAt).toLocaleString()}
          </div>
        </div>
      ))}
    </div>
  );
}
