import * as express from "express"
import { createUser } from "../../controllers/auth.controller"

// eslint-disable-next-line new-cap
export const authRouter = express.Router()

authRouter.use("/create", createUser)
