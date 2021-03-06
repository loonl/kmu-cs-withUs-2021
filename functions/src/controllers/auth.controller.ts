import { Request, Response } from "express"
import { admin, db } from "../config/firebase"


/**
 * Get user data
 * @route GET /user/get
 */
export const getUser = async (req: Request, res: Response): Promise<void> => {
  try {
    if (res.locals.isAnonymous) {
      res.json({
        displayName: "익명",
        birthDate: 0,
        gender: "",
        interest: "",
        region: "",
      })
    }
    const userData = (await (db.collection("User").doc(res.locals.uid).get())).data()
    if (userData != null) {
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
 * @param displayName
 */
export const createUser = async (req: Request, res: Response): Promise<void> => {
  try {
    const { displayName, birthDate, gender, region, interest } = req.body

    if (!displayName && !birthDate && !gender && !region) {
      console.log("No payload from user")
      res.send({ "success": false }) // FIXME: throw empty payload error
    }
    await db.collection("User").doc(res.locals.uid).set({
      displayName: displayName,
      birthDate: birthDate,
      gender: gender,
      region: region,
      interest: interest,
    })
    console.log("Created a new user")
    res.send({ "success": true })
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
    if (res.locals.isAnonymous) {
      res.json({
        displayName: "익명",
        birthDate: 0,
        gender: "",
        interest: "",
        region: "",
      })
    }
    const { displayName, birthDate, region, interest } = req.body

    if (!displayName && !birthDate && !region && !interest) {
      console.log("No payload from user")
      res.send({ "success": false }) // FIXME: throw empty payload error
    }

    await db.collection("User").doc(res.locals.uid).update({
      displayName: displayName,
      birthDate: birthDate,
      region: region,
      interest: interest,
    })
    console.log("Updated user information")
    res.send({ "success": true })
  } catch (error) {
    console.log("Error on updating user information:", error)
  }
}


/**
 * Delete user account & data
 * @route GET /user/delete
 */
export const deleteUser = async (req: Request, res: Response): Promise<void> => {
  try {
    const userData = await db.collection("User").doc(res.locals.uid).get()
    if (userData.exists) {
      await admin.auth().deleteUser(res.locals.uid)
      await db.collection("User").doc(res.locals.uid).delete()
      console.log("Successfully deleted user")
      res.send({ "success": true })
    }
  } catch (error) {
    console.log("Error on creating a new user:", error)
  }
}
