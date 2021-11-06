import { Request, Response } from "express"
import { admin } from "../config/firebase"


/**
 * Create a new user
 * @route GET /user/create
 */
export const createUser = (req: Request, res: Response): void => {
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
      console.log("Successfully created new user:", userRecord.uid)
      res.status(200).send(userRecord.uid)
    })
    .catch((error) => {
      console.log("Error creating new user:", error)
    })
}
