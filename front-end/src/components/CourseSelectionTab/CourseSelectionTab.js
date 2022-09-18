import Autocomplete from "@material-ui/lab/Autocomplete";
import TextField from "@material-ui/core/TextField";
import React, { useEffect, useRef, useState } from "react"
import "./CourseSelectionTab.scss";
import {CourseService, SemesterService, TimetableService} from "../../service"
import cogoToast from 'cogo-toast';
import Tutorial from "../Tutorial";

const CourseSelectionTab = ({
                                advancedFilter,
                                updateAdvancedFilter,
                                setIsLoading,
                                timetableData,
                                updateTimetableData,
                                isTutorialOpen,
                                setIsTutorialOpen
                            }) => {
    const [selectedCourses, setSelectedCourses] = useState([])
    const [coursesJSON, setCoursesJSON] = useState([])
    const [semesters, setSemesters] = useState([])
    const [selectedSemester, setSelectedSemester] = useState("")

    const tempSchedulerData = useRef()
    const tutorialTempCourseData = []

    const setSemester = (semester) => {
        if (semester) {
            setIsLoading(true)
            setSelectedSemester(semester)
        }
    }

    const addCourse = (course) => {
        if (course.length <= 9) {
            setSelectedCourses(course)
        }
    }

    const findPossibleTimetables = (course) => {
        if (course.length === 0)
            return

        TimetableService.GenerateTimetable(course, selectedSemester, advancedFilter).then((res) => {
            setTimetableData(res.data.timetables, res.data.selectedCourses)
        }).catch((error) => {

            if (error.status === "400"){
                cogoToast.error(error.response.data);
            } else {
                cogoToast.error('You are too fast, retry again in 5 seconds.');
            }
        })
    }

    const setTimetableData = (timetable, selectedCoursesData) => {
        updateTimetableData("originalTimetable", timetable)
        updateTimetableData("timetable", timetable)
        updateTimetableData("selectedCoursesData", selectedCoursesData)
    }

    useEffect(() => {
        setIsLoading(true)
        SemesterService.getAllSemesters().then((res) => {
            setSemesters(res.data)

            if (isTutorialOpen) {
                setSemester(res.data[0])
            } else {
                handleSchedulerDataForTutorial(res.data);
            }
        })
    }, [])

    const closeTutorial = () => {
        setIsTutorialOpen(true)
        setSemester(tempSchedulerData.current.selectedSemester)
        updateAdvancedFilter("allowConflict", tempSchedulerData.current.allowConflict)
        updateAdvancedFilter("isLock", tempSchedulerData.current.isLock)
        setSelectedCourses(tempSchedulerData.current.selectedCourses)
    }

    const handleSchedulerDataForTutorial = (semesters) => {
        tempSchedulerData.current = {
            allowConflict: advancedFilter.allowConflict,
            selectedSemester : selectedSemester != "" ? selectedSemester : semesters[0],
            selectedCourses: selectedCourses,
            isLock : advancedFilter.isLock
        }

        let copy = Array.from({length: 13},()=> Array.from({length: 7}, () => false))
        copy[5][0] = !copy[5][0]
        updateAdvancedFilter("isLock", copy)
        updateAdvancedFilter("allowConflict", true)
        const tempSemester = semesters.find(semester => semester.semesterName === "Fall 2013-2014")
        setSemester(tempSemester)
    }

    const openTutorial = (coursesJSON) => {
        Tutorial(closeTutorial)
        tutorialTempCourseData.push("ART 110")
        tutorialTempCourseData.push("CMPE 211")
        addCourse(tutorialTempCourseData)
    }

    useEffect(() => {
        setSelectedCourses([])
        setTimetableData([], [])
        if (selectedSemester)
            CourseService.getCoursesBySemester(selectedSemester).then((res) => {
                setCoursesJSON(res.data)
                setIsLoading(false)
                if (!isTutorialOpen){
                    openTutorial(res.data)
                }
            })
    }, [selectedSemester]);

    useEffect(() => {
        findPossibleTimetables(selectedCourses)
    }, [advancedFilter.allowConflict, advancedFilter.emptyDay, advancedFilter.emptyDayCount])

    useEffect(  () => {
        if (!isTutorialOpen && coursesJSON && coursesJSON.length !== 0){
            handleSchedulerDataForTutorial(semesters);
        }
    }, [isTutorialOpen])

    useEffect(() => {
        if (selectedCourses && selectedCourses.length != 0 && selectedSemester){
            findPossibleTimetables(selectedCourses)
        } else {
            setTimetableData([], [])
        }
    }, [selectedCourses])

    return(
        <div className={"courses"}>
            <Autocomplete
                className={"semester-selector"}
                options={semesters}
                getOptionLabel={option => option.semesterName || ""}
                value={selectedSemester}
                onChange={(e, selectedValue) => setSemester(selectedValue)}
                renderInput={(params) =>
                    <TextField {...params} label="Semesters" variant="outlined" />}
            />
            <Autocomplete
                className={"course-selector"}
                multiple
                filterSelectedOptions
                value={selectedCourses}
                options={coursesJSON != null ? coursesJSON : []}
                groupBy={(option) => option.split(" ")[0]}
                disabled={selectedSemester == null}
                onChange={(e, selectedValue) => addCourse(selectedValue)}
                renderInput={(params) =>
                    <TextField {...params} label="Courses" variant="outlined" />}
            />
        </div>
    )
}

export default CourseSelectionTab
