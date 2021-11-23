import { Request, Response } from "express"
import { db } from "../config/firebase"


/**
 * Get post data
 * @route GET /post/get
 */
export const getPost = async (req: Request, res: Response): Promise<void> => {
  try {
    const { category } = req.query
    console.log(category)
    const snapshot = await db
      .collection("Post")
      .where("category", "==", category)
      // .orderBy("createdAt", "desc")
      .get()
    const result = snapshot.docs.map((doc) => ({
      id: doc.id,
      ...doc.data(),
    }))
    console.log(result)
    res.send(result)
  } catch (error) {
    // TODO:
  }
}


/**
 * Create a new post
 * @route POST /post/create
 */
export const createPost = async (req: Request, res: Response): Promise<void> => {
  try {
    // TODO:
  } catch (error) {
    // TODO:
  }
}


/**
 * Modify post data
 * @route POST /post/modify
 */
export const modifyPost = async (req: Request, res: Response): Promise<void> => {
  try {
    // TODO:
  } catch (error) {
    // TODO:
  }
}


/**
 * Delete a post
 * @route POST /post/delete
 */
export const deletePost = async (req: Request, res: Response): Promise<void> => {
  try {
    // TODO:
  } catch (error) {
    // TODO:
  }
}
