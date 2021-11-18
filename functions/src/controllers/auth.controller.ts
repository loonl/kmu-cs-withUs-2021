import { Request, Response } from "express"
import { db } from "../config/firebase"


/**
 * Get user data
 * @route GET /user/get
 */
export const getUser = async (req: Request, res: Response): Promise<any> => {
  try {
    const userData = (await db.collection("User").doc(res.locals.uid).get()).data()
    if (userData) {
      console.log(userData)
      res.json(userData)
    } else {
      console.log("Failed to get user data")
      res.send({ "success": false })
    }
  } catch (error) {
    console.log("Error on getting a new user:", error)
  }
}


/**
 * Create a new user
 * @route POST /user/create
 */
export const createUser = async (req: Request, res: Response): Promise<void> => {
  try {
    const { displayName, birthDate, gender, region, interest } = req.body

    if (!displayName && !birthDate && !gender && !region) {
      console.log("No payload from user")
      res.send({ "success": false }) // FIXME: throw empty payload error
    }
    db.collection("User").add({
      displayName: displayName,
      birthDate: birthDate,
      gender: gender,
      region: region,
      interest: interest,
    })
    console.log("Created a new user")
    res.send({ "success": false })
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
