import { Request, Response } from "express"
import { admin } from "../config/firebase"


/**
 * Create a new user
 * @route POST /user/create
 */
export const createUser = async (req: Request, res: Response): Promise<void> => {
  try {
    const { email, password } = req.body
    console.log(email, password)
    if (!email && !password) {
      console.log("No payload from user")
      res.status(200).send({ "success": false }) // FIXME: throw empty payload error
    }
    const userRecord = await admin.auth().createUser({
      email: email,
      password: password,
    })
    if (userRecord.uid) {
      console.log(`Successfully created user ${userRecord.uid}`)
      res.status(200).send({ "success": true })
    } else {
      console.log("Failed to create user")
      res.status(200).send({ "success": false })
    }
  } catch (error) {
    console.log("Error creating new user:", error)
  }
}
