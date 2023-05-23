export default function authHeader () {
    //Aktuálisan bejelentkezett user kiolvasása a local storage-ból.
    const user = JSON.parse(localStorage.getItem('user'));

    //Visszatérés a bejelentkezett felhasználó jwt tokenével.
    if (user && user.token) {
      return { Authorization: user.token };
    } else {
      return {};
    }

}