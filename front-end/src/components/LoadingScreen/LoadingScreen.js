import React from "react";
import "./LoadingScreen.scss"

const LoadingScreen = ({ isLoading }) => {


    return (
        <div className={"loading-screen" + (isLoading ? " open" : "")}>
            <div
                className="boxes">
                <div
                    className="box">
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                </div>
                <div
                    className="box">
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                </div>
                <div
                    className="box">
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                </div>
                <div
                    className="box">
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                </div>
            </div>
            <div className={"text"}>
                Loading...<br />If it takes longer than 10 seconds, server might be down.
            </div>
        </div>
    )

}

export default LoadingScreen