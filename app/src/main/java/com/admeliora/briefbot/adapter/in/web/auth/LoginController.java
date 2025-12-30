package com.admeliora.briefbot.adapter.in.web.auth;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/**
 * Controller for login page
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public void login(@RequestParam(required = false) String error, HttpServletResponse response) throws IOException {
        // Return HTML directly to avoid circular view path issue
        String html = generateLoginHtml(error != null);
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(html);
    }

    private String generateLoginHtml(boolean hasError) {
        return """
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - BriefBot</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh; display: flex; align-items: center; justify-content: center; padding: 20px;
        }
        .login-container {
            background: white; border-radius: 16px; box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
            padding: 48px; max-width: 420px; width: 100%;
        }
        .logo { text-align: center; margin-bottom: 32px; }
        .logo h1 { color: #667eea; font-size: 32px; font-weight: 700; margin-bottom: 8px; }
        .logo p { color: #6b7280; font-size: 14px; }
        .error-message {
            background: #fee2e2; border: 1px solid #ef4444; color: #991b1b;
            padding: 12px; border-radius: 8px; margin-bottom: 24px; font-size: 14px;
        }
        .form-group { margin-bottom: 24px; }
        label { display: block; color: #374151; font-weight: 500; margin-bottom: 8px; font-size: 14px; }
        input[type="text"], input[type="email"], input[type="password"] {
            width: 100%; padding: 12px 16px; border: 2px solid #e5e7eb; border-radius: 8px;
            font-size: 14px; transition: border-color 0.2s;
        }
        input:focus { outline: none; border-color: #667eea; }
        .btn {
            width: 100%; padding: 14px; border: none; border-radius: 8px;
            font-size: 16px; font-weight: 600; cursor: pointer; transition: all 0.2s; margin-bottom: 16px;
        }
        .btn-primary { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; }
        .btn-primary:hover { transform: translateY(-2px); box-shadow: 0 10px 20px rgba(102, 126, 234, 0.4); }
        .btn-google {
            background: white; color: #374151; border: 2px solid #e5e7eb;
            display: flex; align-items: center; justify-content: center; gap: 12px;
        }
        .btn-google:hover { background: #f9fafb; border-color: #d1d5db; }
        .divider { text-align: center; margin: 24px 0; position: relative; }
        .divider::before {
            content: ''; position: absolute; top: 50%; left: 0; right: 0; height: 1px; background: #e5e7eb;
        }
        .divider span { background: white; padding: 0 16px; position: relative; color: #6b7280; font-size: 14px; }
        .register-link { text-align: center; margin-top: 24px; color: #6b7280; font-size: 14px; }
        .register-link a { color: #667eea; text-decoration: none; font-weight: 600; }
        .register-link a:hover { text-decoration: underline; }
        .google-icon { width: 20px; height: 20px; }
    </style>
</head>
<body>
    <div class="login-container">
        <div class="logo">
            <h1>🤖 BriefBot</h1>
            <p>Sign in to your account</p>
        </div>
        """ + (hasError ? """
        <div class="error-message">Invalid username or password</div>
        """ : "") + """
        <form action="/api/oauth2/authorization/google" method="get">
            <button type="submit" class="btn btn-google">
                <svg class="google-icon" viewBox="0 0 24 24">
                    <path fill="#4285F4" d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z"/>
                    <path fill="#34A853" d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z"/>
                    <path fill="#FBBC05" d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z"/>
                    <path fill="#EA4335" d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z"/>
                </svg>
                Continue with Google
            </button>
        </form>
        <div class="divider"><span>or sign in with email</span></div>
        <form action="/api/perform-login" method="post">
            <div class="form-group">
                <label for="username">Email</label>
                <input type="email" id="username" name="username" placeholder="your.email@example.com" required autofocus>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" placeholder="Enter your password" required>
            </div>
            <button type="submit" class="btn btn-primary">Sign In</button>
        </form>
        <div class="register-link">
            Don't have an account? <a href="/api/auth/register">Create one via API</a>
        </div>
    </div>
</body>
</html>
        """;
    }
}

