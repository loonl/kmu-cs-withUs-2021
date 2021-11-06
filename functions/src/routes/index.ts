import * as express from "express"
import { authRouter } from "./auth"

// eslint-disable-next-line new-cap
export const router = express.Router()

router.use("/user", authRouter)
