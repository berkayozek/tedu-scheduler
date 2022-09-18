import React from "react";
import "./Footer.scss"
import { GitHub, Email, CameraAlt, HelpRounded } from "@material-ui/icons";
import {ReactComponent as RightArrow} from "../../images/right-arrow.svg"
import {ReactComponent as LeftArrow} from "../../images/left-arrow.svg"
import html2canvas from "html2canvas";
import {IconButton} from "@material-ui/core";

const Footer = ({ timetableData, updateTimetableData, setIsTutorialOpen }) => {
    const captureElement = document.querySelector('#capture')


    const decreaseTimetableIndex = () => {
        const {timetable, timetableIndex} = timetableData
        if (timetableIndex - 1 >= 0) {
            updateTimetableData("timetableIndex", timetableIndex - 1)
        } else {
            updateTimetableData("timetableIndex", timetable.length - 1)
        }
    }

    const increaseTimetableIndex =  () => {
        const {timetable, timetableIndex} = timetableData

        updateTimetableData("timetableIndex", (timetableIndex + 1) % timetable.length)
    }

    const takeScreenshot = async () => {
        const timetable = document.querySelector('.timetable').cloneNode(true)
        captureElement.appendChild(timetable)

        const canvas = await html2canvas(timetable, {})
        const link = document.createElement("a")
        const date = new Date().toLocaleString()
        link.href = canvas.toDataURL()
        link.download = `Schedule - ${date}.png`
        link.click()

        captureElement.querySelectorAll('*').forEach( n => n.remove() );
    }


    return(<div className={"footer"}>
        <div className={"left-footer"}>
            <div className={"github"}>
                <IconButton className={"mui-button"}>
                    <a href={"https://github.com/emreoyun"}>
                        <GitHub />
                    </a>
                </IconButton>
            </div>
            <div className={"mail"}>
                <IconButton className={"mui-button"}>
                    <a href={"mailto:berkayozek@hotmail.com"}>
                        <Email />
                    </a>
                </IconButton>
            </div>
            <div className={"tutorial"}>
                <IconButton className={"mui-button"} onClick={() => setIsTutorialOpen(false)}>
                    <HelpRounded/>
                </IconButton>
            </div>
        </div>
        <div className={"middle-footer"}>
            <div className={"pagination"}>
                <IconButton className={"mui-button"} onClick={decreaseTimetableIndex}>
                    <LeftArrow className={"left-icon"}/>
                </IconButton>
                <div className={"numbers"}>
                    <div className={"current-index"}>
                        { timetableData.timetable.length !== 0 ? timetableData.timetableIndex + 1 : 0}
                    </div>
                    <div className={"slash"}>
                        /
                    </div>
                    <div className={"total-index"}>
                        { timetableData.timetable.length }
                    </div>
                </div>
                <IconButton className={"mui-button"} onClick={increaseTimetableIndex}>
                    <RightArrow className={"right-icon"}/>
                </IconButton>
            </div>
            <div className={"text"}>
                Combinations
            </div>
            <div className={"text"}>
                This website is not affiliated with TEDU in any way.<br />It is a project by students for students.
            </div>

        </div>
        <div className={"right-footer"}>
            <div className={"camera"}>
                <IconButton className={"mui-button"} onClick={takeScreenshot}>
                    <CameraAlt />
                </IconButton>
            </div>
        </div>
    </div>)
}

export default Footer