export class LoginResponse {


    token: string = "";

    tokenType = "Bearer";

    userId: string = "";

    emailAddress: string = "";

    firstName: string = "";

    lastName: string = "";

    roles: Array<string> = new Array<string>();

    constructor(){}
    
}