import api from "./index";

export const getAllSemesters = () => {
    return api.get("/semester/getall")
}