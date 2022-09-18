const dev = {
    BACKEND_URL : "http://localhost:8124/api"
}

const prod = {
    BACKEND_URL: "https://t.emreoyun.tk:8124/api"
}

const config = process.env.REACT_APP_PROD
    ? prod
    : dev;

export default {
    ...config
}