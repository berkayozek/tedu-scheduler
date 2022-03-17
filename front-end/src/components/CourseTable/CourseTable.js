import React from "react";
import "./CourseTable.scss";
import CourseSelectionTab from "../CourseSelectionTab/CourseSelectionTab";
import CourseFilter from "../CourseFilter/CourseFilter";

const CourseTable = ({
                         advancedFilter,
                         updateAdvancedFilter,
                         setIsLoading,
                         timetableData,
                         updateTimetableData,
                         isTutorialOpen,
                         setIsTutorialOpen
                     }) => {

    return (
        <div className={"left-table"}>
            <CourseSelectionTab
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
            <CourseFilter
                {...{
                    advancedFilter,
                    updateAdvancedFilter,
                    timetableData,
                    updateTimetableData
                }}
            />
        </div>
    )
}

export default CourseTable