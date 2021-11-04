import * as express from "express"
import { createUser } from "../controllers/auth.controller"

// eslint-disable-next-line new-cap
const router = express.Router()
router.use("/", createUser)

export { router }
