import firebase from 'firebase';
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyA3SS7NOeFgNeM6kijP4LYc05GoRIIneL0",
  authDomain: "cout-1c9d9.firebaseapp.com",
  databaseURL: "https://cout-1c9d9.firebaseio.com",
  projectId: "cout-1c9d9",
  storageBucket: "cout-1c9d9.appspot.com",
  messagingSenderId: "172537648862",
  appId: "1:172537648862:web:82309e9433d0b4c70f144f",
  measurementId: "G-39HNFM9FQN"
};
  const firebaseApp = firebase.initializeApp(firebaseConfig);
  const db = firebaseApp.firestore();
  const timestamp = firebase.firestore.FieldValue.serverTimestamp();
  const auth = firebase.auth();
const provider = new firebase.auth.GoogleAuthProvider();
export { auth, provider }
  export {timestamp};
  export default db;