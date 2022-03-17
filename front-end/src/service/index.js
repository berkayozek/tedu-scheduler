import axios from "axios"
import * as config from "../config"
import * as TimetableService from "./TimetableService"
import * as SemesterService from "./SemesterService"
import * as CourseService from "./CourseService"

const api = axios.create({
    baseURL: config.default.BACKEND_URL,
    headers: {
        'Access-Control-Allow-Origin': '*',
        'Content-Type': 'application/json',
    }
})

export default api

export { TimetableService, SemesterService, CourseService }