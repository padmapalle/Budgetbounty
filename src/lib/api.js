// src/lib/api.js
import axios from "axios";

const API = axios.create({
  baseURL: process.env.REACT_APP_API_BASE || "http://localhost:8081",
});

// Users
export const getUser = (id) =>
  API.get(`/api/users/${id}`).then(r => r.data);

// Activities
export const getActivities = (userId) =>
  // supports either /api/activities?userId= or /api/activities (no filter)
  API.get(`/api/activities${userId ? `?userId=${userId}` : ""}`).then(r => r.data);

// Goals
export const getGoals = (userId) =>
  // try by-user endpoint first; fall back to all+filter
  API.get(`/api/goals/user/${userId}`)
    .then(r => r.data)
    .catch(() => API.get(`/api/goals`).then(r => r.data.filter(g => g.userId === Number(userId))));

export const createGoal = (payload) =>
  API.post("/api/goals", payload).then(r => r.data);

export const markGoalAsAchieved = (goalId) =>
  API.put(`/api/goals/${goalId}/achieve`).then(r => r.data);

// Rewards & Catalog
export const getRewards = (userId) =>
  API.get(`/api/rewards/user/${userId}`)
    .then(r => r.data)
    .catch(() => API.get(`/api/rewards`).then(r => r.data.filter(x => x.userId === Number(userId))));

export const getCatalog = () =>
  // This line is now fixed to use the correct endpoint
  API.get(`/api/reward-catalog`).then(r => r.data);

// Redemptions
export const redeemItem = ({ userId, catalogItemId }) =>
  API.post(`/api/redemptions`, { userId, catalogItemId }).then(r => r.data);
