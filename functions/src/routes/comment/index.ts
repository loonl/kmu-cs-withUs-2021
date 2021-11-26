import * as express from "express"
import { getComment, createComment, deleteComment } from "../../controllers/comment.controller"

// eslint-disable-next-line new-cap
export const commentRouter = express.Router()

commentRouter.get("/get", getComment)
commentRouter.post("/create", createComment)
commentRouter.get("/delete", deleteComment)
