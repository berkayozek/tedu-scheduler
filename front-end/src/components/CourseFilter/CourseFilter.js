import React, {memo, useEffect, useState} from "react";
import "./CourseFilter.scss";
import {AccordionDetails, AccordionSummary, Switch } from "@material-ui/core";
import AdvancedCourseFilter from "../AdvancedCourseFilter/AdvancedCourseFilter";
import Accordion
    from "@material-ui/core/Accordion";
import {daysToInt, startTime} from "../../data/constant";

const CourseFilter = ({
                          advancedFilter,
                          updateAdvancedFilter,
                          timetableData,
                          updateTimetableData
                      }) => {
    const [expanded, setExpanded] = useState('')
    const [filteredCourses, setFilteredCourses] = useState({})

    const updateFilteredCourses = (filteredObj) => {
        const {courseName, filterType, key, value} = filteredObj

        setFilteredCourses(prevState => ({
            ...prevState,
            [courseName]: {
                ...prevState[courseName],
                [filterType]: {
                    ...prevState[courseName][filterType],
                    [key]: value
                }
            }
        }));
    }

    const addFilteredCourse = () => {
        const newFilteredCourses = {}

        timetableData.selectedCoursesData.forEach((courseObj) => {
            const courseCode = courseObj.courseCode
            const courseName = courseObj.courseName
            const instructors = {}
            const sections = {}

            if (filteredCourses[courseCode]) {
                newFilteredCourses[courseCode] = filteredCourses[courseCode]
            } else {
                courseObj.sections.forEach((section, index) => {
                    sections[section.sectionCode] = true
                    section.instructors.forEach((instructor) => {
                        if (!instructors[instructor.name]) {
                            instructors[instructor.name] = true
                        }
                    })
                })

                let filteredObj = {
                    "courseName": courseName,
                    "courseCode": courseCode,
                    "sections" : sections,
                    "instructors": instructors,
                    "sectionsObj": courseObj.sections
                }

                newFilteredCourses[courseCode] = filteredObj
            }
        })

        setFilteredCourses(newFilteredCourses)
    }

    const changeFilterValue = (courseName, filterType, key, value) => {
        const obj = {
            "courseName": courseName,
            "filterType": filterType,
            "key": key,
            "value": value
        }

        updateFilteredCourses(obj)
    }

    const updateTimetable = () => {
        const newTimetableList = []

        timetableData.originalTimetable.forEach((timetable) => {
            let newTimetable = JSON.parse(JSON.stringify(timetable))
            let hasSection = false

            timetable.sections.forEach((section, index) => {
                const courseCode = section.sectionCode.split("_")[0]

                // Filter for the sections
                if (filteredCourses[courseCode] && !filteredCourses[courseCode].sections[section.sectionCode])
                    hasSection = true

                // Filter for the instructors
                section.instructors.forEach(instructor =>{
                    if (!filteredCourses[courseCode].instructors[instructor.name])
                        hasSection = true
                })
            })

            if (newTimetable.sections.length > 0 && !hasSection)
                newTimetableList.push(newTimetable)
        })

        handleLockedHour(newTimetableList)
    }

    const handleLockedHour = (timetableList) => {
        const newTimetableList = []
        const lockedHours = {}

        advancedFilter.isLock.forEach((row, rowIndex) => {
            row.forEach((col, colIndex) => {
                if (col) {
                    const day = Object.entries(daysToInt).find(([key, value]) => value == colIndex)[0]
                    const hour = startTime + rowIndex

                    lockedHours[day + hour] = true
                }
            })
        })

        timetableList.forEach((timetable) => {
            let newTimetable = JSON.parse(JSON.stringify(timetable))
            let isLocked = false

            newTimetable.sections.forEach((section, index) => {

                section.courseHours.forEach(courseHour => {
                    for (let i=parseInt(courseHour.startHour); i <= parseInt(courseHour.endHour); i++) {
                        if (lockedHours[courseHour.day + i])
                            isLocked = true
                    }
                })
            })

            if (newTimetable.sections.length > 0 && !isLocked)
                newTimetableList.push(newTimetable)
        })

        updateTimetableData("timetable", newTimetableList)
    }

    const getCurrentCourse = (courseObj) => {
        const {timetable, timetableIndex} = timetableData
        const courseFullName = courseObj.courseName
        const courseCode = courseObj.courseCode
        let instructorName = ""
        let currentCourseObj = null

        if (timetable.length > 0 && timetable[timetableIndex]) {
            currentCourseObj = timetable[timetableIndex].sections.find((section) => section.sectionCode.includes(courseObj.courseCode))

            if (currentCourseObj){
                currentCourseObj.instructors.forEach(instructor => instructorName += instructor.name + " ")
            }
        }

        return <>
            <div className={"course-header-code" + (currentCourseObj && timetable[timetableIndex].conflictSections.find(section => section.sectionCode === currentCourseObj.sectionCode) ? " conflict": "")}>
                { currentCourseObj ? currentCourseObj.sectionCode : courseCode + "_XX" }
            </div>
            <div className={"course-detail"}>
                <div className={"course-detail-name"}>
                    { courseFullName }
                </div>
                <div className={"course-detail-lecturer"}>
                    { currentCourseObj ? instructorName : "Lecturer" }
                </div>
            </div>
        </>
    }

    const isLecturerDisabled = (courseValue, lecturerName) => {
        let isDisabled = true

        courseValue.sectionsObj.forEach((sections) => {
            if (sections.instructors.some(instructor => instructor.name == lecturerName)
                && courseValue.sections[sections.sectionCode])
                isDisabled = false
        })

        return isDisabled
    }

    const isSectionDisabled = (courseValue, sectionIndex) => {
        let isDisabled = false

        courseValue.sectionsObj[sectionIndex].instructors.forEach((instructor) => {
            if (!courseValue.instructors[instructor.name])
                isDisabled = true
        })

        return isDisabled
    }

    const handleChange = (panel) => (event, isExpanded) => {
        setExpanded(isExpanded ? panel : false);
    }

    useEffect(() => {
        addFilteredCourse()
    }, [timetableData.selectedCoursesData])

    useEffect(() => {
        updateTimetable()
    }, [filteredCourses, JSON.stringify(advancedFilter.isLock)])

    return (
        <div className={"course-filter"}>
            <div className={"header"}>
                <div className={"header-text"}>
                    Course Filter
                </div>
                <AdvancedCourseFilter {...{advancedFilter, updateAdvancedFilter}}/>
            </div>
            <div className={"course-filter-list"}>
                {Object.keys(filteredCourses).length === 0 &&
                <div className={"course-not-selected"}>
                    No course selected yet
                </div>}
                {Object.entries(filteredCourses).map(([courseKey, courseValue]) =>
                    <Accordion key={courseKey} className={"course"} expanded={expanded === courseKey} onChange={handleChange(courseKey)}>
                        <AccordionSummary className={"course-header"} aria-controls={courseKey + "-content"} id={courseKey + "-content"}>
                            { getCurrentCourse(courseValue) }
                        </AccordionSummary>
                        <AccordionDetails className={"course-content"}>
                            <div className={"section-list"}>
                                {Object.entries(courseValue.sections).map(([key,value], index) =>
                                    <div key={key} className={"section"}>
                                        {"Section " + ( parseInt(index) + 1 )}
                                        <Switch
                                            checked={value}
                                            disabled={isSectionDisabled(courseValue, index)}
                                            color="primary"
                                            size={"small"}
                                            onChange={(e) => changeFilterValue(courseValue.courseCode, "sections", key, e.target.checked)}
                                        />
                                    </div>
                                )}
                            </div>
                            <div className={"lecturer-list"}>
                                {Object.entries(courseValue.instructors).map(([key,value]) =>
                                    <div key={key} className={"lecturer"}>
                                        { key }
                                        <Switch
                                            checked={value}
                                            disabled={isLecturerDisabled(courseValue, key)}
                                            color="primary"
                                            size={"small"}
                                            onChange={(e) => changeFilterValue(courseValue.courseCode, "instructors", key, e.target.checked)}
                                        />
                                    </div>
                                )}
                            </div>
                        </AccordionDetails>
                    </Accordion>)}
            </div>
        </div>
    )
}

export default CourseFilter