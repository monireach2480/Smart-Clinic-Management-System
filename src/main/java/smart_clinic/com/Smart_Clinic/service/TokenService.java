package smart_clinic.com.Smart_Clinic.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import smart_clinic.com.Smart_Clinic.repository.AdminRepository;
import smart_clinic.com.Smart_Clinic.repository.DoctorRepository;
import smart_clinic.com.Smart_Clinic.repository.PatientRepository;
import smart_clinic.com.Smart_Clinic.exception.InvalidTokenException;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration.days}")
    private int expirationDays;

    private final AdminRepository adminRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public TokenService(AdminRepository adminRepository,
                        DoctorRepository doctorRepository,
                        PatientRepository patientRepository) {
        this.adminRepository = adminRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    /**
     * Generates a JWT token for a user
     * @param email The user's email
     * @param userType The type of user (admin, doctor, patient)
     * @return Generated JWT token
     */
    public String generateToken(String email, String userType) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + TimeUnit.DAYS.toMillis(expirationDays));

        return Jwts.builder()
                .setSubject(email)
                .claim("userType", userType)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts the email from a JWT token
     * @param token JWT token
     * @return Extracted email
     * @throws InvalidTokenException if token is invalid
     */
    public String extractEmail(String token) throws InvalidTokenException {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid or expired token");
        }
    }

    /**
     * Extracts the user type from a JWT token
     * @param token JWT token
     * @return User type (admin, doctor, patient)
     * @throws InvalidTokenException if token is invalid
     */
    public String extractUserType(String token) throws InvalidTokenException {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("userType", String.class);
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid or expired token");
        }
    }

    /**
     * Validates a JWT token for a specific user type
     * @param token JWT token to validate
     * @param expectedUserType Expected user type (admin, doctor, patient)
     * @return true if valid, false otherwise
     */
    public boolean validateToken(String token, String expectedUserType) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject();
            String actualUserType = claims.get("userType", String.class);

            // Check if token matches expected user type
            if (!actualUserType.equals(expectedUserType)) {
                return false;
            }

            // Verify user exists in the appropriate repository
            return switch (actualUserType) {
                case "admin" -> adminRepository.existsByEmail(email);
                case "doctor" -> doctorRepository.existsByEmail(email);
                case "patient" -> patientRepository.existsByEmail(email);
                default -> false;
            };
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gets the signing key for JWT tokens
     * @return SecretKey for signing JWTs
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Extracts ID from token based on user type
     * @param token JWT token
     * @param userType User type (admin, doctor, patient)
     * @return User ID
     * @throws InvalidTokenException if token is invalid
     * @throws ResourceNotFoundException if user not found
     */
    public Long extractId(String token, String userType) throws InvalidTokenException, ResourceNotFoundException {
        String email = extractEmail(token);
        return switch (userType) {
            case "admin" -> adminRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Admin not found")).getId();
            case "doctor" -> doctorRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Doctor not found")).getId();
            case "patient" -> patientRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Patient not found")).getId();
            default -> throw new InvalidTokenException("Invalid user type");
        };
    }
}