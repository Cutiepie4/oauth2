# OAuth2 Flow with URLs

## User Initiates Login

**URL:** `http://localhost:8080/oauth2/authorization/github`

- The user navigates to this URL (usually by clicking a "Login with GitHub" button), which is automatically generated by Spring Security based on your configuration.
- Spring Security redirects the user to GitHub's authorization endpoint.

## Redirect to GitHub Authorization Endpoint

**URL:** `https://github.com/login/oauth/authorize`

- GitHub presents a login page (if not already logged in) and an authorization page asking the user to grant permissions to your application.
- After the user grants permissions, GitHub redirects back to your application with an authorization code.

## GitHub Redirects to Your Application with Authorization Code

**URL:** `http://localhost:8080/login/oauth2/code/github?code=AUTHORIZATION_CODE`

- GitHub redirects to this URL, where `AUTHORIZATION_CODE` is a temporary code provided by GitHub.
- Spring Security intercepts this URL, extracts the authorization code, and automatically handles the next step.

## Exchange Authorization Code for Access Token

**URL:** `https://github.com/login/oauth/access_token`

- Spring Security sends a POST request to GitHub's token endpoint to exchange the authorization code for an access token.
- The request includes the authorization code, client ID, client secret, and redirect URI.

## GitHub Responds with Access Token

- GitHub responds with an access token, which Spring Security uses to authenticate the user.
- Spring Security now has the access token to fetch user information from GitHub.

## Fetch User Information

**URL:** `https://api.github.com/user`

- Spring Security uses the access token to make a GET request to GitHub's user information endpoint.
- GitHub responds with the user's profile information.

## Redirect to Success URL

**URL:** `http://localhost:8080/oauth2/login/success`

- After successfully fetching the user's information, Spring Security redirects the user to this URL, which you've configured in your security settings.
- This URL maps to a controller endpoint in your application where you can handle the authenticated user's information.