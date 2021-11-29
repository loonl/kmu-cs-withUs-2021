import * as express from "express"
import {
  getPost, getLatestPost, getPostDetail, createPost, modifyPost, deletePost,
} from "../../controllers/post.controller"

// eslint-disable-next-line new-cap
export const postRouter = express.Router()

postRouter.get("/get", getPost)
postRouter.get("/latest", getLatestPost)
postRouter.get("/detail", getPostDetail)
postRouter.post("/create", createPost)
postRouter.post("/modify", modifyPost)
postRouter.get("/delete", deletePost)
