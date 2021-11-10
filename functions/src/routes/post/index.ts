import * as express from "express"
import { getPost, createPost, modifyPost, deletePost } from "../../controllers/post.controller"

// eslint-disable-next-line new-cap
export const postRouter = express.Router()

postRouter.get("/get", getPost)
postRouter.post("/create", createPost)
postRouter.post("/modify", modifyPost)
postRouter.post("/delete", deletePost)
