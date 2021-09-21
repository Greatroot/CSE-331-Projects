import React from 'react';
import './Header.css';
import { Link } from 'react-router-dom';

const Header = ({ bottomBorder }) => {

    return (
        <>
            <nav className={`navbar ${bottomBorder ? "bottom-border" : ""}`}>
                <div className="navbar-container container">
                    <div className="navbar-logo">
                        CampusPaths
                    </div>
                </div>
            </nav>
        </>
    );
}

export default Header;