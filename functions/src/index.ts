import * as functions from "firebase-functions"
import * as express from "express"
import * as cors from "cors"
import { router } from "./routes"
import { userVerifyToken } from "./utils/tokenHandler"


const app = express()
app.use(express.urlencoded({ extended: true }))
app.use(express.json())
app.use(cors())
app.use(userVerifyToken)
app.use(router)

app.on("error", (err) => {
  console.log(err)
})

app.get("/test", (req: express.Request, res: express.Response) => {
  res.send({ "title": "GET REQUEST TEST", "content": "Test Success" })
})
  .post("/test", (req, res) => {
    res.send(req.body)
  })

export const api = functions.region("asia-northeast3").https.onRequest(app)
