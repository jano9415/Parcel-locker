package com.parcellocker.apigatewayservice.util;

import com.parcellocker.apigatewayservice.exception.JwtTokenMalformedException;
import com.parcellocker.apigatewayservice.exception.JwtTokenMissingException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class JwtUtil {

    //Jwt token titkosítás. A titkosítás az application.properties-ben található
    @Value("${jwt.secret}")
    private String jwtSecret;

    //A user szerepkör ezeket a végpontokat érheti el
    final List<String> apiEndpointsForUser = List.of(
            //Csomag küldése a weblapról feladási kóddal
            "/parcelhandler/parcel/sendparcelwithcodefromwebpage",
            //Felhasználó csomagjainak lekérése
            "/parcelhandler/parcel/getparcelsofuser",
            //Felhasználó kitörli az előzetes csomagfeladást
            "/parcelhandler/parcel/deletemyparcel"
    );

    //A courier szerepkör ezeket a végpontokat érheti el
    final List<String> apiEndpointsForCourier = List.of(
            //Csomagok lekérése, amik készen állnak az elszállításra
            "/parcelhandler/parcel/getparcelsforshipping",
            //Automata kiürítése. Elszállításra váró csomagok átkerülnek a futárhoz
            "/parcelhandler/parcel/emptyparcellocker",
            //Futárnál lévő csomagok lekérése
            "/parcelhandler/parcel/getparcelsforparcellocker",
            //Automata feltöltése
            "/parcelhandler/parcel/fillparcellocker",
            //Futár lead egy csomagot a központi raktárban
            "/parcelhandler/parcel/handparceltostore",
            //Futár felvesz egy csomagot a központi raktárból
            "/parcelhandler/parcel/pickupparcelfromstore"
    );

    //Az admin szerepkör ezeket a végpontokat érheti el
    final List<String> apiEndpointsForAdmin = List.of(
            //Új admin létrehozása
            "/auth/createadmin",
            //Új futár létrehozása
            "/auth/createcourier",
            //Központi raktárak lekérése
            "/parcelhandler/store/getstores",
            //Összes kézbesített csomagok száma
            "/statistics/parcel/numberofparcels",
            //Leggyakoribb méretű csomagok: kicsi, közepes vagy nagy,
            "/statistics/parcel/mostcommonparcelsize",
            //Csomagok száma méret szerint
            "/statistics/parcel/numberofparcelsbysize",
            //Összes bevétel a kézbesített csomagokból
            "/statistics/parcel/totalrevenue",
            //Csomagok értékének átlaga forintban
            "/statistics/parcel/averageparcelvalue",
            //Feladott csomagok száma aszerint, hogy automatából vagy online adják fel
            "/statistics/parcel/amountofparcelsfromonlineandparcellocker",
            //Honnan adják fel a legtöbb csomagot?
            "/statistics/parcel/mostcommonsendinglocation",
            //Hova érkezik a legtöbb csomag?
            "/statistics/parcel/mostcommonreceivinglocation",
            //Mennyi csomagot fizetnek ki előre? Mennyit fizetnek ki az automatánál?
            "/statistics/parcel/paymentdatas",
            //Szállítási idők
            "/statistics/parcel/averageminmaxshippingtime",
            //Csomagfeladások száma automaták szerint
            "/statistics/parcel/totalsendingbylocations",
            //Csomagátvételek száma automaták szerint
            "/statistics/parcel/totalpickingupbylocations",
            //Összes futár lekérése
            "/parcelhandler/courier/getcouriers",
            //Futár valamely adatának módosítása
            "/parcelhandler/courier/updatecourier",
            //Futár lekérése id alapján
            "/parcelhandler/courier/findcourierbyid",
            //Központi raktárak csomagjainak lekérése
            "/parcelhandler/parcel/getparcelsofstore",
            //Csomag újraindítása az automatához
            "/parcelhandler/parcel/updatepickingupexpired",
            //Futárok csomagjainak lekérése
            "/parcelhandler/parcel/getparcelsofcourier",
            //Automaták csomagjainak lekérése
            "/parcelhandler/parcel/getparcelsofparcellocker",
            //Csomagátvételi lejárati idő meghosszabbítása
            "/parcelhandler/parcel/updatepickingupexpirationdate",
            //Csomagfeladási lejárati idő meghosszabbítása
            "/parcelhandler/parcel/updatesendingexpirationdate",
            //Ügyfél elhelyezi a csomagot a feladási automatába időpont -> ügyfél átveszi a csomagot az érkezési automatából időpont
            "/statistics/parcel/placebycustomerandpickupbycustomer",
            //Ügyfél elhelyezi a csomagot a feladási automatába időpont -> futár kiveszi a csomagot a feladási automatából időpont
            "/statistics/parcel/placebycustomerandpickupbycourier",
            //Futár kiveszi a csomagot a feladási automatából időpont -> futár elhelyezi a csomagot az érkezési automatába időpont
            "/statistics/parcel/pickupbycourierandplacebycourier",
            //Futár elhelyezi a csomagot az érkezési automatába időpont -> ügyfél átveszi a csomagot az érkezési automatából időpont
            "/statistics/parcel/placebycourierandpickupbycustomer"



    );

    //Jwt token body részének lekérése
    //A body rész tartalmazza a subject mezőt és a claim mezőket, amiből több is lehet
    public Claims getClaims(final String token) {
        try {
            Claims body = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            return body;
        } catch (Exception e) {
            System.out.println(e.getMessage() + " => " + e);
        }
        return null;
    }

    //Jwt token validációja
    //Szerepkörök lekérése a token-ből
    //Ellenőrzöm, hogy a bejövő végpontot elérheti-e az adott szerepkörrel rendelkező felhasználó
    public void validateToken(final String token, ServerHttpRequest request) throws JwtTokenMalformedException, JwtTokenMissingException {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);

            //Szerepkörök kiolvasása a token-ből
            List<String> roles = (List<String>) getClaims(token).get("roles");

            //Ha a kérés szerepkörei tartalmazzák: user
            if(roles.contains("user")){
                Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpointsForUser.stream()
                        .noneMatch(uri -> r.getURI().getPath().contains(uri));

                //A user nem jogosult elérni
                //Az elérni kívánt végpont nincs benne az 'apiEndpointsForUser' listában
                if(isApiSecured.test(request)){
                    throw new JwtTokenMalformedException("notEligible");
                }
            }
            //Ha a kérés szerepkörei tartalmazzák: courier
            if(roles.contains("courier")){
                Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpointsForCourier.stream()
                        .noneMatch(uri -> r.getURI().getPath().contains(uri));

                //A courier nem jogosult elérni
                //Az elérni kívánt végpont nincs benne az 'apiEndpointsForCourier' listában
                if(isApiSecured.test(request)){
                    throw new JwtTokenMalformedException("notEligible");
                }
            }
            //Ha a kérés szerepkörei tartalmazzák: admin
            if(roles.contains("admin")){
                Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpointsForAdmin.stream()
                        .noneMatch(uri -> r.getURI().getPath().contains(uri));

                //Az admin nem jogosult elérni
                //Az elérni kívánt végpont nincs benne az 'apiEndpointsForAdmin' listában
                if(isApiSecured.test(request)){
                    throw new JwtTokenMalformedException("notEligible");
                }
            }

            //A token nem valid vagy nem a szerver által hitelesített vagy lejárt vagy nem támogatott vagy üres
        } catch (SignatureException ex) {
            throw new JwtTokenMalformedException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new JwtTokenMalformedException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new JwtTokenMalformedException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new JwtTokenMalformedException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new JwtTokenMissingException("JWT claims string is empty.");
        }
    }
}
