import React from "react"
import "./Footer.css";

// TODO: Make left-footer--items all working links to those pages once we make them.
function Footer() {
    return (
        <div>
            <div className="main-footer"> {/* ***************** main-footer **************** */}
                <div className="left-footer">
                    <div className="left-footer--header">
                        <h2>CampusPaths</h2>
                    </div>
                </div>
                <div className="bottom-footer"> {/* ************** bottom-footer **************** */}
                    <p>© 2021 ratemyinternships, All Rights Reserved</p>
                </div>
            </div>
        </div>
    )
}
                export default Footer;