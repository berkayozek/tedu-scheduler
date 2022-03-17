import React, {useEffect, useState} from "react";
import PropTypes from "prop-types";
import "./Timetable.scss";
import colors from "../../data/colors.json"
import {days, daysToInt, startTime} from "../../data/constant";
import Cell from "../Cell/Cell";

let colorIndex = 0

const Timetable = ({
    advancedFilter,
    updateAdvancedFilter,
    timetableData
    }) => {

    const [matrix, setMatrix] = useState(Array.from({length: 13},()=> Array.from({length: 5}, () => null)));

    const updateMatrix = ( ) => {
        const {timetable, timetableIndex} = timetableData
        let copy = Array.from({length: 13},()=> Array.from({length: 5}, () => null));
        colorIndex = 0

        if (timetable.length > 0 && timetable[timetableIndex]) {
            timetable[timetableIndex].sections.forEach((section) => {
                let incrementColor = false

                section.courseHours.forEach((courseHour) => {
                    if (courseHour.time !== 0) {
                        let startHour = courseHour.startHour - startTime
                        let endHour = courseHour.endHour - startTime
                        console.log(advancedFilter.showRoom)
                        let roomName = section.room.map(x => x.roomName).toString()

                        for (let i=startHour;i>= 0 && i <= endHour; i++) {
                            if (copy[i][daysToInt[courseHour.day]] == null) {
                                copy[i][daysToInt[courseHour.day]] = {
                                    "code": section.sectionCode,
                                    "roomName" : roomName,
                                    "color": colors[colorIndex]
                                }
                                incrementColor = true
                            } else {
                                copy[i][daysToInt[courseHour.day]] = {
                                    "code": copy[i][daysToInt[courseHour.day]].code + ", " + section.sectionCode,
                                    "room" : copy[i][daysToInt[courseHour.day]].code + ", " + roomName,
                                    "color": "accent-red-gradient"
                                }
                            }
                        }
                    }
                })

                if (incrementColor){
                    colorIndex++
                }
            })
        }
        setMatrix(copy)
    }

    const UpdateLockMatrix = (rowIndex, colIndex) => {
        let copy = advancedFilter.isLock
        copy[rowIndex][colIndex] = !copy[rowIndex][colIndex]

        updateAdvancedFilter("isLock", copy)
    }

    useEffect(() => {
            updateMatrix()
    },[timetableData.timetable, timetableData.timetableIndex])

    return (
        <div className={"timetable"}>
            <div className={"week-names"}>
                {days.map((day) => <div key={day} className={"timetable-day"}> {day} </div> )}
            </div>
            <div className={"time-interval"}>
                {matrix.map((row, rowIndex) =>
                    <div key={"time" + rowIndex} className={"timetable-time"}>
                        <div className={"timetable-time-text"}>
                            {(startTime+rowIndex) + ":00"}
                        </div>
                    </div>
                )}
            </div>
            <div className={"content"}>
                {matrix.map((row, rowIndex) =>
                    row.map((column, columnIndex) => (
                        <Cell key={(1 + rowIndex) * (1 + columnIndex)} keyCode={"cell" + columnIndex + " " + rowIndex}
                              data={matrix[rowIndex][columnIndex]} isLock={advancedFilter.isLock[rowIndex][columnIndex]}
                              updateFunc={() => UpdateLockMatrix(rowIndex, columnIndex)}
                              showRoom={advancedFilter.showRoom}
                        />
                    ))
                )}
            </div>
        </div>
    )
}

Timetable.propTypes = {
    selectedCourses: PropTypes.array,
    coursesJSON: PropTypes.any,
    possibleTimetables: PropTypes.array,
    timetableIndex: PropTypes.number,
}

export default (Timetable)