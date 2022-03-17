const init = () => [
    {
        intro: "Welcome to TEDU Scheduler, lets show you around.",
    },
    {
        element : document.querySelector(".semester-selector"),
        intro : "First, select your semester from here.",
        position: "right",
    },
    {
        element : document.querySelector(".course-selector"),
        intro : "After selecting your semester you can view and select your courses.",
        position: "right",
    },
    {
        element : document.querySelector(".course-filter"),
        intro : "See your current courses' sections from here and filter them by clicking the course name.",
        position: "right",
    },
    {
        element: document.querySelector(".advanced-filter"),
        intro: "Change your scheduler's options such as allow conflict and show only one/two day empty."
    },
    {
        element: document.querySelector(".timetable"),
        intro: "See your possible timetables from here."
    },
    {
        element: document.querySelector(".timetable").querySelectorAll(".cell ")[36],
        intro: "This red cells are conflict courses. " +
            "<b><i>If you want to see conflict courses you need to change options from Advanced Course Filter!</i></b>"
    },
    {
        element: document.querySelector(".timetable").querySelectorAll(".cell ")[25],
        intro: "Lock timeslot by clicking the cells."
    },
    {
        element: document.querySelector(".pagination"),
        intro: "Switch between possible timetables."
    },
    {
        element: document.querySelector(".camera"),
        intro: "Download your timetables clicking this button."
    }
];

export default init;