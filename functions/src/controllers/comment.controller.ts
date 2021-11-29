import { Request, Response } from "express"
import { db } from "../config/firebase"


/**
 * Get comments
 * @route GET /comment/get
 */
export const getComment = async (req: Request, res: Response): Promise<void> => {
  try {
    const { postUid } = req.query
    const snapshot = await db
      .collection("Comment")
      .where("uid", "==", postUid)
      .orderBy("bundleId", "asc")
      .orderBy("createdAt", "asc")
      .get()
    const result = snapshot.docs.map((doc) => doc.data())
    console.log(result)
    res.send(result)
  } catch (error) {
    console.log("Error on getting comments:", error)
  }
}


/**
 * Create a new comment
 * @route POST /comment/create
 */
export const createComment = async (req: Request, res: Response): Promise<void> => {
  try {
    if (res.locals.isAnonymous) {
      res.send({ success: false })
    }
    let { author, category, displayName, content, isComment, likes, bundleId, postUid, profileImage } = req.body
    if (!author && !category && !displayName && !content && !postUid && !profileImage) {
      console.log("No payload from user")
      res.send({ "success": false }) // FIXME: throw empty payload error
    }

    const time = new Date()
    const createdAt = time.getTime()
    if (isComment) {
      bundleId = createdAt
    }

    const ref = await db.collection("Comment").add({
      author: author,
      category: category,
      displayName: displayName,
      content: content,
      likes: 0,
      isComment: isComment,
      postUid: postUid,
      profileImage: profileImage,
      bundleId: bundleId,
      createdAt: createdAt,
      notification: true,
    })
    await db.collection("Comment").doc(ref.id).update({
      uid: ref.id,
    })
    res.send({ success: true })
  } catch (error) {
    console.log("Error on creating a new comment:", error)
  }
}


/**
 * Delete a comment
 * @route GET /comment/delete
 */
export const deleteComment = async (req: Request, res: Response): Promise<void> => {
  try {
    // TODO:
  } catch (error) {
    // TODO:
  }
}
