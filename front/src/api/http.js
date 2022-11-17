import axios from "axios";
export default axios.create({
  baseURL: "http://localhost:8080/happyhouse/board",
  headers: {
    "Content-Type": "application/json",
  },
});
