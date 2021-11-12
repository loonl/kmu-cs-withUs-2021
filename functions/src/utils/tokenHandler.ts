import { Request, Response, NextFunction } from "express"
import { admin } from "../config/firebase"

export const userVerifyToken = async (req: Request, res: Response, next: NextFunction): Promise<void> => {
  try {
    const headers = req.headers.authorization
    let token
    if (headers) {
      const authorization = headers.split(" ")
      token = authorization[1]
      const decodedIdToken = await admin.auth().verifyIdToken(token)
      const currentDate = Date.now() / 1000
      const exp = decodedIdToken.exp
      if (currentDate >= exp) {
        throw Error("Token has been expired")
      }
      res.locals.uid = decodedIdToken.uid
      res.locals.email = decodedIdToken.email
      console.log("User uid: ", res.locals.uid)
    } else {
      // Anonymous user
      next()
    }
  } catch (error) {
    throw Error("Uncaught error")
  }
}
