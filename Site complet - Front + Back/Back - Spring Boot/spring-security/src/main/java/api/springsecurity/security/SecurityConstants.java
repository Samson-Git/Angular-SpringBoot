package api.springsecurity.security;

public class SecurityConstants {
    public static final String SECRET = "camille";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "bearer ";
    public static final String HEADER_STRING = "Authorization";
}
