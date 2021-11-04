import * as functions from "firebase-functions"
import * as express from "express"
import * as cors from "cors"
import { router } from "./routes/auth.route"


const app = express()
app.use(express.urlencoded({ extended: true }))
app.use(express.json())
app.use(cors())

app.on("error", (err) => {
  console.log(err)
})

// ----- router test -----
app.use("/test", router)
// -----------------------

app.get("/", (req: express.Request, res: express.Response) => {
  res.status(200).send("GET request test")
})
  .post("/", (req, res) => {
    res.status(200).send(req.body)
  })

export const api = functions.region("asia-northeast3").https.onRequest(app)
