import { Request, Response } from "express"
import { admin } from "../config/firebase"

export const appVerifyToken = async (req: Request, res: Response): Promise<void> => {
  try {
    const headers = req.headers.authorization
    let token
    if (headers) {
      const authorization = headers.split(" ")
      token = authorization[1]
    }
    if (token) {
      const decodedIdToken = await admin.auth().verifyIdToken(token)
      const currentDate = Date.now() / 1000
      const exp = decodedIdToken.exp
      if (currentDate >= exp) {
        throw Error("Token has been expired")
      }
    }
  } catch (error) {
    throw Error("Uncaught error")
  }
}
