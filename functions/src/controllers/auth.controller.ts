import { Request, Response } from "express"
import { admin, db } from "../config/firebase"


/**
 * Get user data
 * @route GET /user/get
 */
export const getUser = async (req: Request, res: Response): Promise<any> => {
  try {
    // TODO:
  } catch (error) {
    // TODO:
  }
}


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
    console.log("Error on creating a new user:", error)
  }
}


/**
 * Modify user info
 * @route POST /user/modify
 */
export const modifyUser = async (req: Request, res: Response): Promise<void> => {
  try {
    // TODO:
  } catch (error) {
    // TODO:
  }
}


/**
 * Delete user account & data
 * @route POST /user/delete
 */
export const deleteUser = async (req: Request, res: Response): Promise<void> => {
  try {
    // TODO:
  } catch (error) {
    // TODO:
  }
}
