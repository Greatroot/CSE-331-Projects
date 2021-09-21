import React from "react"
import { Link } from 'react-router-dom'
import {FaInstagram, FaLinkedin} from "react-icons/fa";

function OldFooter() {
    return (

        <div className="footer">
            <section className="footer-links">
                <div className="footer-link-items">
                    <h2>Rate My Internships</h2>
                    <Link to="/about-us">About Us</Link>
                    <Link to="/terms">Terms of Use</Link>
                    <Link to="private-policy">Private Policy</Link>
                </div>
            </section>
            <section className="footer-contacts">
                <div className="footer-contacts-items">
                    <h2>Contact Us</h2>
                    <p>ratemyinternshipoffical@gmail.com</p>
                    <div className="social-icons">
                        <Link className='social-icon-link instagram'
                              to='/instagram'
                              target='blank'
                              aria-label='Instagram'
                        >
                            <FaInstagram />
                        </Link>
                        <Link className='social-icon-link linkedin'
                              to='/linked-in'
                              target='blank'
                              aria-label='LinkedIn'
                        >
                            <FaLinkedin />
                        </Link>
                    </div>
                </div>
            </section>
        </div>

        // Old footer
    // <div>
    //     <footer className="main-footer">
    //         <div className="content">
    //         <div className="linkedin"> <a href = "#"> < i class="fa fa-linkedin"> </i> </a>  </div>
    //         <div className="insta"> <a href = "#"> < i class="fa fa-instagram"></i> </a>  </div>
    //         <br />
    //         <div className="about-us"><a href = "#"> About Us </a> </div>
    //         <div class= "contact-us"><a href = "#"> Contact Us </a> </div>
    //         </div>
    //     </footer>
    // </div>
    )
}
export default OldFooter;