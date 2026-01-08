import { useState } from "react";
import { Link } from "react-router-dom";
import "../styles/Sidebar.css";

const Sidebar = () => {
  const role = localStorage.getItem("role"); // Role-based links
  const [isOpen, setIsOpen] = useState(true); // For mobile toggle

  return (
    <div className={`sidebar ${isOpen ? "open" : "closed"}`}>
      <button className="toggle-btn" onClick={() => setIsOpen(!isOpen)}>
        ☰
      </button>
      <h2 className="logo">TaskManager</h2>
      <nav>
        <ul>
          <li><Link to="/">Home</Link></li>
          {role === "ADMIN" && <li><Link to="/admin">Admin Panel</Link></li>}
          <li><Link to="/profile">Profile</Link></li>
        </ul>
      </nav>
    </div>
  );
};

export default Sidebar;
