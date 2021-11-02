import * as admin from "firebase-admin"

// eslint-disable-next-line @typescript-eslint/no-var-requires
const serviceAccount = require("../Firebase/serviceAccountKey.json")

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  storageBucket: "kmu-with-us.appspot.com",
})

const bucket = admin.storage().bucket()
const db = admin.firestore()
const messaging = admin.messaging()
const timestamp = admin.firestore.FieldValue.serverTimestamp()
const docId = admin.firestore.FieldPath.documentId()
const increment = admin.firestore.FieldValue.increment(1)
const decrement = admin.firestore.FieldValue.increment(-1)


export {admin, bucket, db, messaging, timestamp, docId, increment, decrement}
