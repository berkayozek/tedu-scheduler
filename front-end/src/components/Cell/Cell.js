import React from "react";
import { Lock, LockOpen } from '@material-ui/icons/';

const Cell = ({ keyCode, data, isLock, updateFunc, showRoom}) => {
    let color = data ? data.color : ""
    let courseCode = data ? data.code : ""
    let roomName = data ? data.roomName : ""

    return (
        <div className={"cell " + color} key={ keyCode } >
            {courseCode}
            {roomName != "" && showRoom ? (
                <div className={"room"}>
                    {roomName}
                </div>
            ) : ("")}
            {isLock ? (
                <div key={data} className={"locked"} onClick={updateFunc}>
                    <Lock />
                </div>
            ) : (
                <div key={data} className={"lock" + (courseCode ? " lock_course": "")} onClick={updateFunc}>
                    <LockOpen />
                </div>
            )
            }
        </div>
    )
}

export default (Cell)