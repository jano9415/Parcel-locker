package com.parcellocker.apigatewayservice.filter;

import com.parcellocker.apigatewayservice.exception.JwtTokenMalformedException;
import com.parcellocker.apigatewayservice.exception.JwtTokenMissingException;
import com.parcellocker.apigatewayservice.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Predicate;

@Component
public class JwtAuthenticationFilter implements GatewayFilter {

    @Autowired
    private JwtUtil jwtUtil;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {



        ServerHttpRequest request = (ServerHttpRequest) exchange.getRequest();
        System.out.println("Ez az uri: " + request.getURI().getPath());


        //Ezekhez az végpontokhoz nem szükséges autentikáció. Ezeket bárki elérheti
        final List<String> apiEndpoints = List.of(
                //Felhasználói regisztráció
                "/auth/signup",
                //Regisztráció aktiválása
                "/auth/activation",
                //Felhasználói bejelentkezés
                "/auth/login",
                //Felhasználói bejelentkezés a második faktorral
                "/auth/loginwithsecondfactor",
                //Futár bejelentkezés
                "/auth/courierlogin",
                //Csomag automaták lekérése
                "/parcelhandler/parcellocker/getparcellockersforchoice",
                //Csomag feladása feladási kód nélkül
                "/parcelhandler/parcel/sendparcelwithoutcode",
                //Automata tele van?
                "/parcelhandler/parcellocker/isparcellockerfull",
                //Rekeszek tele vannak? Kicsi, közepes és nagy
                "/parcelhandler/parcellocker/areboxesfull",
                //Csomag átvétele
                "/parcelhandler/parcel/pickupparcel",
                //Csomag keresés feladási kód szerint
                "/parcelhandler/parcel/getparcelforsendingwithcode",
                //Csomag feladása feladási kóddal
                "/parcelhandler/parcel/sendparcelwithcode",
                //Automata telítettségi adatok lekérése
                "/parcelhandler/parcellocker/getsaturationdatas",
                //Csomag követése
                "/parcelhandler/parcel/followparcel"
        );

        Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
                .noneMatch(uri -> r.getURI().getPath().contains(uri));


        //A kérés header része tartalmazza a jwt tokent az alábbi formába
        //Key -> Authorization         Value -> jwt token
        if (isApiSecured.test(request)) {
            //Jwt token nélküli kérések
            //A végponthoz token szükséges, de a kérés head részében hiányzik
            if (!request.getHeaders().containsKey("Authorization")) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);

                return response.setComplete();
            }

            //Jwt token lekérése a kérés header részéből
            final String token = request.getHeaders().getOrEmpty("Authorization").get(0);

            //Token validációja
            //Végpont ellenőrzése, hogy az adott szerepkör elérheti-e
            try {
                jwtUtil.validateToken(token, request);
            } catch (JwtTokenMalformedException | JwtTokenMissingException e) {

                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.BAD_REQUEST);

                return response.setComplete();
            }

            Claims claims = jwtUtil.getClaims(token);
            exchange.getRequest().mutate().header("id", String.valueOf(claims.get("id"))).build();
        }

        return chain.filter(exchange);
    }
}
