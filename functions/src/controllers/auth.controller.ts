import * as express from "express"
import { admin } from "../config/firebase"

const appVerifyToken = async (req: express.Request, res: express.Response) => {
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
        throw new Error("Token has been expired")
      }
    }
  } catch (error) {
    throw new Error("Uncaught error")
  }
}

const createUser = (req: express.Request, res: express.Response) => {
  admin.auth().createUser({
    email: "test@example.com",
    emailVerified: false,
    phoneNumber: "+11234567890",
    password: "secretPassword",
    displayName: "Test User",
    photoURL: "http://www.example.com/12345678/photo.png",
    disabled: false,
  })
    .then((userRecord) => {
      // See the UserRecord reference doc for the contents of userRecord.
      console.log("Successfully created new user:", userRecord.uid)
    })
    .catch((error) => {
      console.log("Error creating new user:", error)
    })
}

export { appVerifyToken, createUser }
