import api from "./index"
const {BACKEND_URL} = process.env;

export const GenerateTimetable = (selectedCourses, semester, filter) => {

    const obj = {
        "courseCodes" : selectedCourses,
        "semester" : semester,
        "timetableFilter" : filter
    }

    return api.post("/timetable/generate", obj)
}