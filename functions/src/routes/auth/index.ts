import * as express from "express"
import { getUser, createUser, modifyUser, deleteUser } from "../../controllers/auth.controller"

// eslint-disable-next-line new-cap
export const authRouter = express.Router()

authRouter.get("/get", getUser)
authRouter.post("/create", createUser)
authRouter.post("/modify", modifyUser)
authRouter.get("/delete", deleteUser)
