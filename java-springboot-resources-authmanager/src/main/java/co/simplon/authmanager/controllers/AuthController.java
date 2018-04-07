package co.simplon.authmanager.controllers;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.simplon.authmanager.model.LoginInfos;
import co.simplon.authmanager.model.PasswordInfos;
import co.simplon.authmanager.model.User;
import co.simplon.authmanager.repository.UserRepository;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class AuthController {

	@Value( "${jwt.secret}" )
	private String JWT_SECRET;

	@Value( "${jwt.issuer}" )
	private String JWT_ISSUER;

	@Value( "${jwt.timetolive}" )
	private long JWT_TOKEN_TIME_TO_LIVE;

	@Autowired UserRepository repository;

	@CrossOrigin
	@PostMapping("/auth")
	public ResponseEntity<Object> authentification(@RequestBody LoginInfos loginInfos) {

		Map<String, String> responseEntity = new HashMap<String, String>();

		if((loginInfos == null) || (loginInfos.getUsername() == null) || (loginInfos.getPassword() == null))
			return ResponseEntity.badRequest().body("Invalid credentials !");

		// First get the user
		List<User> users = repository.findByUsername(loginInfos.getUsername());
		if(users.size() != 1){
			// The user may have use his email as username
			users = repository.findByEmail(loginInfos.getUsername());

			if(users.size() != 1)
				return ResponseEntity.badRequest().body("Invalid credentials !");
		}

		User authUser = users.get(0);

		// Check the user status
		if(authUser.getStatus() != User.UserStatus.ACTIVE)
			return ResponseEntity.badRequest().body("Invalid credentials !");

		// Check the user password
		if(!loginInfos.getPassword().equals(authUser.getPassword()))
			return ResponseEntity.badRequest().body("Invalid credentials !");

		// Check the user has a role
		if(authUser.getRole() == null)
			return ResponseEntity.badRequest().body("Corrupted account !");

		// Prepare the claims for the JWT
		Map<String, Object> tokenClaims = new HashMap<String, Object>();
		tokenClaims.put("user", authUser.getUsername());
		tokenClaims.put("email", authUser.getEmail());
		tokenClaims.put("role", authUser.getRole().getName());
		tokenClaims.put("access", authUser.getRole().getAccess());

		// Create and return the JWT
		String token = createJWT(tokenClaims);

		responseEntity.put("token", token);

		return ResponseEntity.ok().body(responseEntity);
	}

	@CrossOrigin
	@PostMapping("/changePwd")
	public ResponseEntity<Object> changePassword(@RequestBody PasswordInfos pwdInfos) {
		if(pwdInfos.getUsername() == null)
			return ResponseEntity.badRequest().body(null);

		// First get the user
		List<User> users = repository.findByUsername(pwdInfos.getUsername());
		if(users.size() != 1){
			// The user may have use his email as username
			users = repository.findByEmail(pwdInfos.getUsername());

			if(users.size() != 1)
				return ResponseEntity.badRequest().body(null);
		}

		User userToUpdate = users.get(0);

		if(!pwdInfos.isForcePwdChange())
		{
			// Check the old password match with the user password
			if(!userToUpdate.getPassword().equals(pwdInfos.getOldPassword()))
				return ResponseEntity.badRequest().body(null);
		}

		// Update the password
		userToUpdate.setPassword(pwdInfos.getNewPassword());

		repository.save(userToUpdate);
		return ResponseEntity.ok(null);
	}

	private String createJWT(Map<String, Object> tokenClaims) {

		//The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		//We will sign our JWT with our ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(JWT_SECRET);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		//Let's set the JWT Claims
		JwtBuilder builder = Jwts.builder().setId(UUID.randomUUID().toString())
				.setIssuedAt(now)
				.setNotBefore(now)
				.setIssuer(JWT_ISSUER)
				.addClaims(tokenClaims)
				.signWith(signatureAlgorithm, signingKey);

		//if it has been specified, let's add the expiration
		if (JWT_TOKEN_TIME_TO_LIVE >= 0) {
			long expMillis = nowMillis + JWT_TOKEN_TIME_TO_LIVE;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}

		//Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();
	}
}