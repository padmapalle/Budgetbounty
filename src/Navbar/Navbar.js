import React from 'react';
import { Link, NavLink } from 'react-router-dom'; // <-- Added NavLink import
import './Navbar.css';

function Navbar({ isLoggedIn, onLogout }) {
  return (
        <nav className="navbar">
            <h2 className="nav-title">Rewards System</h2>
            <ul className="nav-links">
                {isLoggedIn && (
                    <>
                        <li>
                            <Link to="/dashboard">Dashboard</Link>
                        </li>
                        <li>
                            <button className="logout-btn" onClick={onLogout}>
                                Logout
                            </button>
                        </li>
                        <li>
                            <NavLink to="/user/profile">Profile</NavLink>
                        </li>
                    </>
                )}
            </ul>
        </nav>
  );
}

export default Navbar;
