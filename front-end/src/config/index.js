const dev = {
    BACKEND_URL : "http://localhost:8081/api"
}

const prod = {
    BACKEND_URL: "https://teduscheduler.tk/api"
}

const config = process.env.REACT_APP_PROD
    ? prod
    : dev;

export default {
    ...config
}