package auth

// What type of authentication and authorization will be used
type Method int

const (
	// No authentication or authorization
	AuthTypeNone Method = iota
	// "Plain" OpenID Connect and OAuth 2.0
	AuthTypeOIDC
	// HEART profiled OpenID Connect and OAuth 2.0
	AuthTypeHEART
)

// Config represents configuration information necessary to set up authentication
// and authorization for the FHIR server
type Config struct {
	Method           Method
	ClientID         string
	ClientSecret     string
	AuthorizationURL string
	TokenURL         string
	IntrospectionURL string
	UserInfoURL      string
	JWKPath          string
	OPURL            string
	SessionSecret    string
}

// None provides a server config where no authorization or authentication will
// be provided
func None() Config {
	return Config{Method: AuthTypeNone}
}

// OIDC provides a server configuration that will act as an OpenID Connect relying
// party for authentication, and will perform OAuth 2.0 token introspection to the
// same server for authorization.
//
// This configuration still uses the HEART scopes for authorizing access to FHIR
// resources when using OAuth 2.0.
//
// clientID is the registered ID at the OpenID Connect Provider (OP)
// clientSecret is the secret for the client (usually generated by the OP)
// authorizationURL Where to redirect users for authorization
// tokenURL Where to obtain OAuth 2.0 tokens
// userInfoURL The location of the OpenID Connect UserInfo endpoint
// sessionSecret The secret that will be used to encrypt the session when it is
//   stored in a user's cookie
func OIDC(clientID, clientSecret, authorizationURL, tokenURL, userInfoURL, introspectionURL, sessionSecret string) Config {
	return Config{Method: AuthTypeOIDC, ClientID: clientID, ClientSecret: clientSecret,
		AuthorizationURL: authorizationURL, TokenURL: tokenURL, UserInfoURL: userInfoURL,
		IntrospectionURL: introspectionURL, SessionSecret: sessionSecret}
}

// HEART provides a server configuration that will act as a HEART profiled
// OpenID Connect relying party for authentication, and will perform HEART profiled
// OAuth 2.0 token introspection to the same server for authorization.
//
// This configuration uses the HEART scopes for authorizing access to FHIR
// resources when using OAuth 2.0.
//
// clientID is the registered ID at the OpenID Connect Provider (OP)
// jwkPath is the file location of the private key for this client in JWK format
//   it is expected that the corresponding public key has been registered at the
//   OP
// opURL the location of the OIDC OP. Discovery will be used to locate all of the
//   other necessary endpoints.
// sessionSecret The secret that will be used to encrypt the session when it is
//   stored in a user's cookie
func HEART(clientID, jwkPath, opURL, sessionSecret string) Config {
	return Config{Method: AuthTypeHEART, ClientID: clientID, JWKPath: jwkPath,
		OPURL: opURL, SessionSecret: sessionSecret}
}
