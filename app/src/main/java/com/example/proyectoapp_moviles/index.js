const functions = require("firebase-functions");
const admin = require("firebase-admin");

admin.initializeApp();

exports.deleteUserAuth = functions.https.onCall(async (data, context) => {

    const uid = data.uid;

    await admin.auth().deleteUser(uid);

    return {
        success: true
    };
});