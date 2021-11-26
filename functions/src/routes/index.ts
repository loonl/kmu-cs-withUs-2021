import * as express from "express"
import { authRouter } from "./auth"
import { postRouter } from "./post"
import { commentRouter } from "./comment"

// eslint-disable-next-line new-cap
export const router = express.Router()

router.use("/user", authRouter)
router.use("/post", postRouter)
router.use("/comment", commentRouter)
