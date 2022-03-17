import api from "./index";

export const getCoursesBySemester = (semester) => {
    return api.get("/course/get/" + semester.year + "-" + semester.code)
}