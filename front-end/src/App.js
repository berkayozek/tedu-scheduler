import './App.scss';
import CourseTable from "./components/CourseTable/CourseTable";
import Timetable from "./components/Timetable/Timetable";
import React, {useEffect, useState} from "react";
import Footer from "./components/Footer/Footer";
import LoadingScreen from "./components/LoadingScreen/LoadingScreen";
import {useLocalStorage} from "react-use";

const App = () => {
    const [timetableData, setTimetableData] = useState({"timetable": [], "timetableIndex": 0, "originalTimetable": [], "selectedCoursesData": []})
    const [advancedFilter, setAdvancedFilter] = useState({"allowConflict": false, "conflictCourses": [], "emptyDay": false,
        "emptyDayCount" : 1, "showRoom" : true, "isLock": Array.from({length: 13},()=> Array.from({length: 7}, () => false))})
    const [isLoading, setIsLoading] = useState(true)
    const [isTutorialOpen, setIsTutorialOpen] = useLocalStorage( "isTutorialCompleted",false)
    const [cachedFilters, setCachedFilters] = useLocalStorage("advancedFilter", {})

    const updateAdvancedFilter = (filterKey, filterValue) => {
        setAdvancedFilter(prevState => ({
            ...prevState,
            [filterKey]: filterValue
        }))
    }

    const updateTimetableData = (filterKey, filterValue) => {
        setTimetableData(prevState => ({
            ...prevState,
            [filterKey]: filterValue
        }))
    }

    const handleTimetableIndex = () => {
        const {timetable, timetableIndex} = timetableData

        if (timetable.length == 0) {
            updateTimetableData("timetableIndex", 0)
        } else if (timetable.length - 1 < timetableIndex){
            updateTimetableData("timetableIndex", timetable.length - 1)
        }
    }

    useEffect(() => {
        if (cachedFilters !== {}) {
            setAdvancedFilter(cachedFilters)
            updateAdvancedFilter("isLock", Array.from({length: 13},()=> Array.from({length: 7}, () => false)))
        }
    }, [])

    useEffect(() => {
        setCachedFilters(advancedFilter)
    }, [advancedFilter])

    useEffect(() => {
        handleTimetableIndex()
    }, [timetableData.timetable])


    return (
        <>
            <div className={"container"}>
                <CourseTable
                    {...{
                        advancedFilter,
                        updateAdvancedFilter,
                        setIsLoading,
                        timetableData,
                        updateTimetableData,
                        isTutorialOpen,
                        setIsTutorialOpen
                    }}
                />
                <Timetable
                    {...{
                        advancedFilter,
                        updateAdvancedFilter,
                        timetableData,
                        updateTimetableData
                    }}
                />
                <Footer {...{
                    timetableData,
                    updateTimetableData,
                    setIsTutorialOpen
                }}/>
            </div>
            <div id={"capture"}>

            </div>
            <LoadingScreen {...{
                isLoading,
                setIsLoading
            }}/>
        </>
    )
}

export default App;
