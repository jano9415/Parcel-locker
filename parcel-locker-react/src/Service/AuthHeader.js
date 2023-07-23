import Cookies from "js-cookie";

export default function authHeader () {
    //Aktuálisan bejelentkezett user kiolvasása cookie-ből
    const user = JSON.parse(Cookies.get('user'));

    //Visszatérés a bejelentkezett felhasználó jwt tokenével.
    if (user && user.token) {
      return { Authorization: user.token };
    } else {
      return {};
    }

}